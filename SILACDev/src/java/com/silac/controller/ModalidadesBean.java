/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblmodalidad;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@ManagedBean(name = "modalidadesBean")
@RequestScoped
public class ModalidadesBean implements Serializable {

    private Tblmodalidad modalidad = new Tblmodalidad();
    private Tblmodalidad selectedMod;
    private ArrayList<Tblmodalidad> mods;

    /** Creates a new instance of ModalidadesBean */
    public ModalidadesBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.modalidad = new Tblmodalidad();
        this.selectedMod = null;
        this.mods = new ArrayList<Tblmodalidad>();
        this.populateModalidad();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(modalidad);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La Modalidad se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevo() {
        this.init();
    }

    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idMod");
        Tblmodalidad m = (Tblmodalidad) session.load(Tblmodalidad.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(m);
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

    public void populateModalidad() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cModalidad = session.createCriteria(Tblmodalidad.class);
        cModalidad.addOrder(Order.asc("nombreModalidad"));
        this.mods = (ArrayList<Tblmodalidad>) cModalidad.list();
        session.close();
    }

    /**
     * Carga la lista de modalidades que seran mostrados en un cuadro de lista desplegable
     * @return listaModalidades Lista de articulso filtrados por categoria
     */
    public ArrayList<SelectItem> cargarListaModalidades() {
        ArrayList<SelectItem> listaModalidad = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cModalidad = session.createCriteria(Tblmodalidad.class);
        cModalidad.addOrder(Order.asc("nombreModalidad"));
        Iterator iterModalidad = cModalidad.list().iterator();
        while (iterModalidad.hasNext()) {
            Tblmodalidad m = (Tblmodalidad) iterModalidad.next();
            listaModalidad.add(new SelectItem(m.getIdModalidad(), m.getNombreModalidad(), ""));
        }
        session.close();
        return listaModalidad;
    }

    /**
     * Getters & Setters
     */
    public Tblmodalidad getModalidad() {
        return modalidad;
    }

    public void setModadalidad(Tblmodalidad modalidad) {
        this.modalidad = modalidad;
    }

    public ArrayList<Tblmodalidad> getMods() {
        return mods;
    }

    public void setMods(ArrayList<Tblmodalidad> mods) {
        this.mods = mods;
    }

    public Tblmodalidad getSelectedMod() {
        return selectedMod;
    }

    public void setSelectedMod(Tblmodalidad selectedMod) {
        this.selectedMod = selectedMod;
    }
}
