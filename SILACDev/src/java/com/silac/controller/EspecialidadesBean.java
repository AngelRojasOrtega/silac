/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblespecialidad;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
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
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.primefaces.event.RowEditEvent;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "especialidadesBean")
@RequestScoped
public class EspecialidadesBean {

    private ArrayList<Tblespecialidad> especialidades;
    private Tblespecialidad especialidad = new Tblespecialidad();
    private Tblespecialidad selectedEspecialidad;
    private Tblespecialidad[] selectedEspecialidades;

    /** Creates a new instance of EspecialidadesBean */
    public EspecialidadesBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.especialidades = new ArrayList<Tblespecialidad>();
        this.especialidad = new Tblespecialidad();
        this.selectedEspecialidad = null;
        this.selectedEspecialidades = null;
        this.populateEspecialidad();
    }

    /**Crea una nueva especialidad en la base de datos.*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(especialidad);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La especialidad se registro satisfactoriamente"));
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
        String id = FacesUtil.getRequestParameter("idEspecialidad");
        Tblespecialidad es = (Tblespecialidad) session.load(Tblespecialidad.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(es);
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

    public void populateEspecialidad() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cEspecialidad = session.createCriteria(Tblespecialidad.class);
        cEspecialidad.addOrder(Order.asc("nombreEspecialidad"));
        this.especialidades = (ArrayList<Tblespecialidad>) cEspecialidad.list();
        session.close();
    }

    public void onEditRow(RowEditEvent event) {
        Tblespecialidad editEspecialidad = (Tblespecialidad) event.getObject();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tblespecialidad es = (Tblespecialidad) session.load(Tblespecialidad.class, editEspecialidad.getIdEspecialidad());
            es.setNombreEspecialidad(editEspecialidad.getNombreEspecialidad());
            es.setCodigoEspecialidad(editEspecialidad.getCodigoEspecialidad());
            session.update(es);
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Los datos se actualizaron correctamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    /**
     * Carga la lista de especialidades que seran mostradas en un cuadro de lista desplegable
     * @return listaEspecialidades Lista de especialidades
     */
    public ArrayList<SelectItem> cargarListaEspecialidades() {
        ArrayList<SelectItem> listaEspecialidad = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cEspecialidad= session.createCriteria(Tblespecialidad.class);
        cEspecialidad.addOrder(Order.asc("nombreEspecialidad"));
        Iterator iterModalidad = cEspecialidad.list().iterator();
        while (iterModalidad.hasNext()) {
            Tblespecialidad e = (Tblespecialidad) iterModalidad.next();
            listaEspecialidad.add(new SelectItem(e.getIdEspecialidad(), e.getNombreEspecialidad(), ""));
        }
        session.close();
        return listaEspecialidad;
    }

    /**
     * Getters & Setters
     */
    public Tblespecialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Tblespecialidad especialidad) {
        this.especialidad = especialidad;
    }

    public ArrayList<Tblespecialidad> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(ArrayList<Tblespecialidad> especialidades) {
        this.especialidades = especialidades;
    }

    public Tblespecialidad getSelectedEspecialidad() {
        return selectedEspecialidad;
    }

    public void setSelectedEspecialidad(Tblespecialidad selectedEspecialidad) {
        this.selectedEspecialidad = selectedEspecialidad;
    }

    public Tblespecialidad[] getSelectedEspecialidades() {
        return selectedEspecialidades;
    }

    public void setSelectedEspecialidades(Tblespecialidad[] selectedEspecialidades) {
        this.selectedEspecialidades = selectedEspecialidades;
    }
}
