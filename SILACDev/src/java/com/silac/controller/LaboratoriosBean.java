/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tbllaboratorio;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
@ManagedBean(name = "laboratoriosBean")
@RequestScoped
public class LaboratoriosBean {

    private Tbllaboratorio lab = new Tbllaboratorio();
    private Tbllaboratorio selectedLab;
    private ArrayList<Tbllaboratorio> labs;

    /** Creates a new instance of LaboratoriosBean */
    public LaboratoriosBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.lab = new Tbllaboratorio();
        this.selectedLab = null;
        this.labs = new ArrayList<Tbllaboratorio>();
        this.populateLaboratorios();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(lab);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El laboratorio se registro satisfactoriamente"));
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
        String id = FacesUtil.getRequestParameter("idLab");
        Tbllaboratorio l = (Tbllaboratorio) session.load(Tbllaboratorio.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(l);
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

    public void populateLaboratorios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cLab = session.createCriteria(Tbllaboratorio.class);
        cLab.addOrder(Order.asc("nombreLaboratorio"));
        this.labs = (ArrayList<Tbllaboratorio>) cLab.list();
        session.close();
    }

    /**
     * Getters & Setters
     */
    public Tbllaboratorio getLab() {
        return lab;
    }

    public void setLab(Tbllaboratorio lab) {
        this.lab = lab;
    }

    public ArrayList<Tbllaboratorio> getLabs() {
        return labs;
    }

    public void setLabs(ArrayList<Tbllaboratorio> labs) {
        this.labs = labs;
    }

    public Tbllaboratorio getSelectedLab() {
        return selectedLab;
    }

    public void setSelectedLab(Tbllaboratorio selectedLab) {
        this.selectedLab = selectedLab;
    }
}
