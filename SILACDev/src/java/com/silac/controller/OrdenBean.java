/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tbldomicilio;
import com.silac.model.Tblempleado;
import com.silac.model.Tblespecialidad;
import com.silac.model.Tblexamen;
import com.silac.model.TblexamenOrden;
import com.silac.model.Tblincidencia;
import com.silac.model.Tbllugtrabajo;
import com.silac.model.Tblmedico;
import com.silac.model.Tblmunicipio;
import com.silac.model.Tblorden;
import com.silac.model.Tblpaciente;
import com.silac.model.Tblpersona;
import com.silac.model.Tblseccion;
import com.silac.model.Tblservicio;
import com.silac.model.Tblsolicitudvih;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import org.hibernate.Session;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "ordenBean")
@ViewScoped
public class OrdenBean implements Serializable {

    private String numExpediente;
    private ArrayList<SelectItem> listaExamenes;
    private ArrayList<ExamenOrden> listaExamenesSolicitados;
    private ArrayList<SelectItem> listaMunicipios;
    private ArrayList<SelectItem> listaServicio;
    private ArrayList<SelectItem> listaTipoServicio;
    private boolean chkDomicilio;
    private boolean chkTrabajo;
    private boolean chkIdentificacion;
    private boolean chkMotivo;
    private Date hoy;
    private ExamenOrden selectedExamenSolicitado;
    private Integer selectedMedico;
    private Long selectedExamen;
    private Short selectedModalidad;
    private Short selectedServicio;
    private Short selectedTipoServicio;
    private Short selectedEspecialidad;
    private Short selectedSeccion;
    private Short selectedDepto;
    private Short selectedMunicipio;
    private String selectedMotivo;
    private String selectedTipoDocId;
    private Object[] selectedPersona;
    private TblexamenOrden examenSolicitado;
    private Tblpersona persona;
    private Tblpaciente paciente;
    private Tbldomicilio domicilio;
    private Tblorden orden;
    private Tbllugtrabajo lugarTrabajo;
    private Tblsolicitudvih solicitudvih;
    private ArrayList<Orden> listaOrdenes;
    private Orden selectedOrden;
    private String activeTab;
    private int cantidadOrdenesPendientes;
    private ArrayList<OrdenPendiente> listaOrdenesPendientes;
    private OrdenPendiente selectedOrdenPendiente;
    private OrdenPendiente tempSelection;
    private ArrayList<Object[]> listaPersonas;
    private Object[] selectedResultadoBusqueda;
    private boolean cargarPersona;
    private boolean cargarFechaNacimiento;
    private boolean cargarSexo;
    private boolean cargarPaciente;
    private boolean renderMotivo;

    /** Creates a new instance of OrdenBean */
    public OrdenBean() {
        this.init();
    }

    private void init() {
        this.numExpediente = "";
        this.listaTipoServicio = new ArrayList<SelectItem>();
        this.listaServicio = new ArrayList<SelectItem>();
        this.listaExamenes = new ArrayList<SelectItem>();
        this.listaExamenesSolicitados = new ArrayList<ExamenOrden>();
        this.listaMunicipios = new ArrayList<SelectItem>();
        this.chkDomicilio = false;
        this.chkIdentificacion = false;
        this.chkMotivo = false;
        this.chkTrabajo = false;
        this.hoy = new Date();
        this.selectedModalidad = null;
        this.selectedServicio = null;
        this.selectedTipoServicio = null;
        this.selectedEspecialidad = null;
        this.selectedMedico = null;
        this.selectedSeccion = null;
        this.selectedExamen = null;
        this.selectedMotivo = null;
        this.selectedMunicipio = null;
        this.selectedDepto = null;
        this.selectedTipoDocId = null;
        this.selectedPersona = null;
        this.examenSolicitado = new TblexamenOrden();
        this.selectedExamenSolicitado = new ExamenOrden();
        this.selectedExamenSolicitado.setIncidencia(new Tblincidencia());
        this.persona = new Tblpersona();
        this.paciente = new Tblpaciente();
        this.domicilio = new Tbldomicilio();
        this.orden = new Tblorden();
        this.lugarTrabajo = new Tbllugtrabajo();
        this.solicitudvih = new Tblsolicitudvih();
        this.listaOrdenes = new ArrayList<Orden>();
        this.selectedOrden = null;
        this.activeTab = "";
        this.cantidadOrdenesPendientes = 0;
        this.listaOrdenesPendientes = new ArrayList<OrdenPendiente>();
        this.selectedOrdenPendiente = null;
        this.tempSelection = null;
        this.populateOrdenes();
        this.cargarOrdenesPendientes();
        this.renderMotivo = false;
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

            session.saveOrUpdate(this.persona);

            //Almacena los datos del paciente.
            if (!this.cargarPaciente) {
                this.paciente.setTblpersona(this.persona);
                session.saveOrUpdate(this.paciente);
            }

            //Almacena los datos de las ordenes.
            this.orden.setFechaRecepcion(new Date());
            this.orden.setTblpaciente(paciente);
            //Asigna los servicios a la orden
            Tblservicio s = (Tblservicio) session.load(Tblservicio.class, this.selectedServicio);
            this.orden.setTblservicio(s);
            //Asigna el medico a la orden
            Tblmedico m = (Tblmedico) session.load(Tblmedico.class, this.selectedMedico);
            this.orden.setTblmedico(m);
            //Asigna la especialidad a la orden.
            Tblespecialidad es = (Tblespecialidad) session.load(Tblespecialidad.class, this.selectedEspecialidad);
            this.orden.setTblespecialidad(es);

            //Almacena los datos del domicilio del paciente
            if (this.chkDomicilio) {
                this.domicilio.setTblpersona(persona);
                Tblmunicipio mun = (Tblmunicipio) session.load(Tblmunicipio.class, this.selectedMunicipio);
                this.domicilio.setTblmunicipio(mun);
                session.save(this.domicilio);
                Set<Tbldomicilio> domicilios = new HashSet<Tbldomicilio>();
                domicilios.add(domicilio);
                this.orden.setTbldomicilios(domicilios);
            }

            //Almacena los datos laborales del paciente
            if (this.chkTrabajo) {
                this.lugarTrabajo.setTblpersona(persona);
                session.save(this.lugarTrabajo);
                Set<Tbllugtrabajo> trabajos = new HashSet<Tbllugtrabajo>();
                trabajos.add(lugarTrabajo);
                this.orden.setTbllugtrabajos(trabajos);
            }

            //Busca el id del empleado logueado en el sistema y lo asigna a la orden.
            UsuarioBean u = (UsuarioBean) FacesUtil.getBean("usuarioBean");
            Tblempleado e = (Tblempleado) session.load(Tblempleado.class, u.getIdEmpleado());
            this.orden.setTblempleado(e);
            session.saveOrUpdate(this.orden);

            //Almacena la lista de examenes asignado a la orden.

            Iterator iterExamen = this.listaExamenesSolicitados.iterator();
            while (iterExamen.hasNext()) {
                ExamenOrden eo = (ExamenOrden) iterExamen.next();
                TblexamenOrden tempEo = eo.getExamenOrden();

                //Asigna la orden a los examenes solicitados
                tempEo.setTblorden(this.orden);
                tempEo.setNumeroControl(this.generarNumControl(tempEo.getTblexamen().getTblseccion().getIdSeccionLaboratorio(), session));
                tempEo.setEstado("Pendiente");

                //Almacena las incidencias en caso que las muestras sean rechazadas.
                session.save(tempEo);
                Set<TblexamenOrden> examenesOrden = new HashSet<TblexamenOrden>();
                examenesOrden.add(tempEo);
                if (eo.getIncidencia().getMotivo() != null) {
                    tempEo.setEstado("Rechazada");
                    Tblincidencia in = eo.getIncidencia();
                    in.setFecha(new Date());
                    in.setTblexamenOrdens(examenesOrden);
                    session.save(in);
                }
            }

            //Alamcena el motivo del examen solicitado
            if (this.chkMotivo) {
                this.solicitudvih.setTblorden(this.orden);
                session.save(this.solicitudvih);
            }
            session.flush();
            session.clear();
            tx.commit();
            this.init();
            this.activeTab = "tabViewOrden.selectTab(1)";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:",
                    "La solicitud se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:",
                    e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevo() {
        this.init();
    }

    public void buscarPersona() {
        PersonaBean p = (PersonaBean) FacesUtil.getBean("personasBean");
        this.listaPersonas = p.buscar();
    }

    public void limpiar() {
        PersonaBean p = (PersonaBean) FacesUtil.getBean("personasBean");
        p.setBuscarDocId(null);
        p.setBuscarExpediente(null);
        p.setBuscarNombre(null);
        this.persona = new Tblpersona();
        this.paciente = new Tblpaciente();
        this.cargarPaciente = false;
        this.cargarPersona = false;
        this.cargarSexo = false;
        this.cargarFechaNacimiento = false;
        this.chkDomicilio = false;
        this.chkIdentificacion = false;
        this.chkMotivo = false;
        this.chkTrabajo = false;
        this.renderMotivo = false;
        this.listaPersonas = new ArrayList<Object[]>();
    }

    public void asignarExamen() {
        boolean exist = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tblexamen examen = (Tblexamen) session.load(Tblexamen.class, this.selectedExamen);
        Iterator iterExamen = this.listaExamenesSolicitados.iterator();
        while (iterExamen.hasNext()) {
            ExamenOrden o = (ExamenOrden) iterExamen.next();
            if (o.getIdExamen().intValue() == this.selectedExamen.intValue()) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            this.examenSolicitado.setTblexamen(examen);
            this.listaExamenesSolicitados.add(new ExamenOrden(examen.getIdExamen(),
                    examen.getCodigoExamen(),
                    examen.getNombreExamen(),
                    "cancel",
                    true,
                    false,
                    false,
                    this.examenSolicitado,
                    new Tblincidencia()));
        }
        session.close();
        this.selectedSeccion = null;
        this.selectedExamen = null;
        this.examenSolicitado = new TblexamenOrden();
        this.listaExamenes = new ArrayList<SelectItem>();
    }

    public long generarNumControl(Short idSeccion, Session session) {
        long numControl = 0;
        Tblseccion sec = (Tblseccion) session.load(Tblseccion.class, idSeccion);
        String periodo = sec.getReinicioNumeroControl();
        Map<String, Calendar> mPeriodo = this.getPeriodoReinicio(periodo);
        String sql = "SELECT max(eo.numeroControl) "
                + "FROM Tblseccion sec "
                + "JOIN sec.tblexamens ex "
                + "JOIN ex.tblexamenOrdens eo "
                + "JOIN eo.tblorden o "
                + "WHERE sec.idSeccionLaboratorio = :idSeccion "
                + "AND (o.fechaRecepcion BETWEEN :inicio AND :fin)";
        Query query = session.createQuery(sql);
        query.setParameter("idSeccion", idSeccion);
        query.setParameter("inicio", mPeriodo.get("inicio").getTime());
        query.setParameter("fin", mPeriodo.get("final").getTime());
        if (query.uniqueResult() != null) {
            numControl = (Long) query.uniqueResult();
        }
        return (numControl + 1);
    }

    public Map<String, Calendar> getPeriodoReinicio(String periodo) {
        Map<String, Calendar> mPeriodo = new HashMap<String, Calendar>();
        Calendar cHoy = Calendar.getInstance();
        cHoy.clear();
        cHoy.setTime(this.hoy);
        Calendar cFechaInicio = Calendar.getInstance();
        cFechaInicio.clear();
        Calendar cFechaFinal = Calendar.getInstance();
        cFechaInicio.setTime(this.hoy);
        cFechaInicio.set(Calendar.MILLISECOND, 0);
        cFechaInicio.set(Calendar.SECOND, 0);
        cFechaInicio.set(Calendar.MINUTE, 0);
        cFechaInicio.set(Calendar.HOUR_OF_DAY, 0);
        cFechaFinal.setTime(this.hoy);

        cFechaFinal.set(Calendar.MILLISECOND, 999);
        cFechaFinal.set(Calendar.SECOND, 59);
        cFechaFinal.set(Calendar.MINUTE, 59);
        cFechaFinal.set(Calendar.HOUR_OF_DAY, 23);
        if (periodo.equals("Diario")) {
        } else if (periodo.equals("Semanal")) {
            cFechaInicio.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cFechaFinal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            cFechaFinal.add(Calendar.DAY_OF_WEEK, 6);
        } else if (periodo.equals("Quincenal")) {
            cFechaInicio.set(Calendar.DAY_OF_MONTH, 1);
            cFechaFinal.set(Calendar.DAY_OF_MONTH, 15);
            if (cFechaFinal.compareTo(cHoy) < 0) {
                cFechaInicio.set(Calendar.DAY_OF_MONTH, 16);
                cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        } else if (periodo.equals("Mensual")) {
            cFechaInicio.set(Calendar.DAY_OF_MONTH, 1);
            cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else if (periodo.equals("Trimestral")) {
            Calendar cPrimerTrimestre = Calendar.getInstance();
            cPrimerTrimestre.clear();
            cPrimerTrimestre.setTime(cFechaFinal.getTime());
            cPrimerTrimestre.set(Calendar.MONTH, Calendar.MARCH);
            cPrimerTrimestre.set(Calendar.DAY_OF_MONTH, cPrimerTrimestre.getActualMaximum(Calendar.DAY_OF_MONTH));

            Calendar cSegundoTrimestre = Calendar.getInstance();
            cSegundoTrimestre.clear();
            cSegundoTrimestre.setTime(cFechaFinal.getTime());
            cSegundoTrimestre.set(Calendar.MONTH, Calendar.JUNE);
            cSegundoTrimestre.set(Calendar.DAY_OF_MONTH, cSegundoTrimestre.getActualMaximum(Calendar.DAY_OF_MONTH));

            Calendar cTercerTrimestre = Calendar.getInstance();
            cTercerTrimestre.clear();
            cTercerTrimestre.setTime(cFechaFinal.getTime());
            cTercerTrimestre.set(Calendar.MONTH, Calendar.SEPTEMBER);
            cTercerTrimestre.set(Calendar.DAY_OF_MONTH, cTercerTrimestre.getActualMaximum(Calendar.DAY_OF_MONTH));

            Calendar cCuartoTrimestre = Calendar.getInstance();
            cCuartoTrimestre.clear();
            cCuartoTrimestre.setTime(cFechaFinal.getTime());
            cCuartoTrimestre.set(Calendar.MONTH, Calendar.DECEMBER);
            cCuartoTrimestre.set(Calendar.DAY_OF_MONTH, cCuartoTrimestre.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (cPrimerTrimestre.compareTo(cHoy) > 0) {
                cFechaInicio.set(Calendar.MONTH, Calendar.JANUARY);
                cFechaInicio.set(Calendar.DAY_OF_MONTH, cFechaInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
                cFechaFinal.set(Calendar.MONTH, Calendar.MARCH);
                cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (cSegundoTrimestre.compareTo(cHoy) > 0) {
                cFechaInicio.set(Calendar.MONTH, Calendar.APRIL);
                cFechaInicio.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMinimum(Calendar.DAY_OF_MONTH));
                cFechaFinal.set(Calendar.MONTH, Calendar.JUNE);
                cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else if (cTercerTrimestre.compareTo(cHoy) > 0) {
                cFechaInicio.set(Calendar.MONTH, Calendar.JULY);
                cFechaInicio.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMinimum(Calendar.DAY_OF_MONTH));
                cFechaFinal.set(Calendar.MONTH, Calendar.SEPTEMBER);
                cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                cFechaInicio.set(Calendar.MONTH, Calendar.OCTOBER);
                cFechaInicio.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMinimum(Calendar.DAY_OF_MONTH));
                cFechaFinal.set(Calendar.MONTH, Calendar.DECEMBER);
                cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        } else if (periodo.equals("Semestral")) {
            Calendar cPrimerSemestre = Calendar.getInstance();
            cPrimerSemestre.clear();
            cPrimerSemestre.setTime(cFechaFinal.getTime());
            cPrimerSemestre.set(Calendar.MONTH, Calendar.JUNE);
            cPrimerSemestre.set(Calendar.DAY_OF_MONTH, cPrimerSemestre.getActualMaximum(Calendar.DAY_OF_MONTH));

            Calendar cSegundoSemestre = Calendar.getInstance();
            cSegundoSemestre.clear();
            cSegundoSemestre.setTime(cFechaFinal.getTime());
            cSegundoSemestre.set(Calendar.MONTH, Calendar.DECEMBER);
            cSegundoSemestre.set(Calendar.DAY_OF_MONTH, cSegundoSemestre.getActualMaximum(Calendar.DAY_OF_MONTH));

            if (cPrimerSemestre.compareTo(cHoy) > 0) {
                cFechaInicio.set(Calendar.MONTH, Calendar.JANUARY);
                cFechaInicio.set(Calendar.DAY_OF_MONTH, cFechaInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
                cFechaFinal.set(Calendar.MONTH, Calendar.JUNE);
                cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
            } else {
                cFechaInicio.set(Calendar.MONTH, Calendar.JULY);
                cFechaInicio.set(Calendar.DAY_OF_MONTH, cFechaInicio.getActualMinimum(Calendar.DAY_OF_MONTH));
                cFechaFinal.set(Calendar.MONTH, Calendar.DECEMBER);
                cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
        } else if (periodo.equals("Anual")) {
            cFechaInicio.set(Calendar.MONTH, Calendar.JANUARY);
            cFechaInicio.set(Calendar.DAY_OF_MONTH, cFechaInicio.getActualMinimum(Calendar.DAY_OF_MONTH));

            cFechaFinal.set(Calendar.MONTH, Calendar.DECEMBER);
            cFechaFinal.set(Calendar.DAY_OF_MONTH, cFechaFinal.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        mPeriodo.put("inicio", cFechaInicio);
        mPeriodo.put("final", cFechaFinal);
        return mPeriodo;
    }

    public void onClickCancel() {
        int i = this.listaExamenesSolicitados.indexOf(this.selectedExamenSolicitado);
        ExamenOrden eo = this.listaExamenesSolicitados.get(i);
        eo.setRenderMotivo(false);
        if (this.selectedExamenSolicitado.getAccion().equals("cancel")) {
            eo.setAccion("aceptar");
            eo.setRenderCmdAceptar(true);
            eo.setRenderCmdRechazar(false);
        } else {
            eo.setAccion("cancel");
            eo.setRenderCmdAceptar(false);
            eo.setRenderCmdRechazar(true);
            eo.setIncidencia(new Tblincidencia());
        }
        this.listaExamenesSolicitados.set(i, eo);
    }

    public void populateOrdenes() {
        this.listaOrdenes = new ArrayList<Orden>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT p, pa, o, eo, ex, s "
                + "FROM Tblpersona p "
                + "JOIN p.tblpacientes pa "
                + "JOIN pa.tblordens o "
                + "JOIN o.tblexamenOrdens eo "
                + "JOIN eo.tblexamen ex "
                + "JOIN ex.tblseccion s "
                + "ORDER BY o.fechaRecepcion DESC";
        Query query = session.createQuery(sql);
        Iterator iterOrden = query.list().iterator();
        while (iterOrden.hasNext()) {
            Object[] obj = (Object[]) iterOrden.next();
            Tblpersona p = (Tblpersona) obj[0];
            Tblpaciente pa = (Tblpaciente) obj[1];
            Tblorden o = (Tblorden) obj[2];
            TblexamenOrden eo = (TblexamenOrden) obj[3];
            Tblexamen ex = (Tblexamen) obj[4];
            Tblseccion sec = (Tblseccion) obj[5];
            String nombre = FacesUtil.emptyString(p.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(p.getSegundoApellido());
            this.listaOrdenes.add(new Orden(pa.getNumExpediente(), nombre, sec.getNombreSeccion(), ex.getNombreExamen(), o.getFechaRecepcion(), eo));
        }
        session.close();
    }

    public void onSelectModalidad() {
        TipoServiciosBean ts = (TipoServiciosBean) FacesUtil.getBean("tiposervicioBean");
        this.listaTipoServicio = ts.cargarListaTipoServicio(this.selectedModalidad);
    }

    public void onSelectTipoServicio() {
        ServiciosBean s = (ServiciosBean) FacesUtil.getBean("serviciosBean");
        this.listaServicio = s.cargarListaServicio(this.selectedTipoServicio);
    }

    public void onSelectSeccion() {
        ExamenesBean e = (ExamenesBean) FacesUtil.getBean("examenesBean");
        this.listaExamenes = e.cargarListaExamenes(this.selectedSeccion);
    }

    public void onSelectDepto() {
        DomicilioBean d = (DomicilioBean) FacesUtil.getBean("domicilioBean");
        this.listaMunicipios = d.cargarListaMunicipios(this.selectedDepto);
    }

    public void onSelectMotivo() {
        this.selectedExamenSolicitado.setRenderMotivo(false);
        if (this.selectedExamenSolicitado.getIncidencia().getMotivo() != null) {
            if (this.selectedExamenSolicitado.getIncidencia().getMotivo().equals("Otro")) {
                this.selectedExamenSolicitado.setRenderMotivo(true);
            }
        }
    }

    public void onTabChange(TabChangeEvent event) {
        this.activeTab = "";
    }

    public String onRowSelectNavigate(SelectEvent event) {
        this.selectedOrdenPendiente = (OrdenPendiente) event.getObject();
        //FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCar", event.getObject());  

        return "frmRegResultado?faces-redirect=true";
    }

    public void onSelectPersona(SelectEvent event) {
        try {
            FacesMessage msg = new FacesMessage("Persona seleccionada", ((Object[]) event.getObject())[0].toString());

            String id = ((Object[]) event.getObject())[3].toString().toString();
            Session session = HibernateUtil.getSessionFactory().openSession();
            System.out.println("idPErsona " + id);
            //Carga los datos personales del paciente
            Criteria cPersona = session.createCriteria(Tblpersona.class);
            cPersona.add(Restrictions.eq("idPersona", Long.parseLong(id)));
            this.persona = (Tblpersona) cPersona.uniqueResult();
            if (this.persona.getTipoDocId() != null) {
                this.chkIdentificacion = true;
            } else {
                this.chkIdentificacion = false;
            }
            if (this.persona.getFechaNacimiento() != null) {
                this.cargarFechaNacimiento = true;
            } else {
                this.cargarFechaNacimiento = false;
            }
            if (this.persona.getSexo() != null) {
                this.cargarSexo = true;
            } else {
                this.cargarSexo = false;
            }



            //Carga el numero de expediente del paciente
            Criteria cPaciente = session.createCriteria(Tblpaciente.class);
            cPaciente.add(Restrictions.eq("tblpersona", this.persona));
            if (cPaciente.uniqueResult() != null) {
                this.paciente = (Tblpaciente) cPaciente.uniqueResult();
                this.cargarPaciente = true;
            } else {
                this.paciente = new Tblpaciente();
                this.cargarPaciente = false;
            }

            //Carga la informacion del domicilio mas reciente del paciente
            Criteria cDomicilio = session.createCriteria(Tbldomicilio.class);
            cDomicilio.add(Restrictions.eq("tblpersona", this.persona));
            cDomicilio.addOrder(Order.desc("idDomicilio"));
            if (!cDomicilio.list().isEmpty()) {
                this.domicilio = (Tbldomicilio) cDomicilio.list().get(0);
                this.selectedDepto = this.domicilio.getTblmunicipio().getTbldepartamento().getIdDepartamento();
                this.onSelectDepto();
                this.selectedMunicipio = this.domicilio.getTblmunicipio().getIdMunicipio();
                this.chkDomicilio = true;
            } else {
                this.domicilio = new Tbldomicilio();
                this.selectedMunicipio = null;
                this.selectedDepto = null;
                this.chkDomicilio = false;
            }

            //Carga la informacion laboral mas reciente del paciente
            Criteria cTrabajo = session.createCriteria(Tbllugtrabajo.class);
            cTrabajo.add(Restrictions.eq("tblpersona", this.persona));
            cTrabajo.addOrder(Order.desc("idLugarTrabajo"));
            if (!cTrabajo.list().isEmpty()) {
                this.lugarTrabajo = (Tbllugtrabajo) cTrabajo.list().get(0);
                this.chkTrabajo = true;

            } else {
                this.lugarTrabajo = new Tbllugtrabajo();
                this.chkTrabajo = false;
            }
            session.close();
            FacesContext.getCurrentInstance().addMessage(null, msg);
            this.cargarPersona = true;
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void onSelectNumExpediente(SelectEvent event){
        this.onKeyPress();
    }

    public void onKeyPress() {
        //String id = ((Object[]) event.getObject())[3].toString().toString();
        Session session = HibernateUtil.getSessionFactory().openSession();

        //Carga los datos personales del paciente
        Criteria cPaciente = session.createCriteria(Tblpaciente.class);
        cPaciente.add(Restrictions.like("numExpediente", this.numExpediente));
        cPaciente.setMaxResults(1);
        if (cPaciente.uniqueResult() == null) {
            this.limpiar();
        } else {
            this.paciente = (Tblpaciente) cPaciente.uniqueResult();
            //Criteria cPersona = session.createCriteria(Tblpersona.class);
            //cPersona.add(Restrictions.eq("idPersona", Long.parseLong(id)));
            this.persona = (Tblpersona) paciente.getTblpersona();
            String nombre = FacesUtil.emptyString(this.persona.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(this.persona.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(this.persona.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(this.persona.getSegundoApellido()) + " ";
            FacesMessage msg = new FacesMessage("Paciente seleccionado: ", nombre);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            if (this.persona.getTipoDocId() != null) {
                this.chkIdentificacion = true;
            } else {
                this.chkIdentificacion = false;
            }
            if (this.persona.getFechaNacimiento() != null) {
                this.cargarFechaNacimiento = true;
            } else {
                this.cargarFechaNacimiento = false;
            }
            if (this.persona.getSexo() != null) {
                this.cargarSexo = true;
            } else {
                this.cargarSexo = false;
            }



            //Carga el numero de expediente del paciente
            //Criteria cPaciente = session.createCriteria(Tblpaciente.class);
            cPaciente.add(Restrictions.eq("tblpersona", this.persona));
            if (cPaciente.uniqueResult() != null) {
                this.paciente = (Tblpaciente) cPaciente.uniqueResult();
                this.cargarPaciente = true;
            } else {
                this.paciente = new Tblpaciente();
                this.cargarPaciente = false;
            }

            //Carga la informacion del domicilio mas reciente del paciente
            Criteria cDomicilio = session.createCriteria(Tbldomicilio.class);
            cDomicilio.add(Restrictions.eq("tblpersona", this.persona));
            cDomicilio.addOrder(Order.desc("idDomicilio"));
            if (!cDomicilio.list().isEmpty()) {
                this.domicilio = (Tbldomicilio) cDomicilio.list().get(0);
                this.selectedDepto = this.domicilio.getTblmunicipio().getTbldepartamento().getIdDepartamento();
                this.onSelectDepto();
                this.selectedMunicipio = this.domicilio.getTblmunicipio().getIdMunicipio();
                this.chkDomicilio = true;
            } else {
                this.domicilio = new Tbldomicilio();
                this.selectedMunicipio = null;
                this.selectedDepto = null;
                this.chkDomicilio = false;
            }

            //Carga la informacion laboral mas reciente del paciente
            Criteria cTrabajo = session.createCriteria(Tbllugtrabajo.class);
            cTrabajo.add(Restrictions.eq("tblpersona", this.persona));
            cTrabajo.addOrder(Order.desc("idLugarTrabajo"));
            if (!cTrabajo.list().isEmpty()) {
                this.lugarTrabajo = (Tbllugtrabajo) cTrabajo.list().get(0);
                this.chkTrabajo = true;

            } else {
                this.lugarTrabajo = new Tbllugtrabajo();
                this.chkTrabajo = false;
            }
            session.close();
            this.cargarPersona = true;
        }

    }

    public void onRowSelect(SelectEvent event) {
        try {
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ctx.redirect(ctxPath + "/faces/produccion/frmRegResultadoExpress.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getStringDate(Date d) {
        return FacesUtil.getStringDate(d);
    }

    public void cargarOrdenesPendientes() {
        this.listaOrdenesPendientes = new ArrayList<OrdenPendiente>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT DISTINCT p, pa, o, eo, ex, s, pl, e "
                + "FROM Tblpersona p "
                + "JOIN p.tblpacientes pa "
                + "JOIN pa.tblordens o "
                + "JOIN o.tblexamenOrdens eo "
                + "JOIN eo.tblexamen ex "
                + "JOIN ex.tblseccion s "
                + "JOIN s.tblplanificaciontrabajos pl "
                + "JOIN pl.tblempleado e "
                + "WHERE eo.estado LIKE 'Pendiente' AND "
                + "e.codigoMarcacion = :idEmpleado "
                + "GROUP BY eo.idExamenOrden "
                + "ORDER BY eo.idExamenOrden ASC";
        Query query = session.createQuery(sql);
        UsuarioBean u = (UsuarioBean) FacesUtil.getBean("usuarioBean");
        query.setParameter("idEmpleado", u.getIdEmpleado());
        List queryList = query.list();
        Iterator iterOrdenes = query.list().iterator();
        this.cantidadOrdenesPendientes = queryList.size();
        System.out.println(query.list().size());
        while (iterOrdenes.hasNext()) {
            Object[] obj = (Object[]) iterOrdenes.next();
            Tblpersona p = (Tblpersona) obj[0];
            TblexamenOrden eo = (TblexamenOrden) obj[3];
            Tblexamen ex = (Tblexamen) obj[4];
            String name = FacesUtil.emptyString(p.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(p.getSegundoApellido()) + " ";
            this.listaOrdenesPendientes.add(new OrdenPendiente(name, eo, ex));
        }
        session.close();
    }

    /**
     * Getters & Setters
     */
    public String getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
    }

    public int getCantidadOrdenesPendientes() {
        return cantidadOrdenesPendientes;
    }

    public void setCantidadOrdenesPendientes(int cantidadOrdenesPendientes) {
        this.cantidadOrdenesPendientes = cantidadOrdenesPendientes;
    }

    public boolean isCargarFechaNacimiento() {
        return cargarFechaNacimiento;
    }

    public void setCargarFechaNacimiento(boolean cargarFechaNacimiento) {
        this.cargarFechaNacimiento = cargarFechaNacimiento;
    }

    public boolean isCargarPaciente() {
        return cargarPaciente;
    }

    public void setCargarPaciente(boolean cargarPaciente) {
        this.cargarPaciente = cargarPaciente;
    }

    public boolean isCargarPersona() {
        return cargarPersona;
    }

    public void setCargarPersona(boolean cargarPersona) {
        this.cargarPersona = cargarPersona;
    }

    public boolean isCargarSexo() {
        return cargarSexo;
    }

    public void setCargarSexo(boolean cargarSexo) {
        this.cargarSexo = cargarSexo;
    }

    public boolean isChkDomicilio() {
        return chkDomicilio;
    }

    public void setChkDomicilio(boolean chkDomicilio) {
        this.chkDomicilio = chkDomicilio;
    }

    public boolean isChkIdentificacion() {
        return chkIdentificacion;
    }

    public void setChkIdentificacion(boolean chkIdentificacion) {
        this.chkIdentificacion = chkIdentificacion;
    }

    public boolean isChkMotivo() {
        return chkMotivo;
    }

    public void setChkMotivo(boolean chkMotivo) {
        this.chkMotivo = chkMotivo;
    }

    public boolean isChkTrabajo() {
        return chkTrabajo;
    }

    public void setChkTrabajo(boolean chkTrabajo) {
        this.chkTrabajo = chkTrabajo;
    }

    public Tbldomicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Tbldomicilio domicilio) {
        this.domicilio = domicilio;
    }

    public TblexamenOrden getExamenSolicitado() {
        return examenSolicitado;
    }

    public void setExamenSolicitado(TblexamenOrden examenSolicitado) {
        this.examenSolicitado = examenSolicitado;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public ArrayList<SelectItem> getListaExamenes() {
        return listaExamenes;
    }

    public void setListaExamenes(ArrayList<SelectItem> listaExamenes) {
        this.listaExamenes = listaExamenes;
    }

    public ArrayList<ExamenOrden> getListaExamenesSolicitados() {
        return listaExamenesSolicitados;
    }

    public void setListaExamenesSolicitados(ArrayList<ExamenOrden> listaExamenesSolicitados) {
        this.listaExamenesSolicitados = listaExamenesSolicitados;
    }

    public ArrayList<SelectItem> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(ArrayList<SelectItem> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public ArrayList<Orden> getListaOrdenes() {
        return listaOrdenes;
    }

    public void setListaOrdenes(ArrayList<Orden> listaOrdenes) {
        this.listaOrdenes = listaOrdenes;
    }

    public ArrayList<OrdenPendiente> getListaOrdenesPendientes() {
        return listaOrdenesPendientes;
    }

    public void setListaOrdenesPendientes(ArrayList<OrdenPendiente> listaOrdenesPendientes) {
        this.listaOrdenesPendientes = listaOrdenesPendientes;
    }

    public ArrayList<Object[]> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(ArrayList<Object[]> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    public ArrayList<SelectItem> getListaServicio() {
        return listaServicio;
    }

    public void setListaServicio(ArrayList<SelectItem> listaServicio) {
        this.listaServicio = listaServicio;
    }

    public ArrayList<SelectItem> getListaTipoServicio() {
        return listaTipoServicio;
    }

    public void setListaTipoServicio(ArrayList<SelectItem> listaTipoServicio) {
        this.listaTipoServicio = listaTipoServicio;
    }

    public Tbllugtrabajo getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(Tbllugtrabajo lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
    }

    public Tblorden getOrden() {
        return orden;
    }

    public void setOrden(Tblorden orden) {
        this.orden = orden;
    }

    public Tblpaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Tblpaciente paciente) {
        this.paciente = paciente;
    }

    public Tblpersona getPersona() {
        return persona;
    }

    public void setPersona(Tblpersona persona) {
        this.persona = persona;
    }

    public boolean isRenderMotivo() {
        return renderMotivo;
    }

    public void setRenderMotivo(boolean renderMotivo) {
        this.renderMotivo = renderMotivo;
    }

    public Short getSelectedDepto() {
        return selectedDepto;
    }

    public void setSelectedDepto(Short selectedDepto) {
        this.selectedDepto = selectedDepto;
    }

    public Short getSelectedEspecialidad() {
        return selectedEspecialidad;
    }

    public void setSelectedEspecialidad(Short selectedEspecialidad) {
        this.selectedEspecialidad = selectedEspecialidad;
    }

    public Long getSelectedExamen() {
        return selectedExamen;
    }

    public void setSelectedExamen(Long selectedExamen) {
        this.selectedExamen = selectedExamen;
    }

    public ExamenOrden getSelectedExamenSolicitado() {
        return selectedExamenSolicitado;
    }

    public void setSelectedExamenSolicitado(ExamenOrden selectedExamenSolicitado) {
        this.selectedExamenSolicitado = selectedExamenSolicitado;
    }

    public Integer getSelectedMedico() {
        return selectedMedico;
    }

    public void setSelectedMedico(Integer selectedMedico) {
        this.selectedMedico = selectedMedico;
    }

    public Short getSelectedModalidad() {
        return selectedModalidad;
    }

    public void setSelectedModalidad(Short selectedModalidad) {
        this.selectedModalidad = selectedModalidad;
    }

    public String getSelectedMotivo() {
        return selectedMotivo;
    }

    public void setSelectedMotivo(String selectedMotivo) {
        this.selectedMotivo = selectedMotivo;
    }

    public Short getSelectedMunicipio() {
        return selectedMunicipio;
    }

    public void setSelectedMunicipio(Short selectedMunicipio) {
        this.selectedMunicipio = selectedMunicipio;
    }

    public Orden getSelectedOrden() {
        return selectedOrden;
    }

    public void setSelectedOrden(Orden selectedOrden) {
        this.selectedOrden = selectedOrden;
    }

    public OrdenPendiente getSelectedOrdenPendiente() {
        return selectedOrdenPendiente;
    }

    public void setSelectedOrdenPendiente(OrdenPendiente selectedOrdenPendiente) {
        this.selectedOrdenPendiente = selectedOrdenPendiente;
    }

    public Object[] getSelectedPersona() {
        return selectedPersona;
    }

    public void setSelectedPersona(Object[] selectedPersona) {
        this.selectedPersona = selectedPersona;
    }

    public Object[] getSelectedResultadoBusqueda() {
        return selectedResultadoBusqueda;
    }

    public void setSelectedResultadoBusqueda(Object[] selectedResultadoBusqueda) {
        this.selectedResultadoBusqueda = selectedResultadoBusqueda;
    }

    public Short getSelectedSeccion() {
        return selectedSeccion;
    }

    public void setSelectedSeccion(Short selectedSeccion) {
        this.selectedSeccion = selectedSeccion;
    }

    public Short getSelectedServicio() {
        return selectedServicio;
    }

    public void setSelectedServicio(Short selectedServicio) {
        this.selectedServicio = selectedServicio;
    }

    public String getSelectedTipoDocId() {
        return selectedTipoDocId;
    }

    public void setSelectedTipoDocId(String selectedTipoDocId) {
        this.selectedTipoDocId = selectedTipoDocId;
    }

    public Short getSelectedTipoServicio() {
        return selectedTipoServicio;
    }

    public void setSelectedTipoServicio(Short selectedTipoServicio) {
        this.selectedTipoServicio = selectedTipoServicio;
    }

    public Tblsolicitudvih getSolicitudvih() {
        return solicitudvih;
    }

    public void setSolicitudvih(Tblsolicitudvih solicitudvih) {
        this.solicitudvih = solicitudvih;
    }

    public OrdenPendiente getTempSelection() {
        return tempSelection;
    }

    public void setTempSelection(OrdenPendiente tempSelection) {
        this.tempSelection = tempSelection;
    }

    public class OrdenPendiente implements Serializable {

        private String paciente;
        private TblexamenOrden examenOrden;
        private Tblexamen examen;

        public OrdenPendiente(String paciente, TblexamenOrden examenOrden, Tblexamen examen) {
            this.paciente = paciente;
            this.examenOrden = examenOrden;
            this.examen = examen;
        }

        private OrdenPendiente() {
        }

        public Tblexamen getExamen() {
            return examen;
        }

        public void setExamen(Tblexamen examen) {
            this.examen = examen;
        }

        public TblexamenOrden getExamenOrden() {
            return examenOrden;
        }

        public void setExamenOrden(TblexamenOrden examenOrden) {
            this.examenOrden = examenOrden;
        }

        public String getPaciente() {
            return paciente;
        }

        public void setPaciente(String paciente) {
            this.paciente = paciente;
        }
    }

    public class Orden implements Serializable {

        private String expediente;
        private String paciente;
        private String seccion;
        private String examen;
        private Date fecha;
        private TblexamenOrden examenOrden;

        public Orden(String expediente, String paciente, String seccion, String examen, Date fecha, TblexamenOrden examenOrden) {
            this.expediente = expediente;
            this.paciente = paciente;
            this.seccion = seccion;
            this.examen = examen;
            this.fecha = fecha;
            this.examenOrden = examenOrden;
        }

        public String getExamen() {
            return examen;
        }

        public void setExamen(String examen) {
            this.examen = examen;
        }

        public TblexamenOrden getExamenOrden() {
            return examenOrden;
        }

        public void setExamenOrden(TblexamenOrden examenOrden) {
            this.examenOrden = examenOrden;
        }

        public String getExpediente() {
            return expediente;
        }

        public void setExpediente(String expediente) {
            this.expediente = expediente;
        }

        public String getPaciente() {
            return paciente;
        }

        public void setPaciente(String paciente) {
            this.paciente = paciente;
        }

        public String getSeccion() {
            return seccion;
        }

        public void setSeccion(String seccion) {
            this.seccion = seccion;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }
    }

    public String getActiveTab() {
        return activeTab;
    }

    public void setActiveTab(String activeTab) {
        this.activeTab = activeTab;
    }

    public class ExamenOrden implements Serializable {

        private Long idExamen;
        private String codigoExamen;
        private String nombreExamen;
        private String accion;
        private boolean renderCmdRechazar;
        private boolean renderCmdAceptar;
        private boolean renderMotivo;
        private TblexamenOrden examenOrden;
        private Tblincidencia incidencia;

        public ExamenOrden(Long idExamen, String codigoExamen, String nombreExamen, String accion, boolean renderCmdRechazar, boolean renderCmdAceptar, boolean renderMotivo, TblexamenOrden examenOrden, Tblincidencia incidencia) {
            this.idExamen = idExamen;
            this.codigoExamen = codigoExamen;
            this.nombreExamen = nombreExamen;
            this.accion = accion;
            this.renderCmdRechazar = renderCmdRechazar;
            this.renderCmdAceptar = renderCmdAceptar;
            this.renderMotivo = renderMotivo;
            this.examenOrden = examenOrden;
            this.incidencia = incidencia;
        }

        private ExamenOrden() {
        }

        public boolean isRenderCmdAceptar() {
            return renderCmdAceptar;
        }

        public void setRenderCmdAceptar(boolean renderCmdAceptar) {
            this.renderCmdAceptar = renderCmdAceptar;
        }

        public boolean isRenderCmdRechazar() {
            return renderCmdRechazar;
        }

        public void setRenderCmdRechazar(boolean renderCmdRechazar) {
            this.renderCmdRechazar = renderCmdRechazar;
        }

        public String getCodigoExamen() {
            return codigoExamen;
        }

        public void setCodigoExamen(String codigoExamen) {
            this.codigoExamen = codigoExamen;
        }

        public TblexamenOrden getExamenOrden() {
            return examenOrden;
        }

        public void setExamenOrden(TblexamenOrden examenOrden) {
            this.examenOrden = examenOrden;
        }

        public Long getIdExamen() {
            return idExamen;
        }

        public void setIdExamen(Long idExamen) {
            this.idExamen = idExamen;
        }

        public String getNombreExamen() {
            return nombreExamen;
        }

        public void setNombreExamen(String nombreExamen) {
            this.nombreExamen = nombreExamen;
        }

        public String getAccion() {
            return accion;
        }

        public void setAccion(String accion) {
            this.accion = accion;
        }

        public Tblincidencia getIncidencia() {
            return incidencia;
        }

        public void setIncidencia(Tblincidencia incidencia) {
            this.incidencia = incidencia;
        }

        public boolean isRenderMotivo() {
            return renderMotivo;
        }

        public void setRenderMotivo(boolean renderMotivo) {
            this.renderMotivo = renderMotivo;
        }
    }
}
