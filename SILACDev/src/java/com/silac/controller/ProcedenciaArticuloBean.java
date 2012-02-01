/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblprocedenciaarticulo;
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
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "procedenciaArticuloBean")
@ViewScoped
public class ProcedenciaArticuloBean implements Serializable {

    private List<Tblprocedenciaarticulo> pros;
    private Tblprocedenciaarticulo pro = new Tblprocedenciaarticulo();
    private Tblprocedenciaarticulo selectedPro;

    /** Crea nueva instancia de ProcedenciaArticuloBean */
    public ProcedenciaArticuloBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.pros = null;
        this.pro = new Tblprocedenciaarticulo();
        this.selectedPro = null;
        populateProcedenciaArticulo();
    }

    /**Obtine la procedencia de los artículos*/
    private void populateProcedenciaArticulo() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT p from Tblprocedenciaarticulo p ORDER BY p.idProcedencia";
        pros = null;
        Query query = session.createQuery(sql);
        pros = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    /**Crea una nueva procedencia*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(pro);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "El lugar de procedencia se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            pros = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    /**Elimina una procedencia*/
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idPro");
        Tblprocedenciaarticulo p = (Tblprocedenciaarticulo) session.load(Tblprocedenciaarticulo.class, Short.parseShort(id));



        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(p);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    /**
     * Getters & Setters
     */
    public Tblprocedenciaarticulo getPro() {
        return pro;
    }

    public void setPro(Tblprocedenciaarticulo pro) {
        this.pro = pro;
    }

    public List<Tblprocedenciaarticulo> getPros() {
        return pros;
    }

    public void setPros(List<Tblprocedenciaarticulo> pros) {
        this.pros = pros;
    }

    public Tblprocedenciaarticulo getSelectedPro() {
        return selectedPro;
    }

    public void setSelectedPro(Tblprocedenciaarticulo selectedPro) {
        this.selectedPro = selectedPro;
    }
}
