/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblentregaresultado;
import com.silac.model.Tblexamen;
import com.silac.model.TblexamenOrden;
import com.silac.model.Tblincidencia;
import com.silac.model.Tblorden;
import com.silac.model.Tblpersona;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author kerberoz
 */
@ManagedBean(name = "entregaBean")
@ViewScoped
public class EntregaBean implements Serializable {

    private Tblpersona persona;
    private OrdenFinalizada[] selectedOrden;
    private boolean cargarPersona;
    private ArrayList<Object[]> listaPersonas;
    private Object[] selectedResultadoBusqueda;

    /** Creates a new instance of EntregaBean */
    public EntregaBean() {
        this.init();
    }

    private void init() {
        this.persona = new Tblpersona();
        this.cargarPersona = false;
        this.listaPersonas = new ArrayList<Object[]>();
        this.selectedResultadoBusqueda = null;
        PersonaBean p = (PersonaBean) FacesUtil.getBean("personasBean");
        p.setBuscarDocId(null);
        p.setBuscarExpediente(null);
        p.setBuscarNombre(null);
    }

    /** Crea una nueva seccion en la base de datos.*/
    public void crear() {
        if (this.selectedOrden.length != 0) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = (Transaction) session.beginTransaction();
                /*if (!this.cargarPersona) {
                session.save(this.persona);
                }*/
                session.saveOrUpdate(this.persona);
                for (int i = 0; i < this.selectedOrden.length; i++) {
                    OrdenFinalizada of = this.selectedOrden[i];
                    TblexamenOrden eo = (TblexamenOrden) session.load(TblexamenOrden.class, of.idExamenOrden);
                    Tblentregaresultado entrega = new Tblentregaresultado();
                    entrega.setTblexamenOrden(eo);
                    entrega.setFechaEntrega(new Date());
                    entrega.setTblpersona(persona);
                    session.save(entrega);
                    eo.setEstado("Entregado");
                    session.saveOrUpdate(eo);
                }
                tx.commit();
                session.flush();
                session.clear();
                this.init();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La entrega de resultados se registro satisfactoriamente"));
            } catch (HibernateException e) {
                tx.rollback();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
            } finally {
                session.close();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", "No hay resultados pendientes de entregar"));
        }
    }

    public void buscar() {
        PersonaBean p = (PersonaBean) FacesUtil.getBean("personasBean");
        this.listaPersonas = p.buscar();
    }

    public void limpiar() {
        PersonaBean p = (PersonaBean) FacesUtil.getBean("personasBean");
        p.setBuscarDocId(null);
        p.setBuscarExpediente(null);
        p.setBuscarNombre(null);
        this.persona = new Tblpersona();
        this.cargarPersona = false;
        this.listaPersonas = new ArrayList<Object[]>();
    }

    public ArrayList<OrdenFinalizada> cargarOrdenesFinalizadas() {
        ArrayList<OrdenFinalizada> listaOrdenFinalziada = new ArrayList<OrdenFinalizada>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT eo, p, ex, o, pe FROM Tblpersona p "
                + "JOIN p.tblpacientes pa "
                + "JOIN pa.tblordens o "
                + "JOIN o.tblexamenOrdens eo "
                + "JOIN eo.tblexamen ex "
                + "JOIN eo.tblempleado e "
                + "JOIN e.tblpersona pe "
                + "WHERE eo.estado LIKE 'Finalizado'";
        Query query = session.createQuery(sql);
        Iterator iterOrdenes = query.list().iterator();
        while (iterOrdenes.hasNext()) {
            Object[] obj = (Object[]) iterOrdenes.next();
            TblexamenOrden eo = (TblexamenOrden) obj[0];
            Tblpersona p = (Tblpersona) obj[1];
            Tblexamen ex = (Tblexamen) obj[2];
            Tblorden o = (Tblorden) obj[3];
            Tblpersona pe = (Tblpersona) obj[4];
            String paciente = FacesUtil.emptyString(p.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(p.getSegundoApellido());
            String tecnico = FacesUtil.emptyString(pe.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(pe.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(pe.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(pe.getSegundoApellido());
            listaOrdenFinalziada.add(new OrdenFinalizada(paciente, tecnico,
                    ex.getNombreExamen(),
                    ex.getCodigoExamen(),
                    eo.getNumeroControl(),
                    FacesUtil.getStringDate(o.getFechaRecepcion()),
                    eo.getIdExamenOrden(), new Tblincidencia()));
        }
        session.close();
        return listaOrdenFinalziada;
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Persona seleccionada", ((Object[]) event.getObject())[0].toString());
        String id = ((Object[]) event.getObject())[3].toString().toString();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cPersona = session.createCriteria(Tblpersona.class);
        cPersona.add(Restrictions.eq("idPersona", Long.parseLong(id)));
        this.persona = (Tblpersona) cPersona.uniqueResult();
        session.close();
        FacesContext.getCurrentInstance().addMessage(null, msg);
        this.cargarPersona = true;
    }

    /**
     * Getters & Setter
     */
    public Tblpersona getPersona() {
        return persona;
    }

    public void setPersona(Tblpersona persona) {
        this.persona = persona;
    }

    public OrdenFinalizada[] getSelectedOrden() {
        return selectedOrden;
    }

    public void setSelectedOrden(OrdenFinalizada[] selectedOrden) {
        this.selectedOrden = selectedOrden;
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

    public Object[] getSelectedResultadoBusqueda() {
        return selectedResultadoBusqueda;
    }

    public void setSelectedResultadoBusqueda(Object[] selectedResultadoBusqueda) {
        this.selectedResultadoBusqueda = selectedResultadoBusqueda;
    }

    public class OrdenFinalizada implements Serializable {

        private String paciente;
        private String tecnico;
        private String examen;
        private String codigo;
        private Long numControl;
        private String fecha;
        private Long idExamenOrden;
        private Tblincidencia incidencia;

        public OrdenFinalizada(String paciente, String tecnico, String examen, String codigo, Long numControl, String fecha, Long idExamenOrden, Tblincidencia incidencia) {
            this.paciente = paciente;
            this.tecnico = tecnico;
            this.examen = examen;
            this.codigo = codigo;
            this.numControl = numControl;
            this.fecha = fecha;
            this.idExamenOrden = idExamenOrden;
            this.incidencia = incidencia;
        }

        public OrdenFinalizada() {
        }

        public String getCodigo() {
            return codigo;
        }

        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }

        public String getExamen() {
            return examen;
        }

        public void setExamen(String examen) {
            this.examen = examen;
        }

        public String getFecha() {
            return fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public Long getIdExamenOrden() {
            return idExamenOrden;
        }

        public void setIdExamenOrden(Long idExamenOrdenl) {
            this.idExamenOrden = idExamenOrdenl;
        }

        public Tblincidencia getIncidencia() {
            return incidencia;
        }

        public void setIncidencia(Tblincidencia incidencia) {
            this.incidencia = incidencia;
        }

        public Long getNumControl() {
            return numControl;
        }

        public void setNumControl(Long numControl) {
            this.numControl = numControl;
        }

        public String getPaciente() {
            return paciente;
        }

        public void setPaciente(String paciente) {
            this.paciente = paciente;
        }

        public String getTecnico() {
            return tecnico;
        }

        public void setTecnico(String tecnico) {
            this.tecnico = tecnico;
        }
    }
}
