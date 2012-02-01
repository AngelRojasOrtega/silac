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
import com.silac.model.Tblparametro;
import com.silac.model.Tblpersona;
import com.silac.model.Tblproduccion;
import com.silac.model.Tblresultado;
import com.silac.model.Tblservicio;
import com.silac.model.Tblsolicitudvih;
import com.silac.model.Tbltiporesultado;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;
import com.silac.controller.ResultadoBean.Resultado;

/**
 *
 * @author kerberoz
 */
@ManagedBean(name = "resultadoExtendidoBean")
@ViewScoped
public class ResultadoExtendidoBean implements Serializable {

    private String numExpediente;
    private Tblpersona persona;
    private Tblpaciente paciente;
    private Tbldomicilio domicilio;
    private Tblorden orden;
    private Tbllugtrabajo lugarTrabajo;
    private Tblsolicitudvih solicitudvih;
    private Tblincidencia incidencia;
    private TblexamenOrden examenOrden;
    private ArrayList<SelectItem> listaMunicipios;
    private ArrayList<SelectItem> listaServicio;
    private ArrayList<SelectItem> listaTipoServicio;
    private ArrayList<SelectItem> listaExamenes;
    private ArrayList<Tblparametro> listaParametro;
    private ArrayList<Resultado> listaResultado;
    private ArrayList<Object[]> listaPersonas;
    private Tblparametro selectedParametro;
    private Integer selectedMedico;
    private Long selectedExamen;
    private Short selectedTipoResultado;
    private Short selectedTecnico;
    private Short selectedModalidad;
    private Short selectedServicio;
    private Short selectedTipoServicio;
    private Short selectedEspecialidad;
    private Short selectedSeccion;
    private Short selectedDepto;
    private Short selectedMunicipio;
    private String selectedMotivo;
    private String selectedTipoDocId;
    private Resultado selectedResultado;
    private Object[] selectedPersona;
    private boolean chkDomicilio;
    private boolean chkTrabajo;
    private boolean chkIdentificacion;
    private boolean chkMotivo;
    private boolean renderMotivo;
    private boolean cargarPaciente;
    private boolean cargarFechaNacimiento;
    private boolean cargarSexo;
    private boolean cargarPersona;
    private Date hoy;

    /** Creates a new instance of ResultadoExtendidoBean */
    public ResultadoExtendidoBean() {
        this.init();
    }

    private void init() {
        this.numExpediente = "";
        this.persona = new Tblpersona();
        this.paciente = new Tblpaciente();
        this.domicilio = new Tbldomicilio();
        this.orden = new Tblorden();
        this.lugarTrabajo = new Tbllugtrabajo();
        this.solicitudvih = new Tblsolicitudvih();
        this.incidencia = new Tblincidencia();
        this.examenOrden = new TblexamenOrden();
        this.listaResultado = new ArrayList<Resultado>();
        this.listaExamenes = new ArrayList<SelectItem>();
        this.listaMunicipios = new ArrayList<SelectItem>();
        this.listaParametro = new ArrayList<Tblparametro>();
        this.listaPersonas = new ArrayList<Object[]>();
        this.listaServicio = new ArrayList<SelectItem>();
        this.listaTipoServicio = new ArrayList<SelectItem>();
        this.cargarPaciente = false;
        this.cargarSexo = false;
        this.cargarFechaNacimiento = false;
        this.cargarPersona = false;
        this.selectedResultado = new Resultado();
        this.selectedParametro = new Tblparametro();
        this.selectedTipoResultado = null;
        this.selectedTecnico = null;
        this.selectedExamen = null;
        this.selectedSeccion = null;
        this.selectedDepto = null;
        this.selectedMunicipio = null;
        this.selectedModalidad = null;
        this.selectedTipoServicio = null;
        this.selectedServicio = null;
        this.selectedMedico = null;
        this.selectedEspecialidad = null;
        this.selectedMotivo = null;
        this.selectedTipoDocId = null;
        this.selectedPersona = null;
        this.renderMotivo = false;
        this.chkDomicilio = false;
        this.chkMotivo = false;
        this.chkTrabajo = false;
    }

    public void crear() {
        UsuarioBean usuario = (UsuarioBean) FacesUtil.getBean("usuarioBean");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.saveOrUpdate(this.persona);
            if (!this.cargarPaciente) {
                this.paciente.setTblpersona(this.persona);
                session.saveOrUpdate(this.paciente);
            }

            //TblexamenOrden examenOrden = (TblexamenOrden) session.load(TblexamenOrden.class, usuario.getSelectedOrdenPendiente().getExamenOrden().getIdExamenOrden());
            Tblempleado empleado = (Tblempleado) session.load(Tblempleado.class, usuario.getIdEmpleado());//Carga el registro del empleado que esta digitando el resultado
            Tblempleado tecnico = (Tblempleado) session.load(Tblempleado.class, this.selectedTecnico);//Carga el registro del tecnico que realizo el examen           
            Tblexamen examen = (Tblexamen) session.load(Tblexamen.class, this.selectedExamen);
            Tblespecialidad especialidad = (Tblespecialidad) session.load(Tblespecialidad.class, this.selectedEspecialidad);
            Tblmedico medico = (Tblmedico) session.load(Tblmedico.class, this.selectedMedico);
            Tblservicio servicio = (Tblservicio) session.load(Tblservicio.class, this.selectedServicio);

            //Alamacena la informacion sobre la orden
            orden.setFechaRecepcion(new Date());
            orden.setTblempleado(empleado);
            orden.setTblespecialidad(especialidad);
            orden.setTblmedico(medico);
            orden.setTblpaciente(this.paciente);
            orden.setTblservicio(servicio);
            session.saveOrUpdate(this.orden);

            examenOrden.setFechaRegistro(new Date());
            examenOrden.setTblexamen(examen);
            examenOrden.setTblorden(orden);
            examenOrden.setFechaRegistro(new Date());
            examenOrden.setTblempleado(tecnico);
            session.saveOrUpdate(examenOrden);

            //Almacena la informacion sobre el resultado del analisis en la tabla resultado.
            Tblresultado resultadoGeneral = new Tblresultado();
            Tbltiporesultado tr = (Tbltiporesultado) session.load(Tbltiporesultado.class, this.selectedTipoResultado);
            resultadoGeneral.setTbltiporesultado(tr);
            resultadoGeneral.setTblexamenOrden(examenOrden);
            resultadoGeneral.setTblempleado(empleado);
            resultadoGeneral.setFechaResultado(new Date());
            if (this.selectedTipoResultado.intValue() != 7) {//El valor 7 corresponde al resultado "No se realizo"
                //Almacena los resultados del examen en la tabla de produccion.        
                Iterator iterResultado = this.listaResultado.iterator();
                String valor = "";
                while (iterResultado.hasNext()) {
                    Resultado r = (Resultado) iterResultado.next();
                    Tblparametro p = (Tblparametro) session.load(Tblparametro.class, r.getIdParametro());
                    Tblproduccion pro = new Tblproduccion();
                    pro.setTblexamenOrden(examenOrden);
                    pro.setTblparametro(p);
                    //pro.setTblempleado(tecnico);
                    if (r.getTipo().equals("Valor")) {
                        valor = r.getValor().toString();
                    } else if (r.getTipo().equals("Texto")) {
                        valor = r.getDescripcion();
                    } else if (r.getTipo().equals("Logico")) {
                        valor = r.getLogico();
                    }
                    pro.setValor(valor);
                    session.save(pro);
                }
                //Acutaliza el estado de la orden.
                examenOrden.setEstado("Finalizado");
            } else {
                //Acutaliza el estado de la orden.
                examenOrden.setEstado("No se realizo");

                //Almacena la incidencia
                this.incidencia.setFecha(new Date());
                session.save(this.incidencia);

                //Vincula la incidencia con el resultado registrado.
                Set<Tblincidencia> incidencias = new HashSet<Tblincidencia>();
                incidencias.add(this.incidencia);
                resultadoGeneral.setTblincidencias(incidencias);
            }
            session.save(resultadoGeneral);
            session.saveOrUpdate(examenOrden);
            session.flush();
            session.clear();
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "Los resultados se registraron satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }

    }

    public void nuevo() {
        this.init();
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

    public void buscar() {
        PersonaBean p = (PersonaBean) FacesUtil.getBean("personasBean");
        this.listaPersonas = p.buscar();
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

    public void onRowSelectParametro(SelectEvent event) {
        Tblparametro param = (Tblparametro) event.getObject();
        System.out.println(param.getIdParametro());
        boolean exist = false;
        Iterator iterParam = this.listaResultado.iterator();
        while (iterParam.hasNext()) {
            Resultado r = (Resultado) iterParam.next();
            if (param.getIdParametro().intValue() == r.getIdParametro().intValue()) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            this.listaResultado.add(new Resultado(param.getNombreParametro(),
                    param.getTipoParametro(),
                    param.getIdParametro(),
                    new Tblproduccion(), 0.0, "", "", param.isFijo()));
        }
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Persona seleccionada", ((Object[]) event.getObject())[0].toString());
        String id = ((Object[]) event.getObject())[3].toString().toString();
        Session session = HibernateUtil.getSessionFactory().openSession();

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
        this.listaResultado = new ArrayList<Resultado>();
        this.selectedExamen = null;
    }

    public void onSelectDepto() {
        DomicilioBean d = (DomicilioBean) FacesUtil.getBean("domicilioBean");
        this.listaMunicipios = d.cargarListaMunicipios(this.selectedDepto);
    }

    public void onSelectMotivo() {
        this.renderMotivo = false;
        if (this.incidencia.getMotivo() != null) {
            if (this.incidencia.getMotivo().equals("Otro")) {
                this.renderMotivo = true;
            }
        }
    }

    public void onSelectExamen() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT p FROM Tblexamen ex "
                + "JOIN ex.tblparametros p "
                + "WHERE ex.idExamen = :idExamen AND p.fijo = true";
        Query queryParametro = session.createQuery(sql);
        queryParametro.setParameter("idExamen", this.selectedExamen);
        Iterator iterParametro = queryParametro.list().iterator();
        this.listaResultado = new ArrayList<Resultado>();
        Resultado r = new Resultado();
        while (iterParametro.hasNext()) {
            Tblparametro param = (Tblparametro) iterParametro.next();
            System.out.println(param.getNombreParametro());
            this.listaResultado.add(new Resultado(param.getNombreParametro(),
                    param.getTipoParametro(),
                    param.getIdParametro(),
                    new Tblproduccion(), 0.0, "", "", param.isFijo()));
        }
        session.close();
    }

    public void agregarParametro() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT p FROM Tblexamen ex "
                + "JOIN ex.tblparametros p "
                + "WHERE ex.idExamen = :idExamen AND p.fijo = false";
        Query queryParametro = session.createQuery(sql);
        queryParametro.setParameter("idExamen", this.selectedExamen);
        Iterator iterParametro = queryParametro.list().iterator();
        this.listaParametro = new ArrayList<Tblparametro>();
        Resultado r = new Resultado();
        while (iterParametro.hasNext()) {
            Tblparametro param = (Tblparametro) iterParametro.next();
            this.listaParametro.add(param);
        }
        session.close();
    }

    public void removerParametro() {
        System.out.println("Parametro: " + this.selectedResultado.getTipo());
        this.listaResultado.remove(this.selectedResultado);
    }

    public void onSelectResultado() {
        this.incidencia = new Tblincidencia();
        this.renderMotivo = false;
    }

    /*
     * Getters & Setters
     */
    public String getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
    }

    public boolean isCargarPersona() {
        return cargarPersona;
    }

    public void setCargarPersona(boolean cargarPersona) {
        this.cargarPersona = cargarPersona;
    }

    public ArrayList<Object[]> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(ArrayList<Object[]> listaPersonas) {
        this.listaPersonas = listaPersonas;
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

    public Tblparametro getSelectedParametro() {
        return selectedParametro;
    }

    public void setSelectedParametro(Tblparametro selectedParametro) {
        this.selectedParametro = selectedParametro;
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

    public ArrayList<SelectItem> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(ArrayList<SelectItem> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
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

    public ArrayList<Resultado> getListaResultado() {
        return listaResultado;
    }

    public void setListaResultado(ArrayList<Resultado> listaResultado) {
        this.listaResultado = listaResultado;
    }

    public boolean isCargarPaciente() {
        return cargarPaciente;
    }

    public void setCargarPaciente(boolean cargarPaciente) {
        this.cargarPaciente = cargarPaciente;
    }

    public boolean isCargarFechaNacimiento() {
        return cargarFechaNacimiento;
    }

    public void setCargarFechaNacimiento(boolean cargarFechaNacimiento) {
        this.cargarFechaNacimiento = cargarFechaNacimiento;
    }

    public boolean isCargarSexo() {
        return cargarSexo;
    }

    public void setCargarSexo(boolean cargarSexo) {
        this.cargarSexo = cargarSexo;
    }

    public ArrayList<Tblparametro> getListaParametro() {
        return listaParametro;
    }

    public void setListaParametro(ArrayList<Tblparametro> listaParametro) {
        this.listaParametro = listaParametro;
    }

    public Resultado getSelectedResultado() {
        return selectedResultado;
    }

    public void setSelectedResultado(Resultado selectedResultado) {
        this.selectedResultado = selectedResultado;
    }

    public Object[] getSelectedPersona() {
        return selectedPersona;
    }

    public void setSelectedPersona(Object[] selectedPersona) {
        this.selectedPersona = selectedPersona;
    }

    public Short getSelectedTecnico() {
        return selectedTecnico;
    }

    public void setSelectedTecnico(Short selectedTecnico) {
        this.selectedTecnico = selectedTecnico;
    }

    public Short getSelectedTipoResultado() {
        return selectedTipoResultado;
    }

    public void setSelectedTipoResultado(Short selectedTipoResultado) {
        this.selectedTipoResultado = selectedTipoResultado;
    }

    public TblexamenOrden getExamenOrden() {
        return examenOrden;
    }

    public void setExamenOrden(TblexamenOrden examenOrden) {
        this.examenOrden = examenOrden;
    }
}
