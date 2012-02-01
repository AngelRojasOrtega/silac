/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblunidadmedida;
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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "unidadMedidaBean")
@ViewScoped
public class UnidadMedidaBean implements Serializable {

    private Tblunidadmedida unidad = new Tblunidadmedida();
    private Tblunidadmedida selectedUnidad;
    private List<Tblunidadmedida> unidades;

    /** Creates a new instance of UnidadMedidaBean */
    public UnidadMedidaBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.unidad = new Tblunidadmedida();
        this.selectedUnidad = null;
        this.unidades = null;
        populateUnidades();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(unidad);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Unidad de Medida se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            unidades = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idUnidadMedida");
        Tblunidadmedida u = (Tblunidadmedida) session.load(Tblunidadmedida.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(u);
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

    /**
     * Carga la lista de unidades de medida que seran mostradas en un cuadro de lista desplegable
     * @return listaUnidadMedida Lista de unidades de medida
     */
    public ArrayList<SelectItem> cargarListaUnidadMedida() {
        ArrayList<SelectItem> listaUnidadMedida = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cSeccion = session.createCriteria(Tblunidadmedida.class);
        cSeccion.addOrder(Order.asc("unidadMedida"));
        Iterator iter = cSeccion.list().iterator();
        while (iter.hasNext()) {
            Tblunidadmedida um = (Tblunidadmedida) iter.next();
            listaUnidadMedida.add(new SelectItem(um.getIdUnidadMedida(), um.getUnidadMedida() + "\t(" + um.getAbreviatura() + ")", ""));
        }
        session.close();
        return listaUnidadMedida;
    }

    public void nuevo() {
        this.init();
    }

    private void populateUnidades() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT u from Tblunidadmedida u ORDER BY u.idUnidadMedida";
        unidades = null;
        Query query = session.createQuery(sql);
        unidades = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    /** 
     * Getters & Setters 
     */
    public Tblunidadmedida getSelectedUnidad() {
        return selectedUnidad;
    }

    public void setSelectedUnidad(Tblunidadmedida selectedUnidad) {
        this.selectedUnidad = selectedUnidad;
    }

    public Tblunidadmedida getUnidad() {
        return unidad;
    }

    public void setUnidad(Tblunidadmedida unidad) {
        this.unidad = unidad;
    }

    public List<Tblunidadmedida> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<Tblunidadmedida> unidades) {
        this.unidades = unidades;
    }
}
