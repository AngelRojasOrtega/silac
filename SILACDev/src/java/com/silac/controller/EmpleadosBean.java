/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tbldomicilio;
import com.silac.model.Tblempleado;
import com.silac.model.Tblmunicipio;
import com.silac.model.Tblpersona;
import com.silac.model.Tblpuesto;
import com.silac.model.Tblpuestoempleado;
import com.silac.model.Tblsueldoempleado;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "empleadosBean")
@ViewScoped
public class EmpleadosBean implements Serializable {

    private Tblpersona persona = new Tblpersona();
    private Tblempleado empleado = new Tblempleado();
    private Tblpuestoempleado puestoEmpleado = new Tblpuestoempleado();
    private Tblsueldoempleado sueldoEmpleado = new Tblsueldoempleado();
    private Tbldomicilio domicilio = new Tbldomicilio();
    private ArrayList empleados = new ArrayList();
    private String selectedSexo;
    private Boolean checkSueldo = false;
    private Boolean disabledCheckTipoSueldo = true;
    private Boolean showMessage = false;
    private Object selectedEmpleado;
    private Short selectedPuesto;
    private Short selectedDepto;
    private Short SelectedMunicipio;
    private ArrayList<SelectItem> listaMunicipios;

    /** Creates a new instance of EmpleadosBean */
    public EmpleadosBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.persona = new Tblpersona();
        this.empleado = new Tblempleado();
        this.puestoEmpleado = new Tblpuestoempleado();
        this.sueldoEmpleado = new Tblsueldoempleado();
        this.domicilio = new Tbldomicilio();
        this.empleados = new ArrayList();
        this.selectedSexo = null;
        this.checkSueldo = false;
        this.disabledCheckTipoSueldo = true;
        this.showMessage = false;
        this.selectedEmpleado = null;
        this.selectedPuesto = null;
        this.selectedDepto = null;
        this.SelectedMunicipio = null;
        this.listaMunicipios = new ArrayList<SelectItem>();
        populateEmpleados();
    }

    /**Crea una nueva seccion en la base de datos.*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

            session.save(persona);

            DomicilioBean d = (DomicilioBean) FacesUtil.getBean("domicilioBean");
            domicilio.setTblmunicipio((Tblmunicipio) session.load(Tblmunicipio.class, this.SelectedMunicipio));
            domicilio.setTblpersona(persona);
            session.save(domicilio);

            empleado.setTblpersona(persona);
            session.save(empleado);

            puestoEmpleado.setTblempleado(empleado);
            puestoEmpleado.setEstado(true);
            puestoEmpleado.setTblpuesto((Tblpuesto) session.load(Tblpuesto.class, this.selectedPuesto));
            session.save(puestoEmpleado);

            sueldoEmpleado.setFechaAsignacion(puestoEmpleado.getFechaAsignacion());
            sueldoEmpleado.setTblpuestoempleado(puestoEmpleado);
            session.save(sueldoEmpleado);

            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El empleado se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            //secs = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
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
        String id = FacesUtil.getRequestParameter("idEmpleado");
        Tblempleado e = (Tblempleado) session.load(Tblempleado.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(e);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException ex) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", ex.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public ArrayList<SelectItem> listaEmpleados() {
        ArrayList<SelectItem> listaEmpleados = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        //Obtiene la lista de empleados
        String sql = "SELECT p, e from Tblempleado e join e.tblpersona p GROUP BY e.codigoMarcacion ORDER BY p.primerApellido";
        Query query = session.createQuery(sql);
        List result = (ArrayList) session.createQuery(sql).list();
        Iterator itEmpleado = result.iterator();
        while (itEmpleado.hasNext()) {
            Object[] o = (Object[]) itEmpleado.next();
            Tblpersona p = (Tblpersona) o[0];
            Tblempleado e = (Tblempleado) o[1];
            String nombre = FacesUtil.emptyString(p.getPrimerNombre()) + " " + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " " + FacesUtil.emptyString(p.getSegundoApellido());
            listaEmpleados.add(new SelectItem(new Short(e.getCodigoMarcacion()).toString(), nombre, ""));
        }
        session.close();
        return listaEmpleados;
    }
    
    public ArrayList<SelectItem> cargarlistaTecnicos(){
                ArrayList<SelectItem> listaEmpleados = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        //Obtiene la lista de empleados
        String sql = "SELECT p, e from Tblempleado e "
                + "JOIN e.tblpersona p "
                + "JOIN e.tblpuestoempleados pe "
                + "JOIN pe.tblpuesto pu "
                + "WHERE pe.estado = true AND "
                + "pu.idPuesto = 1"
                + "GROUP BY e.codigoMarcacion ORDER BY p.primerApellido";
        Query query = session.createQuery(sql);
        List result = (ArrayList) session.createQuery(sql).list();
        Iterator itEmpleado = result.iterator();
        while (itEmpleado.hasNext()) {
            Object[] o = (Object[]) itEmpleado.next();
            Tblpersona p = (Tblpersona) o[0];
            Tblempleado e = (Tblempleado) o[1];
            String nombre = FacesUtil.emptyString(p.getPrimerNombre()) + " " + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " " + FacesUtil.emptyString(p.getSegundoApellido());
            listaEmpleados.add(new SelectItem(new Short(e.getCodigoMarcacion()).toString(), nombre, ""));
        }
        session.close();
        return listaEmpleados;
    }

    public void populateEmpleados() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT e, p, pu, pe, se FROM Tblpersona p "
                + "JOIN p.tblempleados e "
                + "JOIN e.tblpuestoempleados pe "
                + "JOIN pe.tblpuesto pu "
                + "JOIN pe.tblsueldoempleados se "
                + "GROUP BY e.codigoMarcacion "
                + "ORDER BY e.codigoMarcacion";
        empleados = null;
        empleados = (ArrayList) session.createQuery(sql).list();
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

    public void onSelectDepto() {
        DomicilioBean d = (DomicilioBean) FacesUtil.getBean("domicilioBean");
        this.listaMunicipios = d.cargarListaMunicipios(this.selectedDepto);
    }

    /*
     * Getters & Setters
     */
    public String getSelectedSexo() {
        return selectedSexo;
    }

    public void setSelectedSexo(String selectedSexo) {
        this.selectedSexo = selectedSexo;
    }

    public Tbldomicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Tbldomicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Tblempleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Tblempleado empleado) {
        this.empleado = empleado;
    }

    public Tblpersona getPersona() {
        return persona;
    }

    public void setPersona(Tblpersona persona) {
        this.persona = persona;
    }

    public Tblpuestoempleado getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(Tblpuestoempleado puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }

    public Tblsueldoempleado getSueldoEmpleado() {
        return sueldoEmpleado;
    }

    public void setSueldoEmpleado(Tblsueldoempleado sueldoEmpleado) {
        this.sueldoEmpleado = sueldoEmpleado;
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

    public Boolean getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(Boolean showMessage) {
        this.showMessage = showMessage;
    }

    public ArrayList getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList empleados) {
        this.empleados = empleados;
    }

    public Object getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Object selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public Short getSelectedPuesto() {
        return selectedPuesto;
    }

    public void setSelectedPuesto(Short selectedPuesto) {
        this.selectedPuesto = selectedPuesto;
    }

    public Short getSelectedMunicipio() {
        return SelectedMunicipio;
    }

    public void setSelectedMunicipio(Short SelectedMunicipio) {
        this.SelectedMunicipio = SelectedMunicipio;
    }

    public ArrayList<SelectItem> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(ArrayList<SelectItem> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public Short getSelectedDepto() {
        return selectedDepto;
    }

    public void setSelectedDepto(Short selectedDepto) {
        this.selectedDepto = selectedDepto;
    }    
}
