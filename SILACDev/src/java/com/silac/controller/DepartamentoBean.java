/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tbldepartamento;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author siman
 */
@ManagedBean(name="departamentoBean")
@ViewScoped
public class DepartamentoBean implements Serializable {
    
    private Tbldepartamento departamento = new Tbldepartamento();
    private List<Tbldepartamento> deptos;
    private Object selectedDepto;

    /** Creates a new instance of DepartamentoBean */
    public DepartamentoBean() {
        this.init();
    }
    
    private void init(){
    this.departamento = new Tbldepartamento();
    this.selectedDepto = null;
    populateDeptos();
    }
    
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

             
             session.save(departamento);
             System.out.println(departamento);
            

            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El departamento se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            //secs = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
        //return null;
    }
    
    
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idDepartamento");
        Tbldepartamento d = (Tbldepartamento) session.load(Tbldepartamento.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(d);
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

    private void populateDeptos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT d from Tbldepartamento d ORDER BY d.idDepartamento";
        deptos = null;
        Query query = session.createQuery(sql);
        deptos = (ArrayList) session.createQuery(sql).list();
        session.close();
    }
    
    public void nuevo(){
        this.init();
    }
    
    public Tbldepartamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Tbldepartamento departamento) {
        this.departamento = departamento;
    }

    public List<Tbldepartamento> getDeptos() {
        return deptos;
    }

    public void setDeptos(List<Tbldepartamento> deptos) {
        this.deptos = deptos;
    }

    public Object getSelectedDepto() {
        return selectedDepto;
    }

    public void setSelectedDepto(Object selectedDepto) {
        this.selectedDepto = selectedDepto;
    }
    
    
    
    
}
