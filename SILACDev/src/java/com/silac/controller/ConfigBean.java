/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblestablecimiento;
import com.silac.model.Tblmunicipio;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Marin
 */
@ManagedBean(name = "configBean")
@ViewScoped
public class ConfigBean implements Serializable {

    
    private ArrayList<String> temas;
    private Tblestablecimiento establecimiento;
    private Short selectedMunicipio;
    private Short selectedDepto;
    private ArrayList<SelectItem> listaMunicipios;
    private boolean cargarEstablecimiento;
    private String selectedTema;
    /** Creates a new instance of ConfigBean */
    public ConfigBean() {        
        this.init();
      
    }

    private void init() {  
         temas=new ArrayList<String>();
        temas.add("blitzer");
        temas.add("bluesky");
        temas.add("cupertino");
        temas.add("dot-luv");
        temas.add("humanity");
        temas.add("redmond");
        temas.add("start");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT e FROM Tblestablecimiento e WHERE e.idEstablecimiento = 1"; 
        establecimiento = new Tblestablecimiento();
         this.selectedTema="redmond";
        if (session.createQuery(sql).uniqueResult() == null) {
          cargarEstablecimiento=false;
            this.establecimiento.setIdEstablecimiento(new Integer(1));
            System.out.println("es nulo");
           
        } else {
             cargarEstablecimiento=true;
            this.establecimiento = (Tblestablecimiento) session.createQuery(sql).uniqueResult();
              System.out.println("no es nulo");
              this.selectedTema=this.establecimiento.getTema();
              this.selectedMunicipio = this.establecimiento.getTblmunicipio().getIdMunicipio();
              this.selectedDepto = this.establecimiento.getTblmunicipio().getTbldepartamento().getIdDepartamento();
              this.onSelectDepto();
        }
        session.close();
    }

     
    
    public void crear() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException, javax.transaction.RollbackException, IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tblmunicipio municipio = (Tblmunicipio) session.load(Tblmunicipio.class, this.selectedMunicipio);
        this.establecimiento.setTblmunicipio(municipio);
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            if(cargarEstablecimiento){
                 this.establecimiento.setTema(selectedTema); 
                 session.saveOrUpdate(this.establecimiento);
                 
            }else{                
                this.establecimiento.setTema("redmond");    
                session.save(this.establecimiento);
            }
            UsuarioBean usuario = (UsuarioBean) FacesUtil.getBean("usuarioBean");
            usuario.setTheme(this.establecimiento.getTema());
            session.flush();
            session.clear();
            tx.commit();            
          try {
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ctx.redirect(ctxPath + "/faces/admon/frmConfig.xhtml" );
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El permiso se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            String msg = "";
            if (e.getCause() != null) {
                msg = e.getCause().getMessage();
            } else {
                e.getMessage();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }
    

    public void onSelectDepto() {
        DomicilioBean d = (DomicilioBean) FacesUtil.getBean("domicilioBean");
        this.listaMunicipios = d.cargarListaMunicipios(this.selectedDepto);
    }

    /** get and set*/
    public Tblestablecimiento getEstablecimiento() {
        return establecimiento;
    }

    public Short getSelectedMunicipio() {
        return selectedMunicipio;
    }

    public void setSelectedMunicipio(Short selectedMunicipio) {
        this.selectedMunicipio = selectedMunicipio;
    }

    public void setEstablecimiento(Tblestablecimiento establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Short getSelectedDepto() {
        return selectedDepto;
    }

    public void setSelectedDepto(Short selectedDepto) {
        this.selectedDepto = selectedDepto;
    }

    public ArrayList<SelectItem> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(ArrayList<SelectItem> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public ArrayList<String> getTemas() {
        return temas;
    }

    public void setTemas(ArrayList<String> temas) {
        this.temas = temas;
    }

    public String getSelectedTema() {
        return selectedTema;
    }

    public void setSelectedTema(String selectedTema) {
        this.selectedTema = selectedTema;
    }

    
    
    
    
}
