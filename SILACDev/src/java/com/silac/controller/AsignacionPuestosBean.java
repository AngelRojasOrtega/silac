/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblempleado;
import com.silac.model.Tblpersona;
import com.silac.model.Tblpuesto;
import com.silac.model.Tblpuestoempleado;
import com.silac.model.Tblsueldoempleado;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author siman
 */
@ManagedBean(name = "asignacionPuestosBean")
@ViewScoped
public class AsignacionPuestosBean implements Serializable {

    private Short selectedEmpleado;
    private Short selectedPuesto;
    private Boolean checkSueldo = false;
    private Boolean disabledCheckTipoSueldo = true;
    private Boolean showMessage = false;
    private Tblpuestoempleado puestoEmpleado = new Tblpuestoempleado();
    private Tblsueldoempleado sueldoEmpleado = new Tblsueldoempleado();
    private ArrayList empleados = new ArrayList();
    private ArrayList<Puesto> historial = new ArrayList<Puesto>();
    private Date hoy = new Date();
    //private Tblempleado empleado = new Tblempleado();

    /** Creates a new instance of AsignacionPuestosBean */
    public AsignacionPuestosBean() {
        this.init();
    }

    private void init() {
        this.selectedEmpleado = null;
        this.selectedPuesto = null;
        this.checkSueldo = false;
        this.disabledCheckTipoSueldo = true;
        this.showMessage = false;
        //this.empleado = new Tblempleado();
        this.puestoEmpleado = new Tblpuestoempleado();
        this.sueldoEmpleado = new Tblsueldoempleado();
        this.empleados = new ArrayList();
        this.historial = new ArrayList<Puesto>();
        this.hoy = new Date();
        populateEmpleados();
        populateHistorial();
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
                    temPe.setEstado(false);
                    session.saveOrUpdate(temPe);
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

            puestoEmpleado.setTblempleado(empleado);
            puestoEmpleado.setEstado(true);
            puestoEmpleado.setTblpuesto((Tblpuesto) session.load(Tblpuesto.class, this.selectedPuesto));
            session.save(puestoEmpleado);

            sueldoEmpleado.setFechaAsignacion(puestoEmpleado.getFechaAsignacion());
            sueldoEmpleado.setTblpuestoempleado(puestoEmpleado);
            sueldoEmpleado.setEstado(true);
            session.save(sueldoEmpleado);

            tx.commit();
            this.nuevo();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La asignación del nuevo puesto se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            //secs = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
        //return null;
    }

    public void populateEmpleados() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT e, p, pu, pe, se FROM Tblpersona p "
                + "JOIN p.tblempleados e "
                + "JOIN e.tblpuestoempleados pe "
                + "JOIN pe.tblpuesto pu "
                + "JOIN pe.tblsueldoempleados se "                
                + "ORDER BY e.codigoMarcacion, pe.fechaAsignacion, se.fechaAsignacion  ";
        empleados = null;
        empleados = (ArrayList) session.createQuery(sql).list();
        System.out.println(empleados);
        session.close();
    }
    
    public void populateHistorial() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Short id=0;
        if(selectedEmpleado != null){
         id=selectedEmpleado;
        }
        String sql = "SELECT e, p, pu, pe, se FROM Tblpersona p "
                + "JOIN p.tblempleados e "
                + "JOIN e.tblpuestoempleados pe "
                + "JOIN pe.tblpuesto pu "
                + "JOIN pe.tblsueldoempleados se " 
                + "WHERE e.codigoMarcacion = " + id
                + "ORDER BY e.codigoMarcacion, pe.fechaAsignacion, se.fechaAsignacion  ";
        historial = new ArrayList<Puesto>();
        Query query= session.createQuery(sql);
        
       Iterator iter = session.createQuery(sql).list().iterator();
       while (iter.hasNext()){
          Object[] obj = (Object[]) iter.next();
          Tblempleado e= (Tblempleado) obj[0];
          Tblpersona p = (Tblpersona) obj[1];
          Tblpuesto pu = (Tblpuesto) obj[2];
          Tblpuestoempleado pe = (Tblpuestoempleado) obj[3];
          Tblsueldoempleado se = (Tblsueldoempleado) obj[4];
          historial.add(new Puesto(e, p, pu, pe, se));
       }
       
        System.out.println(id);
        session.close();
    }

    
     public void renderTxtSueldo() {
        // HtmlSelectBooleanCheckbox chk = (HtmlSelectBooleanCheckbox) e.getSource();
        sueldoEmpleado = new Tblsueldoempleado();
        if (this.checkSueldo) {
            //String idPuesto = (String) chk.getAttributes().get("puesto");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Tblpuesto p = (Tblpuesto) session.load(Tblpuesto.class, this.selectedPuesto);
            sueldoEmpleado.setSueldo(p.getSueldoBase());
            session.close();
        }
    }

    public void renderChkTipoSueldo() {
        checkSueldo = false;
        sueldoEmpleado.setSueldo(0);
        if (selectedPuesto != null) {
            disabledCheckTipoSueldo = false;
        } else {
            disabledCheckTipoSueldo = true;
        }
    }
   

    public void nuevo() {
        this.init();
    }

    public Boolean getCheckSueldo() {
        return checkSueldo;
    }

    public void setCheckSueldo(Boolean checkSueldo) {
        this.checkSueldo = checkSueldo;
    }

    public Boolean getDisabledCheckTipoSueldo() {
        return disabledCheckTipoSueldo;
    }

    public void setDisabledCheckTipoSueldo(Boolean disabledCheckTipoSueldo) {
        this.disabledCheckTipoSueldo = disabledCheckTipoSueldo;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    
    
    public ArrayList getEmpleados() {
        return empleados;
    }
    
    

    public void setEmpleados(ArrayList empleados) {
        this.empleados = empleados;
    }

    public Tblpuestoempleado getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(Tblpuestoempleado puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }

    public Short getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Short selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public Short getSelectedPuesto() {
        return selectedPuesto;
    }

    public void setSelectedPuesto(Short selectedPuesto) {
        this.selectedPuesto = selectedPuesto;
    }

    public Boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(Boolean showMessage) {
        this.showMessage = showMessage;
    }

    public Tblsueldoempleado getSueldoEmpleado() {
        return sueldoEmpleado;
    }

    public void setSueldoEmpleado(Tblsueldoempleado sueldoEmpleado) {
        this.sueldoEmpleado = sueldoEmpleado;
    }

    public ArrayList<Puesto> getHistorial() {
        return historial;
    }

    public void setHistorial(ArrayList<Puesto> historial) {
        this.historial = historial;
    }

   
    
    
    public class Puesto implements Serializable {

        private Tblempleado emple;
        private Tblpersona person;
        private Tblpuesto puesto;
        private Tblpuestoempleado puestoEmple;
        private Tblsueldoempleado sueldoEmple;

        public Puesto(Tblempleado emple, Tblpersona person, Tblpuesto puesto, Tblpuestoempleado puestoEmple, Tblsueldoempleado sueldoEmple) {
            this.emple = emple;
            this.person = person;
            this.puesto = puesto;
            this.puestoEmple = puestoEmple;
            this.sueldoEmple = sueldoEmple;
        }

        public String determinarEstado() {
            String estado = "";
            if (puestoEmple.isEstado()) {
                estado = "Activo";}
            else{
                estado = "Inactivo";
            }            
            

            return estado;
        }

        public Tblempleado getEmple() {
            return emple;
        }

        public void setEmple(Tblempleado emple) {
            this.emple = emple;
        }

        public Tblpersona getPerson() {
            return person;
        }

        public void setPerson(Tblpersona person) {
            this.person = person;
        }

        public Tblpuesto getPuesto() {
            return puesto;
        }

        public void setPuesto(Tblpuesto puesto) {
            this.puesto = puesto;
        }

        public Tblpuestoempleado getPuestoEmple() {
            return puestoEmple;
        }

        public void setPuestoEmple(Tblpuestoempleado puestoEmple) {
            this.puestoEmple = puestoEmple;
        }

        public Tblsueldoempleado getSueldoEmple() {
            return sueldoEmple;
        }

        public void setSueldoEmple(Tblsueldoempleado sueldoEmple) {
            this.sueldoEmple = sueldoEmple;
        }

        
    }
    
}
