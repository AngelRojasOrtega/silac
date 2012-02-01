/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tbldepartamento;
import com.silac.model.Tbldomicilio;
import com.silac.model.Tblempleado;
import com.silac.model.Tblespecialidad;
import com.silac.model.Tblexamen;
import com.silac.model.TblexamenOrden;
import com.silac.model.Tblincidencia;
import com.silac.model.Tblmedico;
import com.silac.model.Tblmodalidad;
import com.silac.model.Tblmunicipio;
import com.silac.model.Tblorden;
import com.silac.model.Tblpaciente;
import com.silac.model.Tblparametro;
import com.silac.model.Tblpersona;
import com.silac.model.Tblproduccion;
import com.silac.model.Tblresultado;
import com.silac.model.Tblseccion;
import com.silac.model.Tblservicio;
import com.silac.model.Tbltiporesultado;
import com.silac.model.Tbltiposervicio;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.event.DateSelectEvent;

/**
 *
 * @author kerberoz
 */
@ManagedBean(name = "resultadoBean")
@ViewScoped
public class ResultadoBean implements Serializable {

    private String medico;
    private Tblpersona persona;
    private Tblpaciente paciente;
    private Tbldomicilio domicilio;
    private Tblorden orden;
    private UsuarioBean usuario;
    private Tblmunicipio municipio;
    private Tbldepartamento departamento;
    private Tblservicio servicio;
    private Tbltiposervicio tiposervicio;
    private Tblmodalidad modalidad;
    private Tblseccion seccion;
    private Tblexamen examen;
    private Tblespecialidad especialidad;
    private ArrayList<Resultado> listaResultado;
    private Short selectedTipoResultado;
    private Short selectedTecnico;
    private Tblincidencia incidencia;
    private boolean renderMotivo;
    private String buscarNombre;
    private String buscarDocId;
    private String buscarExpediente;
    private Date fechaInicio;
    private Date fechaFinal;
    private ArrayList<Object[]> listaResultadosProduccion;
    private Object[] selectedResultadoProduccion;
    private String url;
    private Date minDate;
    private Date hoy = new Date();
    private String selectedTipo;

    /** Creates a new instance of ResultadoBean */
    public ResultadoBean() {
        this.init();
        if (this.usuario.getSelectedOrdenPendiente() != null) {
            this.cargarOrden();
        }
    }

    private void init() {
        this.selectedTipoResultado = null;
        this.selectedTecnico = null;
        this.renderMotivo = false;
        this.incidencia = new Tblincidencia();
        this.usuario = (UsuarioBean) FacesUtil.getBean("usuarioBean");
        this.listaResultadosProduccion = new ArrayList<Object[]>();
        this.buscarNombre = null;
        this.buscarDocId = null;
        this.buscarExpediente = null;
        this.fechaInicio = null;
        this.fechaFinal = null;
        this.selectedResultadoProduccion = null;
        this.selectedTipo = null;
    }

    public void crear() throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            TblexamenOrden examenOrden = (TblexamenOrden) session.load(TblexamenOrden.class, usuario.getSelectedOrdenPendiente().getExamenOrden().getIdExamenOrden());
            Tblempleado empleado = (Tblempleado) session.load(Tblempleado.class, usuario.getIdEmpleado());//Carga el registro del empleado que esta digitando el resultado
            Tblempleado tecnico = (Tblempleado) session.load(Tblempleado.class, this.selectedTecnico);//Carga el registro del tecnico que realizo el examen           
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
                    pro.setTblexamenOrden(this.usuario.getSelectedOrdenPendiente().getExamenOrden());
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
            examenOrden.setFechaRegistro(new Date());
            examenOrden.setTblempleado(tecnico);
            session.saveOrUpdate(examenOrden);
            session.flush();
            session.clear();
            tx.commit();
            this.init();
            this.usuario.setSelectedOrdenPendiente(null);
            try {
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
                ctx.redirect(ctxPath + "/faces/produccion/frmRegResultado.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Los resutlados se registraron satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void cargarOrden() {
        String sql;
        if (this.usuario != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            this.persona = this.usuario.getSelectedOrdenPendiente().getExamenOrden().getTblorden().getTblpaciente().getTblpersona();
            this.paciente = this.usuario.getSelectedOrdenPendiente().getExamenOrden().getTblorden().getTblpaciente();
            this.orden = this.usuario.getSelectedOrdenPendiente().getExamenOrden().getTblorden();
            sql = "SELECT do, m, d FROM Tblorden o "
                    + "JOIN o.tbldomicilios do "
                    + "JOIN do.tblmunicipio m "
                    + "JOIN m.tbldepartamento d "
                    + "WHERE o.idOrden = :idOrden";
            Query query = session.createQuery(sql);
            query.setParameter("idOrden", orden.getIdOrden());
            Iterator iterDomicilio = query.list().iterator();
            while (iterDomicilio.hasNext()) {
                Object[] obj = (Object[]) iterDomicilio.next();
                this.domicilio = (Tbldomicilio) obj[0];
                this.municipio = (Tblmunicipio) obj[1];
                this.departamento = (Tbldepartamento) obj[2];
            }
            sql = "SELECT s, ts, m FROM Tblorden o "
                    + "JOIN o.tblservicio s "
                    + "JOIN s.tbltiposervicio ts "
                    + "JOIN ts.tblmodalidad m "
                    + "WHERE o.idOrden = :idOrden";
            Query queryServicio = session.createQuery(sql);
            queryServicio.setParameter("idOrden", orden.getIdOrden());
            Iterator iterServicio = queryServicio.list().iterator();
            while (iterServicio.hasNext()) {
                Object[] obj = (Object[]) iterServicio.next();
                this.servicio = (Tblservicio) obj[0];
                this.tiposervicio = (Tbltiposervicio) obj[1];
                this.modalidad = (Tblmodalidad) obj[2];
            }
            sql = "SELECT e FROM Tblorden o "
                    + "JOIN o.tblespecialidad e "
                    + "WHERE o.idOrden = :idOrden";
            Query queryEspecialidad = session.createQuery(sql);
            queryEspecialidad.setParameter("idOrden", orden.getIdOrden());
            this.especialidad = (Tblespecialidad) queryEspecialidad.uniqueResult();
            sql = "SELECT p FROM Tblorden o "
                    + "JOIN o.tblmedico m "
                    + "JOIN m.tblpersona p "
                    + "WHERE o.idOrden = :idOrden";
            Query queryMedico = session.createQuery(sql);
            queryMedico.setParameter("idOrden", orden.getIdOrden());
            Tblpersona p = (Tblpersona) queryMedico.uniqueResult();
            this.medico = FacesUtil.emptyString(p.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(p.getSegundoApellido());
            this.examen = this.usuario.getSelectedOrdenPendiente().getExamenOrden().getTblexamen();
            this.seccion = this.usuario.getSelectedOrdenPendiente().getExamenOrden().getTblexamen().getTblseccion();
            sql = "SELECT p FROM Tblexamen ex "
                    + "JOIN ex.tblparametros p "
                    + "WHERE ex.idExamen = :idExamen";
            Query queryParametro = session.createQuery(sql);
            queryParametro.setParameter("idExamen", this.examen.getIdExamen());
            Iterator iterParametro = queryParametro.list().iterator();
            this.listaResultado = new ArrayList<Resultado>();
            while (iterParametro.hasNext()) {
                Tblparametro param = (Tblparametro) iterParametro.next();
                System.out.println(param.getNombreParametro());
                this.listaResultado.add(new Resultado(param.getNombreParametro(),
                        param.getTipoParametro(),
                        param.getIdParametro(),
                        new Tblproduccion(), 0.0, "", "", param.isFijo()));
            }
            session.close();
        } else {
            try {
                ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
                ctx.redirect(ctxPath + "/faces/produccion/frmRegResultado.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ArrayList<SelectItem> cargarListaTipoResultado() {
        ArrayList<SelectItem> listaTipoResultado = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cTipo = session.createCriteria(Tbltiporesultado.class);
        Iterator iterTipo = cTipo.list().iterator();
        while (iterTipo.hasNext()) {
            Tbltiporesultado t = (Tbltiporesultado) iterTipo.next();
            listaTipoResultado.add(new SelectItem(t.getTipoResultado(), t.getNombreResultado(), ""));
        }
        session.close();
        return listaTipoResultado;
    }

    public void onSelectMotivo() {
        this.renderMotivo = false;
        if (this.incidencia.getMotivo() != null) {
            if (this.incidencia.getMotivo().equals("Otro")) {
                this.renderMotivo = true;
            }
        }
    }

    public void onSelectRow() {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.url = contextPath + "/faces/createReportServlet?fileName=";
        String fileName = "resultadoAnalisis";
        String tipo = this.selectedTipo;
        String lista = "";
        for (int i = 0; i < this.selectedResultadoProduccion.length; i++) {
            Object[] obj = (Object[]) this.selectedResultadoProduccion[i];
            if (i != this.selectedResultadoProduccion.length - 1) {
                lista = lista + obj[7] + ",";
            } else {
                lista = lista + obj[7];
            }
        }
        String params = "&tipo=" + tipo + "&lista=" + lista;
        this.url = this.url + fileName + params;
    }

    public void cargarListaResultadosProduccion() {
        this.listaResultadosProduccion = new ArrayList<Object[]>();
        Query query = null;
        String sql = "SELECT CONCAT(IFNULL(p.primerNombre,''),' ',IFNULL(p.segundoNombre,''),' ',IFNULL(p.primerApellido,''),' ',IFNULL(p.segundoApellido,'')) "
                + "AS nombre,  CONCAT(IFNULL(pe.primerNombre,''),' ',IFNULL(pe.segundoNombre,''),' ',IFNULL(pe.primerApellido,''),' ',IFNULL(pe.segundoApellido,'')) "
                + "AS empleado, eo.numeroControl, ex.codigoExamen, ex.nombreExamen, DATE_FORMAT(o.fechaRecepcion, '%d/%m/%Y'), pa.numExpediente, eo.idExamenOrden "
                + "FROM  tblpersona p INNER JOIN tblpaciente pa ON p.idPersona = pa.idPersona "
                + "INNER JOIN tblorden o ON pa.idPaciente = o.idPaciente "
                + "INNER JOIN tblexamen_orden eo ON o.idOrden = eo.idOrden "
                + "INNER JOIN tblempleado e ON eo.codigoMarcacion = e.codigoMarcacion "
                + "INNER JOIN tblexamen ex ON eo.idExamen = ex.idExamen "
                + "INNER JOIN tblpersona pe ON e.idPersona = pe.idPersona "
                + "WHERE (eo.estado LIKE 'Finalizado' OR eo.estado LIKE 'Entregado') ";
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (this.buscarNombre != null || this.buscarDocId != null || this.buscarExpediente != null || this.fechaInicio != null || this.fechaFinal != null) {
            String param1 = "AND CONCAT(IFNULL(p.primerNombre,''),' ',IFNULL(p.segundoNombre,''),' ',IFNULL(p.primerApellido,''),' ',IFNULL(p.segundoApellido,'')) LIKE :nombre "
                    + "AND IFNULL(p.docId,'') LIKE :docId "
                    + "AND IFNULL(pa.numExpediente,'') LIKE :numExpediente ";
            query = session.createSQLQuery(sql + param1 + " LIMIT 200");
            if (this.fechaInicio != null && this.fechaFinal != null) {
                String param2 = "AND (DATE(o.fechaRecepcion) BETWEEN :inicio AND :final) ";
                query = session.createSQLQuery(sql + param1 + param2 + " LIMIT 200");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
                String sInicio = sdf.format(this.fechaInicio);
                String sFinal = sdf.format(this.fechaFinal);
                query.setParameter("inicio", sInicio);
                query.setParameter("final", sFinal);
                System.out.println(this.fechaInicio + "'" + sInicio + "'");
                System.out.println(this.fechaFinal + "'" + sFinal + "'");
            }
            query.setParameter("nombre", '%' + this.buscarNombre + '%');
            query.setParameter("docId", '%' + this.buscarDocId + '%');
            query.setParameter("numExpediente", '%' + this.buscarExpediente + '%');
        } else {
            query = session.createSQLQuery(sql + " LIMIT 200");
        }
        this.listaResultadosProduccion = (ArrayList<Object[]>) query.list();
        session.close();
    }

    public void onSelectResultado() {
        this.incidencia = new Tblincidencia();
        this.renderMotivo = false;
    }

    public String getStringDate(Date d) {
        return FacesUtil.getStringDate(d);
    }

    public void handleDateSelect(DateSelectEvent event) {
        this.minDate = event.getDate();
    }

    public void limpiar() {
        this.buscarNombre = null;
        this.buscarDocId = null;
        this.buscarExpediente = null;
        this.fechaInicio = null;
        this.fechaFinal = null;
    }

    /*
     * Getters & Setters
     */
    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public Tbldomicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Tbldomicilio domicilio) {
        this.domicilio = domicilio;
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

    public UsuarioBean getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioBean usuario) {
        this.usuario = usuario;
    }

    public Tbldepartamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Tbldepartamento departamento) {
        this.departamento = departamento;
    }

    public Tblmunicipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Tblmunicipio municipio) {
        this.municipio = municipio;
    }

    public Tblespecialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Tblespecialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Tblexamen getExamen() {
        return examen;
    }

    public void setExamen(Tblexamen examen) {
        this.examen = examen;
    }

    public Tblseccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Tblseccion seccion) {
        this.seccion = seccion;
    }

    public Tblservicio getServicio() {
        return servicio;
    }

    public void setServicio(Tblservicio servicio) {
        this.servicio = servicio;
    }

    public Tblmodalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Tblmodalidad modalidad) {
        this.modalidad = modalidad;
    }

    public Tbltiposervicio getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(Tbltiposervicio tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public Tblpersona getPersona() {
        return persona;
    }

    public void setPersona(Tblpersona persona) {
        this.persona = persona;
    }

    public ArrayList<Resultado> getListaResultado() {
        return listaResultado;
    }

    public void setListaResultado(ArrayList<Resultado> listaResultado) {
        this.listaResultado = listaResultado;
    }

    public Short getSelectedTipoResultado() {
        return selectedTipoResultado;
    }

    public void setSelectedTipoResultado(Short selectedTipoResultado) {
        this.selectedTipoResultado = selectedTipoResultado;
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

    public Short getSelectedTecnico() {
        return selectedTecnico;
    }

    public void setSelectedTecnico(Short selectedTecnico) {
        this.selectedTecnico = selectedTecnico;
    }

    public String getBuscarDocId() {
        return buscarDocId;
    }

    public void setBuscarDocId(String buscarDocId) {
        this.buscarDocId = buscarDocId;
    }

    public String getBuscarExpediente() {
        return buscarExpediente;
    }

    public void setBuscarExpediente(String buscarExpediente) {
        this.buscarExpediente = buscarExpediente;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Object[] getSelectedResultadoProduccion() {
        return selectedResultadoProduccion;
    }

    public void setSelectedResultadoProduccion(Object[] selectedResultadoProduccion) {
        this.selectedResultadoProduccion = selectedResultadoProduccion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public ArrayList<Object[]> getListaResultadosProduccion() {
        return listaResultadosProduccion;
    }

    public void setListaResultadosProduccion(ArrayList<Object[]> listaResultadosProduccion) {
        this.listaResultadosProduccion = listaResultadosProduccion;
    }

    public String getSelectedTipo() {
        return selectedTipo;
    }

    public void setSelectedTipo(String selectedTipo) {
        this.selectedTipo = selectedTipo;
    }

    public static class Resultado implements Serializable { 

        String parametro;
        String tipo;
        Integer idParametro;
        Tblproduccion produccion;
        Double valor;
        String descripcion;
        String logico;
        boolean fijo;

        public Resultado() {
        }

        public Resultado(String parametro, String tipo, Integer idParametro, Tblproduccion produccion, Double valor, String descripcion, String logico, boolean fijo) {
            this.parametro = parametro;
            this.tipo = tipo;
            this.idParametro = idParametro;
            this.produccion = produccion;
            this.valor = valor;
            this.descripcion = descripcion;
            this.logico = logico;
            this.fijo = fijo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getLogico() {
            return logico;
        }

        public void setLogico(String logico) {
            this.logico = logico;
        }

        public Double getValor() {
            return valor;
        }

        public void setValor(Double valor) {
            this.valor = valor;
        }

        public Integer getIdParametro() {
            return idParametro;
        }

        public void setIdParametro(Integer idParametro) {
            this.idParametro = idParametro;
        }

        public String getParametro() {
            return parametro;
        }

        public void setParametro(String parametro) {
            this.parametro = parametro;
        }

        public Tblproduccion getProduccion() {
            return produccion;
        }

        public void setProduccion(Tblproduccion produccion) {
            this.produccion = produccion;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public boolean isFijo() {
            return fijo;
        }

        public void setFijo(boolean fijo) {
            this.fijo = fijo;
        }        
    }
}
