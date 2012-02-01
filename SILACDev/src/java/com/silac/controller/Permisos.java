/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblempleado;
import com.silac.model.Tblpermiso;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
import org.primefaces.event.DateSelectEvent;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "permisosBean")
@ViewScoped
public class Permisos implements Serializable {

    private Short selectedEmpleado;
    private String selectedTipo;
    private String otro;
    private Tblpermiso permiso = new Tblpermiso();
    private boolean renderTipoLicencia = true;
    private boolean renderOtro = false;
    private boolean renderHoraInicio = true;
    private boolean renderHoraFinal = true;
    private boolean renderJornada = true;
    private int horaInicio;
    private int minsInicio;
    private int am_pmInicio;
    private int horaFinal;
    private int minsFinal;
    private int am_pmFinal;
    private ArrayList listaPermisos = new ArrayList();
    private Date minDate;
    private Date hoy = new Date();
    private String selectedTipoExp;
    private Object[] selectedPermisos;
    private String url;
    private String tipo;
    private Date fechaIni;
    private Date fechaFin;
    private boolean selectedTipoPermiso;

    /** Creates a new instance of Permisos */
    public Permisos() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.selectedEmpleado = null;
        this.selectedTipo = null;
        this.otro = null;
        this.permiso = new Tblpermiso();
        this.renderTipoLicencia = true;
        this.renderOtro = false;
        this.renderHoraInicio = true;
        this.renderHoraFinal = true;
        this.renderJornada = true;
        this.horaInicio = 0;
        this.minsInicio = 0;
        this.am_pmInicio = 0;
        this.horaFinal = 0;
        this.minsFinal = 0;
        this.am_pmFinal = 0;
        this.cargarListaPermisos();
        this.hoy = new Date();
        this.selectedTipoExp = null;
        this.selectedPermisos = null;
        this.tipo = null;
        this.fechaIni = null;
        this.fechaFin = null;
        this.selectedTipoPermiso = true;
    }

    public void nuevo() {
        this.init();
    }

    public void crear() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tblempleado e = (Tblempleado) session.load(Tblempleado.class, this.selectedEmpleado);
            this.permiso.setTblempleado(e);
            this.permiso.setFechaSolicitud(new Date());
            if (this.permiso.isTipoPermiso()) {
                this.permiso.setTipoLicencia("Ausentarse");
            }
            if (!this.permiso.isJornadaCompleta()) {
                Calendar cInicio = Calendar.getInstance();
                cInicio.setTime(this.permiso.getFechaInicio());
                cInicio.set(Calendar.HOUR, this.horaInicio);
                cInicio.set(Calendar.MINUTE, this.minsInicio);
                cInicio.set(Calendar.SECOND, 0);
                cInicio.set(Calendar.AM_PM, this.am_pmInicio);
                this.permiso.setFechaInicio(cInicio.getTime());

                Calendar cFinal = Calendar.getInstance();
                cFinal.setTime(this.permiso.getFechaFinal());
                cFinal.set(Calendar.HOUR, this.horaFinal);
                cFinal.set(Calendar.MINUTE, this.minsFinal);
                cFinal.set(Calendar.SECOND, 0);
                cFinal.set(Calendar.AM_PM, this.am_pmFinal);
                this.permiso.setFechaFinal(cFinal.getTime());
            }
            if (this.permiso.getMotivo() != null) {
                if (this.permiso.getMotivo().equals("Otro")) {
                    this.permiso.setMotivo(this.otro);
                }

            }
            session.save(this.permiso);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El permiso se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            String msg = "";
            if (e.getCause() != null) {
                msg = e.getCause().getMessage();
            } else {
                e.getMessage();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", msg));
        } finally {
            session.close();
        }
    }

    public void handleDateSelect(DateSelectEvent event) {
        this.minDate = event.getDate();
    }

    public void actualizarRenderTipoLicencia() {
        this.permiso.setTipoLicencia(null);
        this.renderJornada = true;
        this.renderHoraFinal = true;
        this.renderHoraInicio = true;
        this.permiso.setJornadaCompleta(false);
        if (this.permiso.isTipoPermiso()) {
            this.renderTipoLicencia = false;
        } else {
            this.renderTipoLicencia = true;
        }
    }

    public void actualizarRenderHorario() {
        if (this.permiso.isJornadaCompleta()) {
            this.renderHoraInicio = false;
            this.renderHoraFinal = false;
        } else {
            this.renderHoraInicio = true;
            this.renderHoraFinal = true;
        }
    }

    public void onSelectMotivo() {
        this.renderOtro = false;
        if (this.permiso.getMotivo() != null) {
            if (this.permiso.getMotivo().equals("Otro")) {
                this.renderOtro = true;
            }
        }
    }

    public void onSelectTipoLicencia() {
        this.permiso.setJornadaCompleta(false);
        if (this.permiso.getTipoLicencia() != null) {
            if (this.permiso.getTipoLicencia().equals("Retirarse")) {
                this.renderHoraInicio = true;
                this.renderHoraFinal = false;
                this.permiso.setJornadaCompleta(false);
                this.renderJornada = false;
            } else {
                this.renderHoraFinal = true;
                this.renderHoraInicio = true;
                this.renderJornada = true;
            }
        }
    }

    public void cargarListaPermisos() {
        String sInicio = "";
        String sFinal = "";
        this.listaPermisos = new ArrayList();
        Session session = HibernateUtil.getSessionFactory().openSession();

        if (this.fechaIni != null && this.fechaFin != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
            sInicio = sdf.format(this.fechaIni);
            sFinal = sdf.format(this.fechaFin);
            //query.setParameter("inicio", sInicio);
            //query.setParameter("final", sFinal);
            System.out.println(this.fechaIni + "'" + sInicio + "'");
            System.out.println(this.fechaFin + "'" + sFinal + "'");
        }
        //System.out.println(selectedTipoPermiso);
        String sql = "SELECT p, e, pe FROM Tblpersona p "
                + "JOIN p.tblempleados e "
                + "JOIN e.tblpermisos pe "
                + "WHERE (DATE(pe.fechaSolicitud) BETWEEN  '" + sInicio + "' AND '" + sFinal + "')"
                + "AND pe.tipoPermiso = " + this.selectedTipoPermiso;
        
        Query query = session.createQuery(sql);
        this.listaPermisos = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    public ArrayList<SelectItem> cargarListaTipoPermiso() {
        ArrayList<SelectItem> listaTipoPermisos = new ArrayList<SelectItem>();
        listaTipoPermisos.add(new SelectItem(false, "Menor a 5 días", ""));
        listaTipoPermisos.add(new SelectItem(true, "Mayor a 5 días", ""));
        return listaTipoPermisos;
    }

    public void onSelectRow() {
        String fileName;
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.url = contextPath + "/faces/createReportServlet?fileName=";
        if (this.selectedTipoPermiso) {
            fileName = "informePermiso";
        } else {
            fileName = "solicitudesLicencias";
        }
        String tipo = this.selectedTipoExp;
        String lista = "";
        for (int i = 0; i < this.selectedPermisos.length; i++) {
            Object[] obj = (Object[]) this.selectedPermisos[i];
            if (i != this.selectedPermisos.length - 1) {
                lista = lista + ((Tblpermiso) obj[2]).getIdPermiso() + ",";
            } else {
                lista = lista + ((Tblpermiso) obj[2]).getIdPermiso();
            }
        }
        String params = "&tipo=" + tipo + "&lista=" + lista + "&tipoPermiso=" + selectedTipoPermiso;
        this.url = this.url + fileName + params;

    }

    public String emptyString(String s) {
        String cadena = FacesUtil.emptyString(s);
        return cadena;
    }

    public String getStringDate(Date d) {
        String sDate = FacesUtil.getStringDate(d);
        return sDate;
    }

    public void limpiar() {
        this.fechaIni = null;
        this.fechaFin = null;
    }

    public String goceSueldo(Boolean b) {
        String cadena = "";
        if (b) {
            cadena = "si";
        } else {
            cadena = "no";
        }
        return cadena;
    }

    /**
     * Getters & Setters
     */
    public Short getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Short selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public Tblpermiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Tblpermiso permiso) {
        this.permiso = permiso;
    }

    public boolean isRenderTipoLicencia() {
        return renderTipoLicencia;
    }

    public void setRenderTipoLicencia(boolean renderTipoLicencia) {
        this.renderTipoLicencia = renderTipoLicencia;
    }

    public String getSelectedTipo() {
        return selectedTipo;
    }

    public void setSelectedTipo(String selectedTipo) {
        this.selectedTipo = selectedTipo;
    }

    public int getAm_pmInicio() {
        return am_pmInicio;
    }

    public void setAm_pmInicio(int am_pmInicio) {
        this.am_pmInicio = am_pmInicio;
    }

    public Object[] getSelectedPermisos() {
        return selectedPermisos;
    }

    public void setSelectedPermisos(Object[] selectedPermisos) {
        this.selectedPermisos = selectedPermisos;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getMinsInicio() {
        return minsInicio;
    }

    public void setMinsInicio(int minsInicio) {
        this.minsInicio = minsInicio;
    }

    public int getAm_pmFinal() {
        return am_pmFinal;
    }

    public void setAm_pmFinal(int am_pmFinal) {
        this.am_pmFinal = am_pmFinal;
    }

    public int getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(int horaFinal) {
        this.horaFinal = horaFinal;
    }

    public int getMinsFinal() {
        return minsFinal;
    }

    public void setMinsFinal(int minsFinal) {
        this.minsFinal = minsFinal;
    }

    public boolean isRenderOtro() {
        return renderOtro;
    }

    public void setRenderOtro(boolean renderOtro) {
        this.renderOtro = renderOtro;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public boolean isRenderHoraFinal() {
        return renderHoraFinal;
    }

    public void setRenderHoraFinal(boolean renderHoraFinal) {
        this.renderHoraFinal = renderHoraFinal;
    }

    public boolean isRenderHoraInicio() {
        return renderHoraInicio;
    }

    public void setRenderHoraInicio(boolean renderHoraInicio) {
        this.renderHoraInicio = renderHoraInicio;
    }

    public boolean isRenderJornada() {
        return renderJornada;
    }

    public void setRenderJornada(boolean renderJornada) {
        this.renderJornada = renderJornada;
    }

    public ArrayList getListaPermisos() {
        return listaPermisos;
    }

    public void setListaPermisos(ArrayList listaPermisos) {
        this.listaPermisos = listaPermisos;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public boolean isSelectedTipoPermiso() {
        return selectedTipoPermiso;
    }

    public void setSelectedTipoPermiso(boolean selectedTipoPermiso) {
        this.selectedTipoPermiso = selectedTipoPermiso;
    }

    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public String getSelectedTipoExp() {
        return selectedTipoExp;
    }

    public void setSelectedTipoExp(String selectedTipoExp) {
        this.selectedTipoExp = selectedTipoExp;
    }
}
