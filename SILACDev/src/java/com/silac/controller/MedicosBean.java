/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblmedico;
import com.silac.model.Tblpersona;
import com.silac.model.Tblseccion;
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
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "medicosBean")
@RequestScoped
public class MedicosBean implements Serializable {

    private ArrayList<Medico> medicos;
    private Tblpersona persona = new Tblpersona();
    private Tblmedico medico = new Tblmedico();
    private Medico selectedMedico;

    /** Creates a new instance of MedicosBean */
    public MedicosBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.medicos = new ArrayList<Medico>();
        this.persona = new Tblpersona();
        this.medico = new Tblmedico();
        this.selectedMedico = null;
        this.populateMedicos();
    }

    public String crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(persona);
            medico.setTblpersona(persona);
            session.save(medico);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El medico se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
        return null;
    }

    public void nuevo() {
        this.init();
    }

    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            String id = FacesUtil.getRequestParameter("idMedico");
            Tblmedico tempMedico = (Tblmedico) session.load(Tblmedico.class, Integer.parseInt(id));
            session.delete(tempMedico);
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

    public void populateMedicos() {
        this.medicos  = new ArrayList<Medico>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT p, m from Tblmedico m join m.tblpersona p GROUP BY m.codMedico ORDER BY m.idMedico";
        Iterator iterMedico = session.createQuery(sql).list().iterator();
        String nombre = "";
        while (iterMedico.hasNext()) {
            Object[] obj = (Object[]) iterMedico.next();
            Tblpersona p = (Tblpersona) obj[0];
            Tblmedico m = (Tblmedico) obj[1];
            nombre = FacesUtil.emptyString(p.getPrimerNombre()) + " " + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " " + FacesUtil.emptyString(p.getSegundoApellido());
            this.medicos.add(new Medico(m, nombre));
        }
        session.close();
    }

    /**
     * Carga la lista de medicos que seran mostrados en un cuadro de lista desplegable
     * @return listaMedicos Lista de medicos
     */
    public ArrayList<SelectItem> cargarListaMedicos() {
        ArrayList<SelectItem> listaMedicos = new ArrayList<SelectItem>();
        this.populateMedicos();
        Iterator iterMedicos = this.medicos.iterator();
        while (iterMedicos.hasNext()) {
            Medico m = (Medico) iterMedicos.next();
            listaMedicos.add(new SelectItem(m.getMedico().getIdMedico(), m.getNombre(), ""));
        }
        return listaMedicos;
    }

    /**
     * Getters & Setters
     */
    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    public void setMedicos(ArrayList<Medico> medicos) {
        this.medicos = medicos;
    }

    public Tblmedico getMedico() {
        return medico;
    }

    public void setMedico(Tblmedico medico) {
        this.medico = medico;
    }

    public Tblpersona getPersona() {
        return persona;
    }

    public void setPersona(Tblpersona persona) {
        this.persona = persona;
    }

    public Medico getSelectedMedico() {
        return selectedMedico;
    }

    public void setSelectedMedico(Medico selectedMedico) {
        this.selectedMedico = selectedMedico;
    }

    public class Medico implements Serializable {

        private String nombre;
        private Tblmedico medico;

        public Medico(Tblmedico medico, String nombre) {
            this.nombre = nombre;
            this.medico = medico;
        }

        public Tblmedico getMedico() {
            return medico;
        }

        public void setMedico(Tblmedico medico) {
            this.medico = medico;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
