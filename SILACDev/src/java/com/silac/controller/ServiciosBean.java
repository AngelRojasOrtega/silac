/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblclinicaexterna;
import com.silac.model.Tblservicio;
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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 *
 * @author siman
 */
@ManagedBean(name = "serviciosBean")
@ViewScoped
public class ServiciosBean implements Serializable {

    private Short selectedTipoServicio;
    private boolean chkClinica = false;
    private Tblservicio servicio = new Tblservicio();
    private Tblclinicaexterna clinica = new Tblclinicaexterna();
    private ArrayList<Tblservicio> servicios;
    private Short selectedModalidad;
    private ArrayList<SelectItem> listaTipoServicio;

    /** Creates a new instance of ServiciosBean */
    public ServiciosBean() {
        this.init();
    }

    private void init() {
        this.selectedTipoServicio = null;
        this.listaTipoServicio = new ArrayList<SelectItem>();
        this.servicio = new Tblservicio();
        this.clinica = new Tblclinicaexterna();
        this.chkClinica = false;
        this.servicios = new ArrayList<Tblservicio>();
        this.selectedModalidad = null;
        populateServicios();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tbltiposervicio ts = (Tbltiposervicio) session.load(Tbltiposervicio.class, this.selectedTipoServicio);
            servicio.setTbltiposervicio(ts);
            session.save(servicio);
            if (this.isChkClinica())
            {
                clinica.setTblservicio(servicio);
                session.save(clinica);
            }
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El servicio se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevo() {
        this.init();
    }
        
    private void populateServicios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cServicio = session.createCriteria(Tblservicio.class);
        cServicio.addOrder(Order.asc("nombreServicio"));
        servicios = (ArrayList<Tblservicio>) cServicio.list();
        session.close();
    }

    public void onSelectModalidad() {
        TipoServiciosBean ts = (TipoServiciosBean) FacesUtil.getBean("tiposervicioBean");
        this.listaTipoServicio = ts.cargarListaTipoServicio(this.selectedModalidad);
    }
    
        /**
     * Carga la lista de servicios que seran mostrados en un cuadro de lista desplegable
     * @param idTipoServicio Representa el Id del tipo de servicio seleccionado por el usario, 
     * el cual servira para filtrar la lista de servicios
     * @return listaServicio Lista de servicios filtrados por tipo de serivicio
     */
    public ArrayList<SelectItem> cargarListaServicio(Short idTipoServicio) {
        ArrayList<SelectItem> listaServicio = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (idTipoServicio!= null) {
            Tbltiposervicio tiposervicio = (Tbltiposervicio) session.load(Tbltiposervicio.class, idTipoServicio);
            Iterator iterServicio = tiposervicio.getTblservicios().iterator();
            while (iterServicio.hasNext()) {
                Tblservicio s = (Tblservicio) iterServicio.next();
                listaServicio.add(new SelectItem(s.getIdServicio(), s.getNombreServicio(), ""));
            }
        }
        session.close();
        return listaServicio;
    }

    public Short getSelectedTipoServicio() {
        return selectedTipoServicio;
    }

    public void setSelectedTipoServicio(Short selectedTipoServicio) {
        this.selectedTipoServicio = selectedTipoServicio;
    }

    public boolean isChkClinica() {
        return chkClinica;
    }

    public void setChkClinica(boolean chkClinica) {
        this.chkClinica = chkClinica;
    }


    public Tblclinicaexterna getClinica() {
        return clinica;
    }

    public void setClinica(Tblclinicaexterna clinica) {
        this.clinica = clinica;
    }

    public Tblservicio getServicio() {
        return servicio;
    }

    public void setServicio(Tblservicio servicio) {
        this.servicio = servicio;
    }

    public ArrayList<Tblservicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Tblservicio> servicios) {
        this.servicios = servicios;
    }

    public ArrayList<SelectItem> getListaTipoServicio() {
        return listaTipoServicio;
    }

    public void setListaTipoServicio(ArrayList<SelectItem> listaTipoServicio) {
        this.listaTipoServicio = listaTipoServicio;
    }

    public Short getSelectedModalidad() {
        return selectedModalidad;
    }

    public void setSelectedModalidad(Short selectedModalidad) {
        this.selectedModalidad = selectedModalidad;
    }
}
