/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblcapacitacioncontinua;
import com.silac.model.Tblempleado;
import com.silac.model.TblempleadoCapacitacion;
import com.silac.model.TblempleadoCapacitacionId;
import com.silac.model.Tblinstitucion;
import com.silac.model.Tblpersona;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DualListModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "asistenciaCapBean")
@ViewScoped
public class AsistenciaCapBean implements Serializable {

    private Tblcapacitacioncontinua capacitacion = new Tblcapacitacioncontinua();
    private Short selectedInstitucion;
    private DualListModel<Empleado> listaEmpleados;
    private int horaInicio;
    private int minsInicio;
    private int am_pmInicio;
    private int horaFinal;
    private int minsFinal;
    private int am_pmFinal;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private boolean renderSchedule;
    private ScheduleModel eventModel;
    private Date minDate;
    private String tipo;
    private ArrayList listaCapacitacion = new ArrayList();
    private Object[] selectedCapacitacion;
    private String selectedTipoExpor;
    private String url;
    private String buscarCapacitacion;
    private int anio;
    private ArrayList<SelectItem> listaCapacitaciones;
    private Long selectedIdCapacitacion;

    /** Crea una nueva instancia de AsistenciaCapBean */
    public AsistenciaCapBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.capacitacion = new Tblcapacitacioncontinua();
        this.selectedInstitucion = null;
        this.listaEmpleados = new DualListModel<Empleado>();
        this.horaInicio = 0;
        this.minsInicio = 0;
        this.am_pmInicio = 0;
        this.horaFinal = 0;
        this.minsFinal = 0;
        this.am_pmFinal = 0;
        this.cargarListaEmpleados();
        this.cargarEventModel();
        this.renderSchedule = false;
        this.tipo = null;
        this.selectedCapacitacion = null;
        this.cargarListaCapacitaciones();
        this.selectedTipoExpor = null;
        this.buscarCapacitacion = null;
        this.anio = 0;
        this.listaCapacitaciones = new ArrayList<SelectItem>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        this.anio = Integer.parseInt(sdf.format(new Date()));
        this.cargarListaCapacitacion();
        if (!this.listaCapacitaciones.isEmpty()) {
            this.selectedIdCapacitacion = (Long) this.listaCapacitaciones.get(0).getValue();
        } else {
            this.selectedIdCapacitacion = null;
        }
        this.cargarListaCapacitaciones();
    }

    /*
     * Crea un nuevo registro en tblcapacitacioncontinua y registra a los empleados que participaran
     * en la capacitación.
     */
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

            //Establece la fecha y hora de inicio de la capacitacion.
            Calendar cInicio = Calendar.getInstance();
            cInicio.setTime(this.capacitacion.getFechaInicio());
            cInicio.set(Calendar.HOUR, this.horaInicio);
            cInicio.set(Calendar.MINUTE, this.minsInicio);
            cInicio.set(Calendar.SECOND, 0);
            cInicio.set(Calendar.AM_PM, this.am_pmInicio);

            //Establece la fecha y hora de finalizacion de la capacitacion.
            Calendar cFinal = Calendar.getInstance();
            cFinal.setTime(this.capacitacion.getFechaFinal());
            cFinal.set(Calendar.HOUR, this.horaFinal);
            cFinal.set(Calendar.MINUTE, this.minsFinal);
            cFinal.set(Calendar.SECOND, 0);
            cFinal.set(Calendar.AM_PM, this.am_pmFinal);

            this.capacitacion.setFechaInicio(cInicio.getTime());
            this.capacitacion.setFechaFinal(cFinal.getTime());

            Tblinstitucion i = (Tblinstitucion) session.load(Tblinstitucion.class, this.selectedInstitucion);
            this.capacitacion.setTblinstitucion(i);

            //Almacena los datos de la capactiacion.
            session.save(this.capacitacion);

            //Almacena la lista de empleados que participaran en la capactiacion.
            Iterator iterTarget = this.listaEmpleados.getTarget().iterator();
            if (iterTarget.hasNext()) {
                Empleado e;
                Tblempleado tempEmp;
                while (iterTarget.hasNext()) {
                    e = (Empleado) iterTarget.next();
                    tempEmp = (Tblempleado) session.load(Tblempleado.class, e.getCodigo());

                    TblempleadoCapacitacionId id = new TblempleadoCapacitacionId();
                    id.setCodigoMarcacion(tempEmp.getCodigoMarcacion());
                    id.setIdCapacitacion(this.capacitacion.getIdCapacitacion());

                    TblempleadoCapacitacion empCap = new TblempleadoCapacitacion();
                    empCap.setTipo(tipo);
                    empCap.setTblcapacitacioncontinua(this.capacitacion);
                    empCap.setTblempleado(tempEmp);
                    empCap.setId(id);
                    session.save(empCap);
                }
            }
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "La capacitacion se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }// Fin crear

    /**Carga la lista de empleados que sera mostrada en cboEmpleados*/
    private void cargarListaEmpleados() {
        this.listaEmpleados = new DualListModel<Empleado>();
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        List<Empleado> empleadosSource = new ArrayList<Empleado>();
        List<Empleado> empleadosTarget = new ArrayList<Empleado>();
        String nombre = "";

        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT p, e from Tblempleado e join e.tblpersona p GROUP BY e.codigoMarcacion ORDER BY p.primerApellido";
        result = (ArrayList) session.createQuery(sql).list();

        for (Object[] r : result) {
            Tblpersona p = (Tblpersona) r[0];
            Tblempleado e = (Tblempleado) r[1];
            nombre = FacesUtil.emptyString(p.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(p.getSegundoApellido());
            empleadosSource.add(new Empleado(nombre, e.getCodigoMarcacion()));
        }
        this.listaEmpleados = new DualListModel<Empleado>(empleadosSource, empleadosTarget);
        session.close();
    }//Fin cargarListaEmpleados

    public void cargarEventModel() {
        eventModel = new DefaultScheduleModel();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cEvento = session.createCriteria(Tblcapacitacioncontinua.class);
        cEvento.addOrder(Order.asc("fechaInicio"));
        Iterator iterEvento = cEvento.list().iterator();
        while (iterEvento.hasNext()) {
            Tblcapacitacioncontinua tempCap = (Tblcapacitacioncontinua) iterEvento.next();
            eventModel.addEvent(new DefaultScheduleEvent(tempCap.getNombreEvento(), tempCap.getFechaInicio(), tempCap.getFechaFinal(), tempCap.getIdCapacitacion().toString()));
        }
        session.close();
    }

    public ArrayList<SelectItem> cargarListaTipo() {
        ArrayList<SelectItem> listaTipo = new ArrayList<SelectItem>();
        listaTipo.add(new SelectItem("Facilitador", "Facilitador", ""));
        listaTipo.add(new SelectItem("Receptor", "Receptor", ""));
        return listaTipo;
    }

    public void onTabChange(TabChangeEvent event) {
        if (event.getTab().getId().equals("consulta")) {
            this.cargarEventModel();
        }
    }

    public void cargarListaCapacitaciones() {
        this.listaCapacitacion = new ArrayList();
        if (selectedIdCapacitacion != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String sql = "SELECT CONCAT(' ',IFNULL(p.primerNombre,' '),' ', IFNULL(p.segundoNombre,' '),' ',IFNULL( p.primerApellido,'  '),' ', IFNULL( p.segundoApellido,' ')), e, ec, c FROM Tblpersona p "
                    + "JOIN p.tblempleados e "
                    + "JOIN e.tblempleadoCapacitacions  ec "
                    + "JOIN ec.tblcapacitacioncontinua c "
                    + "WHERE c.idCapacitacion = " + this.selectedIdCapacitacion.longValue();
            Query queryCap = session.createQuery(sql);
            // queryCap.setParameter("idCapacitacion", this.selectedIdCapacitacion.longValue());

            this.listaCapacitacion = (ArrayList) session.createQuery(sql).list();
            session.close();
        }
    }

    public void cargarListaCapacitacion() {
        this.listaCapacitaciones = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT c FROM Tblcapacitacioncontinua c "
                + "WHERE YEAR(c.fechaInicio)= :fAnio";
        Query query = session.createQuery(sql);
        query.setParameter("fAnio", this.anio);
        Iterator iter = query.list().iterator();
        while (iter.hasNext()) {
            Tblcapacitacioncontinua c = (Tblcapacitacioncontinua) iter.next();
            this.listaCapacitaciones.add(new SelectItem(c.getIdCapacitacion(), c.getNombreEvento(), ""));
        }
        session.close();
    }

    public void onSelectRow() {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.url = contextPath + "/faces/createReportServlet?fileName=";
        String fileName = "capacitaciones";
        String tipo = this.selectedTipoExpor;
        String lista = "";
        for (int i = 0; i < this.selectedCapacitacion.length; i++) {
            Object[] obj = (Object[]) this.selectedCapacitacion[i];
            if (i != this.selectedCapacitacion.length - 1) {
                lista = lista + ((Tblempleado) obj[1]).getCodigoMarcacion() + ",";
            } else {
                lista = lista + ((Tblempleado) obj[1]).getCodigoMarcacion();
            }
        }
        String params = "&tipo=" + tipo + "&lista=" + lista + "&idCapacitacion=" + selectedIdCapacitacion;
        this.url = this.url + fileName + params;
    }

    public void onChangeAnio() {
        this.cargarListaCapacitacion();
        selectedIdCapacitacion = null;
    }

    public void onSelectCapacitaciones() {
        this.cargarListaCapacitaciones();
    }

    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {
        event = (DefaultScheduleEvent) selectEvent.getScheduleEvent();
    }

    public void onDateSelect(DateSelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", selectEvent.getDate(), selectEvent.getDate());
    }

    public void handleDateSelect(DateSelectEvent event) {
        this.minDate = event.getDate();
    }

    public void limpiar() {
        //this.buscarCapacitacion = null;
        //this.anio = 0;
    }

    /*
     * Getters & Setters
     */
    public Tblcapacitacioncontinua getCapacitacion() {
        return capacitacion;
    }

    public void setCapacitacion(Tblcapacitacioncontinua capacitacion) {
        this.capacitacion = capacitacion;
    }

    public Short getSelectedInstitucion() {
        return selectedInstitucion;
    }

    public void setSelectedInstitucion(Short selectedInstitucion) {
        this.selectedInstitucion = selectedInstitucion;
    }

    public DualListModel<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(DualListModel<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }

    public int getAm_pmFinal() {
        return am_pmFinal;
    }

    public void setAm_pmFinal(int am_pmFinal) {
        this.am_pmFinal = am_pmFinal;
    }

    public int getAm_pmInicio() {
        return am_pmInicio;
    }

    public void setAm_pmInicio(int am_pmInicio) {
        this.am_pmInicio = am_pmInicio;
    }

    public int getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(int horaFinal) {
        this.horaFinal = horaFinal;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getMinsFinal() {
        return minsFinal;
    }

    public void setMinsFinal(int minsFinal) {
        this.minsFinal = minsFinal;
    }

    public int getMinsInicio() {
        return minsInicio;
    }

    public void setMinsInicio(int minsInicio) {
        this.minsInicio = minsInicio;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }

    public boolean isRenderSchedule() {
        return renderSchedule;
    }

    public void setRenderSchedule(boolean renderSchedule) {
        this.renderSchedule = renderSchedule;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getBuscarCapacitacion() {
        return buscarCapacitacion;
    }

    public void setBuscarCapacitacion(String buscarCapacitacion) {
        this.buscarCapacitacion = buscarCapacitacion;
    }

    public ArrayList getListaCapacitacion() {
        return listaCapacitacion;
    }

    public void setListaCapacitacion(ArrayList listaCapacitacion) {
        this.listaCapacitacion = listaCapacitacion;
    }

    public ArrayList<SelectItem> getListaCapacitaciones() {
        return listaCapacitaciones;
    }

    public void setListaCapacitaciones(ArrayList<SelectItem> listaCapacitaciones) {
        this.listaCapacitaciones = listaCapacitaciones;
    }

    public Object[] getSelectedCapacitacion() {
        return selectedCapacitacion;
    }

    public void setSelectedCapacitacion(Object[] selectedCapacitacion) {
        this.selectedCapacitacion = selectedCapacitacion;
    }

    public Long getSelectedIdCapacitacion() {
        return selectedIdCapacitacion;
    }

    public void setSelectedIdCapacitacion(Long selectedIdCapacitacion) {
        this.selectedIdCapacitacion = selectedIdCapacitacion;
    }

    public String getSelectedTipoExpor() {
        return selectedTipoExpor;
    }

    public void setSelectedTipoExpor(String selectedTipoExpor) {
        this.selectedTipoExpor = selectedTipoExpor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /*
     * Clase auxiliar que se utiliza para almacenar el nombre del empleado y su codigo de marcación
     * que seran usados para generar la lista de empleados en cboEmpleados.
     */
    public class Empleado implements Serializable {

        private String nombre;
        private Short codigo;

        public Empleado(String nombre, Short codigo) {
            this.nombre = nombre;
            this.codigo = codigo;
        }

        public Short getCodigo() {
            return codigo;
        }

        public void setCodigo(Short codigo) {
            this.codigo = codigo;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
    }
}
