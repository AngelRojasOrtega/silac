/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tbldepartamento;
import com.silac.model.Tblmunicipio;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author siman
 */
@ManagedBean(name="municipioBean")
@ViewScoped
public class MunicipioBean implements Serializable{
private String selectedDepto;
private Tblmunicipio municipio = new Tblmunicipio();
private ArrayList municipios = new ArrayList();
private Object selectedMuni;
    /** Creates a new instance of MunicipioBean */
    public MunicipioBean() {
        this.init();
    }
    
    private void init(){
    this.municipio = new Tblmunicipio();
    this.selectedDepto = null;
    this.municipios = new ArrayList();
    this.selectedMuni = null;
    populateMunicipios();
    }
    
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

            Tbldepartamento d = (Tbldepartamento) session.load(Tbldepartamento.class, Short.parseShort(selectedDepto));
            this.municipio.setTbldepartamento(d);             
            
             session.save(municipio);
             System.out.println();
            

            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El municipio se registro satisfactoriamente"));
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
        String id = FacesUtil.getRequestParameter("idMunicipio");
        Tblmunicipio m = (Tblmunicipio) session.load(Tblmunicipio.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(m);
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
    
    public void populateMunicipios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT m, d from Tbldepartamento d JOIN d.tblmunicipios m ORDER BY d.idDepartamento";
        municipios = null;
        municipios = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    public void nuevo(){
        this.init();
    }
    
    public Tblmunicipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Tblmunicipio municipio) {
        this.municipio = municipio;
    }

    public String getSelectedDepto() {
        return selectedDepto;
    }

    public void setSelectedDepto(String selectedDepto) {
        this.selectedDepto = selectedDepto;
    }

    public ArrayList getMunicipios() {
        return municipios;
    }

    public void setMunicipios(ArrayList municipios) {
        this.municipios = municipios;
    }

    public Object getSelectedMuni() {
        return selectedMuni;
    }

    public void setSelectedMuni(Object selectedMuni) {
        this.selectedMuni = selectedMuni;
    }
 
    
    
}
