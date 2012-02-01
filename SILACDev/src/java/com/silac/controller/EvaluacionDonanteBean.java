/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.TblantecedentesDonante;
import com.silac.model.Tblcriterio;
import com.silac.model.TblcuestionarioEvaluacion;
import com.silac.model.Tbldomicilio;
import com.silac.model.Tbldonante;
import com.silac.model.Tblevaluaciondonante;
import com.silac.model.Tblevaluacionfisica;
import com.silac.model.Tblexcluido;
import com.silac.model.Tbllugtrabajo;
import com.silac.model.Tblpaciente;
import com.silac.model.Tblpersona;
import com.silac.model.TblrespuestaCriterio;
import com.silac.model.TblrespuestaCuestionario;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author kerberoz
 */
@ManagedBean(name = "evaluacionDonanteBean")
@ViewScoped
public class EvaluacionDonanteBean implements Serializable {

    private Tblpersona persona;
    private Tbldonante donante;
    private Tbldomicilio domicilio;
    private Tbllugtrabajo lugarTrabajo;
    private Tblevaluaciondonante evaluacion;
    private Tblevaluacionfisica evalFisica;
    private TblrespuestaCriterio respuestaCriterio;
    private TblrespuestaCuestionario respuestaCuestionario;
    private TblantecedentesDonante antecedentes;
    private Tblexcluido exclusion;
    private ArrayList<SelectItem> listaMunicipios;
    private ArrayList<Evaluacion> listaCuestionario;
    private Short selectedDepto;
    private Short selectedMunicipio;
    private Date hoy;
    private ArrayList<Object[]> listaDonantes;
    private Object selectedDonante;
    private Evaluacion selectedEvaluacion;
    private Criterio selectedCriterio;
    private Tblpaciente paciente;
    private boolean chkIdentificacion;
    private boolean cargarPaciente;
    private boolean chkDomicilio;
    private boolean chkTrabajo;
    private boolean cargarPersona;
    private boolean rechazar;
    private boolean cargarFechaNacimiento;
    private boolean cargarSexo;
    private boolean showConfirmPregunta;
    private boolean showConfirmCriterio;
    private String docId;
    private String idRadio;
    private Integer indexRow;
    private boolean diferido;
    private boolean renderMotivo;
    private boolean renderDiferido;

    /** Creates a new instance of EvaluacionDonanteBean */
    public EvaluacionDonanteBean() {
        this.init();
    }

    private void init() {
        this.docId = "";
        this.hoy = new Date();
        this.cargarFechaNacimiento = false;
        this.cargarSexo = false;
        this.persona = new Tblpersona();
        this.donante = new Tbldonante();
        this.domicilio = new Tbldomicilio();
        this.lugarTrabajo = new Tbllugtrabajo();
        this.antecedentes = new TblantecedentesDonante();
        this.evalFisica = new Tblevaluacionfisica();
        this.evaluacion = new Tblevaluaciondonante();
        this.exclusion = new Tblexcluido();
        this.selectedEvaluacion = null;
        this.selectedCriterio = null;
        this.cargarListaCuestionario();
        this.rechazar = false;
        this.showConfirmPregunta = false;
        this.showConfirmCriterio = false;
        this.idRadio = null;
    }

    public void buscar() {
        PersonaBean p = (PersonaBean) FacesUtil.getBean("personasBean");
        this.listaDonantes = p.buscar();
    }

    public void limpiar() {
        this.cargarFechaNacimiento = false;
        this.cargarSexo = false;
        this.persona = new Tblpersona();
        this.donante = new Tbldonante();
        this.domicilio = new Tbldomicilio();
        this.lugarTrabajo = new Tbllugtrabajo();
    }

    public void onSelectRespuesta() {
        diferido = false;
        this.showConfirmCriterio = false;
        this.rechazar = false;
        this.evaluacion.setResultado("Aprobado");
        String boton = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("boton");
        if (boton.equals("si")) {
            this.selectedEvaluacion.getRespuestaPregunta().setRespuesta(true);
            this.listaCuestionario.set(this.listaCuestionario.indexOf(selectedEvaluacion), selectedEvaluacion);
        } else {
            this.selectedEvaluacion.getRespuestaPregunta().setRespuesta(false);
            this.listaCuestionario.set(this.listaCuestionario.indexOf(selectedEvaluacion), selectedEvaluacion);
        }
        //Evaluacion selectedPregunta = this.listaCuestionario.get(this.indexRow.intValue());
        boolean rechazarDonante = this.selectedEvaluacion.getPregunta().isRechazarDonante();
        boolean respuesta = false;
        if (this.selectedEvaluacion.getRespuestaPregunta().getRespuesta() != null) {
            respuesta = this.selectedEvaluacion.getRespuestaPregunta().getRespuesta().booleanValue();
        }
        this.rechazar = false;
        this.showConfirmPregunta = false;
        if (this.selectedEvaluacion.getPregunta().getTblcriterios().isEmpty()) {
            if (rechazarDonante == respuesta) {
                this.rechazar = true;
                this.showConfirmPregunta = true;
                this.showConfirmCriterio = false;
            }
        }
    }

    public void onSelectCriterio() {
        Iterator iter = this.selectedEvaluacion.getListaCriterios().iterator();
        diferido = false;
        this.showConfirmCriterio = false;
        this.rechazar = false;
        this.evaluacion.setResultado("Aprobado");
        while (iter.hasNext()) {
            Criterio criterio = (Criterio) iter.next();
            //criterio.setRespuestaCriterio(new TblrespuestaCriterio());
            if (criterio.equals(this.selectedCriterio)) {
                if (this.selectedCriterio.getRespuestaCriterio().getRespuesta() == null || this.selectedCriterio.getRespuestaCriterio().getRespuesta() == false) {
                    criterio.getRespuestaCriterio().setRespuesta(true);
                    String periodo = criterio.getCriterio().getPeriodoExclusion();
                    if (!periodo.equals("Sin exclusion")) {
                        this.showConfirmCriterio = true;
                        this.rechazar = true;
                        if (!periodo.equals("Permanente")) {
                            this.diferido = true;
                        }
                    }
                } else {
                    criterio.getRespuestaCriterio().setRespuesta(false);
                }
                this.selectedEvaluacion.getListaCriterios().set(this.selectedEvaluacion.getListaCriterios().indexOf(this.selectedCriterio), criterio);
            } else {
                //if (this.selectedCriterio.getRespuestaCriterio().getRespuesta() != null) {
                //  criterio.getRespuestaCriterio().setRespuesta(false);
                //this.selectedEvaluacion.getListaCriterios().set(this.selectedEvaluacion.getListaCriterios().indexOf(criterio), criterio);
                //}
            }
        }
    }

    public void reiniciarCuestionario() {
        if (!this.showConfirmCriterio) {
            this.selectedEvaluacion.setRespuestaPregunta(new TblrespuestaCuestionario());
            this.listaCuestionario.set(this.listaCuestionario.indexOf(selectedEvaluacion), selectedEvaluacion);
        } else {
            this.selectedCriterio.setRespuestaCriterio(new TblrespuestaCriterio());
            this.selectedEvaluacion.listaCriterios.set(this.selectedEvaluacion.listaCriterios.indexOf(this.selectedCriterio), selectedCriterio);
        }
        this.rechazar = false;
        this.showConfirmCriterio = false;
        this.showConfirmPregunta = false;
    }

    public void onSelectDepto() {
        DomicilioBean d = (DomicilioBean) FacesUtil.getBean("domicilioBean");
        this.listaMunicipios = d.cargarListaMunicipios(this.selectedDepto);
    }

    public void onSelectDocId(SelectEvent event) {
        this.onKeyPress();
    }

    public void onKeyPress() {
        //String id = ((Object[]) event.getObject())[3].toString().toString();
        Session session = HibernateUtil.getSessionFactory().openSession();

        //Carga los datos personales del paciente
        Criteria cPersona = session.createCriteria(Tblpersona.class);
        cPersona.add(Restrictions.like("docId", this.docId));
        cPersona.setMaxResults(1);
        if (cPersona.uniqueResult() == null) {
            this.limpiar();
        } else {
            this.persona = (Tblpersona) cPersona.uniqueResult();
            //Criteria cPersona = session.createCriteria(Tblpersona.class);
            //cPersona.add(Restrictions.eq("idPersona", Long.parseLong(id)));
            //this.persona = (Tblpersona) paciente.getTblpersona();
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

    public void onClickRechazar() {
        this.renderMotivo = false;
        this.renderDiferido = false;
        if (this.diferido) {
            this.evaluacion.setResultado("Diferido");
            this.renderMotivo = true;
            this.renderDiferido = true;
        } else if (this.rechazar) {
            this.evaluacion.setResultado("Rechazado");
            this.renderMotivo = true;
        } else {
            this.evaluacion.setResultado("Aprobado");
        }
    }

    public void cargarListaCuestionario() {
        this.listaCuestionario = new ArrayList<Evaluacion>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cCuestionario = session.createCriteria(TblcuestionarioEvaluacion.class);
        cCuestionario.addOrder(Order.asc("numeroPregunta"));
        Iterator iter = cCuestionario.list().iterator();
        while (iter.hasNext()) {
            TblcuestionarioEvaluacion ce = (TblcuestionarioEvaluacion) iter.next();
            ArrayList<Criterio> criterios = new ArrayList<Criterio>();
            if (ce.getTblcriterios() != null) {
                Set<Tblcriterio> cr = (Set<Tblcriterio>) ce.getTblcriterios();
                Iterator itCriterio = cr.iterator();
                while (itCriterio.hasNext()) {
                    Tblcriterio tempCriterio = (Tblcriterio) itCriterio.next();
                    criterios.add(new Criterio(new TblrespuestaCriterio(), tempCriterio));
                }
            }
            this.listaCuestionario.add(new Evaluacion(ce, new TblrespuestaCuestionario(), criterios));
        }
        session.close();
    }

    public void onChangeResultado() {
        this.renderMotivo = false;
        this.renderDiferido = false;
        if (this.evaluacion.getResultado().equals("Rechazado")) {
            this.renderMotivo = true;
        } else if (this.evaluacion.getResultado().equals("Diferido")) {
            this.renderMotivo = true;
            this.renderDiferido = true;
        }
    }

    public ArrayList<SelectItem> cargarListaRespuestaCuestionario() {
        ArrayList<SelectItem> listaRespuesta = new ArrayList<SelectItem>();
        listaRespuesta.add(new SelectItem(true, "Si", ""));
        listaRespuesta.add(new SelectItem(false, "No", ""));
        return listaRespuesta;
    }

    public ArrayList<SelectItem> cargarListaPrimeraVez() {
        ArrayList<SelectItem> listaPrimeraVez = new ArrayList<SelectItem>();
        listaPrimeraVez.add(new SelectItem(true, "Si", ""));
        listaPrimeraVez.add(new SelectItem(false, "No", ""));
        return listaPrimeraVez;
    }

    public ArrayList<SelectItem> cargarListaDonanteHabitual() {
        ArrayList<SelectItem> listaDonanteHabitual = new ArrayList<SelectItem>();
        listaDonanteHabitual.add(new SelectItem(true, "Si", ""));
        listaDonanteHabitual.add(new SelectItem(false, "No", ""));
        return listaDonanteHabitual;
    }

    public ArrayList<SelectItem> cargarListaVoluntario() {
        ArrayList<SelectItem> listaVoluntario = new ArrayList<SelectItem>();
        listaVoluntario.add(new SelectItem(true, "Si", ""));
        listaVoluntario.add(new SelectItem(false, "No", ""));
        return listaVoluntario;
    }

    public ArrayList<SelectItem> cargarListaHemograma() {
        ArrayList<SelectItem> listaHemograma = new ArrayList<SelectItem>();
        listaHemograma.add(new SelectItem(true, "Normal", ""));
        listaHemograma.add(new SelectItem(false, "Anormal", ""));
        return listaHemograma;
    }

    public ArrayList<SelectItem> cargarListaHemoglobina() {
        ArrayList<SelectItem> listaHemoglobina = new ArrayList<SelectItem>();
        listaHemoglobina.add(new SelectItem(true, "Normal", ""));
        listaHemoglobina.add(new SelectItem(false, "Anormal", ""));
        return listaHemoglobina;
    }

    public ArrayList<SelectItem> cargarListaComida() {
        ArrayList<SelectItem> listaComida = new ArrayList<SelectItem>();
        listaComida.add(new SelectItem(true, "Si", ""));
        listaComida.add(new SelectItem(false, "No", ""));
        return listaComida;
    }

    public ArrayList<SelectItem> cargarListaResultado() {
        ArrayList<SelectItem> listaComida = new ArrayList<SelectItem>();
        listaComida.add(new SelectItem("Aprobado", "Aprobado", ""));
        listaComida.add(new SelectItem("Rechazado", "Rechazado", ""));
        listaComida.add(new SelectItem("Diferido", "Diferido", ""));
        return listaComida;
    }
    /*
     * Getters & Setters
     */

    public ArrayList<SelectItem> getListaMunicipios() {
        return listaMunicipios;
    }

    public void setListaMunicipios(ArrayList<SelectItem> listaMunicipios) {
        this.listaMunicipios = listaMunicipios;
    }

    public Tblpersona getPersona() {
        return persona;
    }

    public void setPersona(Tblpersona persona) {
        this.persona = persona;
    }

    public Short getSelectedDepto() {
        return selectedDepto;
    }

    public void setSelectedDepto(Short selectedDepto) {
        this.selectedDepto = selectedDepto;
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

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public ArrayList<Object[]> getListaDonantes() {
        return listaDonantes;
    }

    public void setListaDonantes(ArrayList<Object[]> listaDonantes) {
        this.listaDonantes = listaDonantes;
    }

    public Object getSelectedDonante() {
        return selectedDonante;
    }

    public void setSelectedDonante(Object selectedDonante) {
        this.selectedDonante = selectedDonante;
    }

    public Short getSelectedMunicipio() {
        return selectedMunicipio;
    }

    public void setSelectedMunicipio(Short selectedMunicipio) {
        this.selectedMunicipio = selectedMunicipio;
    }

    public Tbldomicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Tbldomicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Tbllugtrabajo getLugarTrabajo() {
        return lugarTrabajo;
    }

    public void setLugarTrabajo(Tbllugtrabajo lugarTrabajo) {
        this.lugarTrabajo = lugarTrabajo;
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

    public boolean isChkTrabajo() {
        return chkTrabajo;
    }

    public void setChkTrabajo(boolean chkTrabajo) {
        this.chkTrabajo = chkTrabajo;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Tbldonante getDonante() {
        return donante;
    }

    public void setDonante(Tbldonante donante) {
        this.donante = donante;
    }

    public Tblevaluacionfisica getEvalFisica() {
        return evalFisica;
    }

    public void setEvalFisica(Tblevaluacionfisica evalFisica) {
        this.evalFisica = evalFisica;
    }

    public Tblevaluaciondonante getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Tblevaluaciondonante evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Tblpaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Tblpaciente paciente) {
        this.paciente = paciente;
    }

    public TblrespuestaCriterio getRespuestaCriterio() {
        return respuestaCriterio;
    }

    public void setRespuestaCriterio(TblrespuestaCriterio respuestaCriterio) {
        this.respuestaCriterio = respuestaCriterio;
    }

    public TblrespuestaCuestionario getRespuestaCuestionario() {
        return respuestaCuestionario;
    }

    public void setRespuestaCuestionario(TblrespuestaCuestionario respuestaCuestionario) {
        this.respuestaCuestionario = respuestaCuestionario;
    }

    public TblantecedentesDonante getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(TblantecedentesDonante antecedentes) {
        this.antecedentes = antecedentes;
    }

    public ArrayList<Evaluacion> getListaCuestionario() {
        return listaCuestionario;
    }

    public void setListaCuestionario(ArrayList<Evaluacion> listaCuestionario) {
        this.listaCuestionario = listaCuestionario;
    }

    public Evaluacion getSelectedEvaluacion() {
        return selectedEvaluacion;
    }

    public void setSelectedEvaluacion(Evaluacion selectedEvaluacion) {
        this.selectedEvaluacion = selectedEvaluacion;
    }

    public boolean isRechazar() {
        return rechazar;
    }

    public void setRechazar(boolean rechazar) {
        this.rechazar = rechazar;
    }

    public String getIdRadio() {
        return idRadio;
    }

    public void setIdRadio(String idRadio) {
        this.idRadio = idRadio;
    }

    public Integer getIndexRow() {
        return indexRow;
    }

    public void setIndexRow(Integer indexRow) {
        this.indexRow = indexRow;
    }

    public Criterio getSelectedCriterio() {
        return selectedCriterio;
    }

    public void setSelectedCriterio(Criterio selectedCriterio) {
        this.selectedCriterio = selectedCriterio;
    }

    public boolean isShowConfirmCriterio() {
        return showConfirmCriterio;
    }

    public void setShowConfirmCriterio(boolean showConfirmCriterio) {
        this.showConfirmCriterio = showConfirmCriterio;
    }

    public boolean isShowConfirmPregunta() {
        return showConfirmPregunta;
    }

    public void setShowConfirmPregunta(boolean showConfirmPregunta) {
        this.showConfirmPregunta = showConfirmPregunta;
    }

    public boolean isDiferido() {
        return diferido;
    }

    public void setDiferido(boolean diferido) {
        this.diferido = diferido;
    }

    public Tblexcluido getExclusion() {
        return exclusion;
    }

    public void setExclusion(Tblexcluido exclusion) {
        this.exclusion = exclusion;
    }

    public boolean isrenderDiferido() {
        return renderDiferido;
    }

    public void setrenderDiferido(boolean renderDiferido) {
        this.renderDiferido = renderDiferido;
    }

    public boolean isRenderMotivo() {
        return renderMotivo;
    }

    public void setRenderMotivo(boolean renderMotivo) {
        this.renderMotivo = renderMotivo;
    }

    public static class Evaluacion implements Serializable {

        private TblcuestionarioEvaluacion pregunta;
        private TblrespuestaCuestionario respuestaPregunta;
        private ArrayList<Criterio> listaCriterios;

        public Evaluacion() {
        }

        public Evaluacion(TblcuestionarioEvaluacion pregunta, TblrespuestaCuestionario respuestaPregunta, ArrayList<Criterio> listaCriterios) {
            this.pregunta = pregunta;
            this.respuestaPregunta = respuestaPregunta;
            this.listaCriterios = listaCriterios;
        }

        /*
         * Getters & Setters
         */
        public ArrayList<Criterio> getListaCriterios() {
            return listaCriterios;
        }

        public void setListaCriterios(ArrayList<Criterio> listaCriterios) {
            this.listaCriterios = listaCriterios;
        }

        public TblcuestionarioEvaluacion getPregunta() {
            return pregunta;
        }

        public void setPregunta(TblcuestionarioEvaluacion pregunta) {
            this.pregunta = pregunta;
        }

        public TblrespuestaCuestionario getRespuestaPregunta() {
            return respuestaPregunta;
        }

        public void setRespuestaPregunta(TblrespuestaCuestionario respuestaPregunta) {
            this.respuestaPregunta = respuestaPregunta;
        }
    }

    public static class Criterio implements Serializable {

        private TblrespuestaCriterio respuestaCriterio;
        private Tblcriterio criterio;

        public Criterio() {
        }

        public Criterio(TblrespuestaCriterio respuestaCriterio, Tblcriterio criterio) {
            this.respuestaCriterio = respuestaCriterio;
            this.criterio = criterio;
        }

        public Tblcriterio getCriterio() {
            return criterio;
        }

        public void setCriterio(Tblcriterio criterio) {
            this.criterio = criterio;
        }

        public TblrespuestaCriterio getRespuestaCriterio() {
            return respuestaCriterio;
        }

        public void setRespuestaCriterio(TblrespuestaCriterio respuestaCriterio) {
            this.respuestaCriterio = respuestaCriterio;
        }
    }
}
