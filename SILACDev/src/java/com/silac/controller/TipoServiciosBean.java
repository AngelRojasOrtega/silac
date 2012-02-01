/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblmodalidad;
import com.silac.model.Tbltiposervicio;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "tiposervicioBean")
@ViewScoped
public class TipoServiciosBean implements Serializable {

    private String selectedModalidad;
    private ArrayList listaModalidades = new ArrayList();
    private Tbltiposervicio servicio = new Tbltiposervicio();
    private Object selectedServicio;
    private List servicios;

    /** Creates a new instance of TipoServiciosBean */
    public TipoServiciosBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/    
    private void init() {
        this.selectedModalidad = null;
        this.listaModalidades = new ArrayList();
        this.servicio = new Tbltiposervicio();
        this.selectedServicio = null;
        this.servicios = null;
        populateServicios();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tblmodalidad m = (Tblmodalidad) session.load(Tblmodalidad.class, Short.parseShort(selectedModalidad));
            servicio.setTblmodalidad(m);
            session.save(servicio);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El tipo de servicio se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idTipoServicio");
        Tbltiposervicio s = (Tbltiposervicio) session.load(Tbltiposervicio.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(s);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }
    
    public void nuevo(){
        this.init();
    }
    
    private void populateServicios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT m.nombreModalidad, t from Tbltiposervicio t join t.tblmodalidad m GROUP BY t.idTipoServicio ORDER BY t.idTipoServicio";
        servicios = null;
        Query query = session.createQuery(sql);
        servicios = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    /**
     * Carga la lista de tipos de servicio que seran mostrados en un cuadro de lista desplegable
     * @param idModalidad Representa el Id de la modalidad seleccionada por el usario, 
     * el cual servira para filtrar la lista de tipos de servicios
     * @return listaTipoServicio Lista de tipos de servicio filtrados por modalidad
     */
    public ArrayList<SelectItem> cargarListaTipoServicio(Short idModalidad) {
        ArrayList<SelectItem> listaTipoServicio = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (idModalidad!= null) {
            Tblmodalidad modalidad = (Tblmodalidad) session.load(Tblmodalidad.class, idModalidad);
            Iterator iterTipoServicios = modalidad.getTbltiposervicios().iterator();
            while (iterTipoServicios.hasNext()) {
                Tbltiposervicio ts = (Tbltiposervicio) iterTipoServicios.next();
                listaTipoServicio.add(new SelectItem(ts.getIdTipoServicio(), ts.getNombreTipoServicio(), ""));
            }
        }
        session.close();
        return listaTipoServicio;
    }
    
    /** 
     * Getters & Setters 
     */
    public ArrayList getListaModalidades() {
        return listaModalidades;
    }

    public void setListaModalidades(ArrayList listaModalidades) {
        this.listaModalidades = listaModalidades;
    }

    public String getSelectedModalidad() {
        return selectedModalidad;
    }

    public void setSelectedModalidad(String selectedModalidad) {
        this.selectedModalidad = selectedModalidad;
    }

    public Object getSelectedServicio() {
        return selectedServicio;
    }

    public void setSelectedServicio(Object selectedServicio) {
        this.selectedServicio = selectedServicio;
    }

    public Tbltiposervicio getServicio() {
        return servicio;
    }

    public void setServicio(Tbltiposervicio servicio) {
        this.servicio = servicio;
    }

    public List getServicios() {
        return servicios;
    }

    public void setServicios(List servicios) {
        this.servicios = servicios;
    }
}
