/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblempleado;
import com.silac.model.Tblpuestoempleado;
import com.silac.model.Tblsueldoempleado;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author siman
 */
@ManagedBean(name = "actualizarSalarioBean")
@ViewScoped
public class ActualizarSalario implements Serializable {

    private Short selectedEmpleado;
    private Tblsueldoempleado sueldoEmpleado = new Tblsueldoempleado();
    private Date hoy = new Date();

    /** Creates a new instance of ActualizarSalario */
    public ActualizarSalario() {
        this.init();
    }

    private void init() {
        this.selectedEmpleado = null;
        this.sueldoEmpleado = new Tblsueldoempleado();
        this.hoy = new Date();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();


            Tblempleado empleado = (Tblempleado) session.load(Tblempleado.class, this.selectedEmpleado);
            System.out.println(selectedEmpleado);

            Iterator iter = empleado.getTblpuestoempleados().iterator();

            while (iter.hasNext()) {
                Tblpuestoempleado pe = (Tblpuestoempleado) iter.next();

                if (pe.isEstado());
                {
                    Tblpuestoempleado temPe = (Tblpuestoempleado) session.load(Tblpuestoempleado.class, pe.getIdPuestoEmpleado());
                    Iterator itera = temPe.getTblsueldoempleados().iterator();
                    while (itera.hasNext()) {
                        Tblsueldoempleado se = (Tblsueldoempleado) itera.next();

                        if (se.isEstado());
                        {
                            Tblsueldoempleado temSe = (Tblsueldoempleado) session.load(Tblsueldoempleado.class, se.getIdSueldoEmpleado());
                            temSe.setEstado(false);
                            session.saveOrUpdate(temSe);
                        }

                    }

                }

            }
            
            Criteria cPuesto = session.createCriteria(Tblpuestoempleado.class);
            cPuesto.add(Restrictions.eq("estado", true));
            cPuesto.add(Restrictions.eq("tblempleado", empleado));
            Tblpuestoempleado temP =  (Tblpuestoempleado) cPuesto.uniqueResult();
            
            sueldoEmpleado.setTblpuestoempleado(temP);
            sueldoEmpleado.setFechaAsignacion(temP.getFechaAsignacion());
            sueldoEmpleado.setEstado(true);
            session.save(sueldoEmpleado);            

            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La asignación del nuevo sueldo se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            //secs = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
        //return null;
    }

    public void nuevo() {
        this.init();
    }

    public Short getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Short selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public Tblsueldoempleado getSueldoEmpleado() {
        return sueldoEmpleado;
    }

    public void setSueldoEmpleado(Tblsueldoempleado sueldoEmpleado) {
        this.sueldoEmpleado = sueldoEmpleado;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }
    
    
}
