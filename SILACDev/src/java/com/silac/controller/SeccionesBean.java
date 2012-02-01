/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import com.silac.model.Tblseccion;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.util.Iterator;
import javax.faces.model.SelectItem;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "seccionesBean")
@RequestScoped
public class SeccionesBean {

    private ArrayList<Tblseccion> secs;
    private Tblseccion sec = new Tblseccion();
    private Tblseccion selectedSeccion;
    private Tblseccion[] selectedSecciones;

    /** Creates a new instance of SeccionesBean */
    public SeccionesBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.secs = new ArrayList<Tblseccion>();
        this.sec = new Tblseccion();
        this.selectedSeccion = null;
        this.selectedSecciones = null;
        this.populateSecciones();
    }

    /** Crea una nueva seccion en la base de datos.*/
    public String crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(sec);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La se seccion se creo satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
        return null;
    }

    /** Elimina el registro seleccionado por el usuario.*/
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idSeccion");
        Tblseccion s = (Tblseccion) session.load(Tblseccion.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(s);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public void populateSecciones() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cSeccion = session.createCriteria(Tblseccion.class);
        cSeccion.addOrder(Order.asc("nombreSeccion"));
        this.secs = (ArrayList<Tblseccion>) cSeccion.list();
        session.close();
    }

    /**
     * Carga la lista de secciones que seran mostradas en un cuadro de lista desplegable
     * @return listaSecciones Lista de secciones
     */
    public ArrayList<SelectItem> cargarSecciones() {
        ArrayList<SelectItem> listaSecciones = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cSeccion = session.createCriteria(Tblseccion.class);
        cSeccion.addOrder(Order.asc("nombreSeccion"));
        Iterator iter = cSeccion.list().iterator();
        while (iter.hasNext()) {
            Tblseccion s = (Tblseccion) iter.next();
            listaSecciones.add(new SelectItem(s.getIdSeccionLaboratorio(), s.getNombreSeccion(), ""));
        }
        session.close();
        return listaSecciones;
    }

    public void nuevo() {
        this.init();
    }

    /** 
     * Getters & Setters 
     */
    public ArrayList<Tblseccion> getSecs() {
        return secs;
    }

    public void setSecs(ArrayList<Tblseccion> secs) {
        this.secs = secs;
    }

    public Tblseccion getSec() {
        return sec;
    }

    public void setSec(Tblseccion sec) {
        this.sec = sec;
    }

    public Tblseccion getSelectedSeccion() {
        return selectedSeccion;
    }

    public void setSelectedSeccion(Tblseccion selectedSeccion) {
        this.selectedSeccion = selectedSeccion;
    }

    public Tblseccion[] getSelectedSecciones() {
        return selectedSecciones;
    }

    public void setSelectedSecciones(Tblseccion[] selectedSecciones) {
        this.selectedSecciones = selectedSecciones;
    }
}
