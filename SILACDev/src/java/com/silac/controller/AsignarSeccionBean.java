/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblempleado;
import com.silac.model.Tblpersona;
import com.silac.model.Tblplanificaciontrabajo;
import com.silac.model.Tblseccion;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author siman
 */
@ManagedBean(name = "asignarSeccionBean")
@ViewScoped
public class AsignarSeccionBean implements Serializable {

    private Date hoy = new Date();
    private DualListModel<Tblseccion> listaSecciones;
    private ArrayList empleados = new ArrayList();
    private List<Short> selectedEmpleado;
    private Date fecha = new Date();
    private ArrayList<SeccionAsignada> listaAsignadas = new ArrayList<SeccionAsignada>();
    private ArrayList<SelectItem> listaEmpleados = new ArrayList<SelectItem>();
    //private Tblasignarseccion asignar = new Tblasignarseccion();

    /** Creates a new instance of AsignarSeccionBean */
    public AsignarSeccionBean() {
        this.init();
        System.out.println("fecha default:" + fecha);
    }

    private void init() {
        this.hoy = new Date();
        this.listaSecciones = new DualListModel<Tblseccion>();
        this.selectedEmpleado = null;
        this.fecha = new Date();
        this.listaAsignadas = new ArrayList<SeccionAsignada>();
        cargarSecciones();
        cargarAsignadas();
        cargarListaEmpleados();
    }

    public void nuevo() {
        this.init();

    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

            Calendar cInicio = Calendar.getInstance();
            cInicio.setTime(this.fecha);
            cInicio.set(Calendar.MILLISECOND, 0);
            cInicio.set(Calendar.SECOND, 0);
            cInicio.set(Calendar.MINUTE, 0);
            cInicio.set(Calendar.HOUR, 0);
            System.out.println("SIZE EMPLEADO:" + selectedEmpleado.size());

            Calendar cFinal = Calendar.getInstance();
            cFinal.setTime(this.fecha);
            cFinal.set(Calendar.MILLISECOND, 999);
            cFinal.set(Calendar.SECOND, 59);
            cFinal.set(Calendar.MINUTE, 59);
            cFinal.set(Calendar.HOUR, 23);
            System.out.println("fecha final:" + cFinal);

            Iterator iter = selectedEmpleado.iterator();
            while (iter.hasNext()) {
                String id = (String) iter.next();
                Tblempleado emp = (Tblempleado) session.load(Tblempleado.class, Short.parseShort(id));
                Criteria cPlaniTrab = session.createCriteria(Tblplanificaciontrabajo.class);
                cPlaniTrab.add(Restrictions.eq("tblempleado", emp));
                cPlaniTrab.add(Restrictions.between("fechaEntrada", cInicio.getTime(), cFinal.getTime()));
                if (cPlaniTrab.uniqueResult() != null) {
                    Tblplanificaciontrabajo pt = (Tblplanificaciontrabajo) cPlaniTrab.uniqueResult();
                    //Tblplanificaciontrabajo pt = (Tblplanificaciontrabajo) session.load(Tblplanificaciontrabajo.class, id);
                    System.out.println("PASA AK" + id);

                    //List seccionTarget = listaSecciones.getTarget();
                    System.out.println("FECHA DE ENTRADA" + pt.getFechaEntrada());
                    Iterator iterTarget = this.listaSecciones.getTarget().iterator();
                    System.out.println("SIZE DE SECCION TARGET");
                    if (iterTarget.hasNext()) {
                        System.out.println("PASA ITERTARGET");
                        Set<Tblseccion> secciones = new HashSet<Tblseccion>();
                        System.out.println("PASA HASH");
                        while (iterTarget.hasNext()) {
                            System.out.println("PASA WHILE");
                            Tblseccion s = (Tblseccion) iterTarget.next();
                            Tblseccion tempSec = (Tblseccion) session.load(Tblseccion.class, s.getIdSeccionLaboratorio());

                            secciones.add(tempSec);

                        }
                        pt.setTblseccions(secciones);
                        session.saveOrUpdate(pt);
                    }
                }
            }
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La Sección se asignó satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }
    //selectListener="#{asignarSeccionBean.handleDateSelect}"

    public void handleDateSelect(DateSelectEvent event) {
        this.fecha = event.getDate();
        System.out.println("fecha:" + fecha);
        cargarAsignadas();
        cargarListaEmpleados();
    }

    public void cargarListaEmpleados() {
        this.listaEmpleados = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        System.out.println("FECHA"+fecha);
        
        Calendar cInicio = Calendar.getInstance();
        cInicio.setTime(this.fecha);
        cInicio.set(Calendar.MILLISECOND, 0);
        cInicio.set(Calendar.SECOND, 0);
        cInicio.set(Calendar.MINUTE, 0);
        cInicio.set(Calendar.HOUR, 0);

        Calendar cFinal = Calendar.getInstance();
        cFinal.setTime(this.fecha);
        cFinal.set(Calendar.MILLISECOND, 999);
        cFinal.set(Calendar.SECOND, 59);
        cFinal.set(Calendar.MINUTE, 59);
        cFinal.set(Calendar.HOUR, 23);


        //Obtiene la lista de empleados
        String sql = "SELECT p, e, pt FROM Tblpersona p "
                + "join p.tblempleados e "
                + "join e.tblplanificaciontrabajos pt "
                + "WHERE pt.fechaEntrada BETWEEN :inicio AND :final "
                + "GROUP BY e.codigoMarcacion "
                + "ORDER BY p.primerApellido";
        Query query = session.createQuery(sql);
        query.setParameter("inicio", cInicio.getTime());
        query.setParameter("final", cFinal.getTime());
        List result = (ArrayList) query.list();
        Iterator itEmpleado = result.iterator();
        while (itEmpleado.hasNext()) {
            Object[] o = (Object[]) itEmpleado.next();
            Tblpersona p = (Tblpersona) o[0];
            Tblempleado e = (Tblempleado) o[1];
            Tblplanificaciontrabajo pt = (Tblplanificaciontrabajo) o[2];
            String nombre = FacesUtil.emptyString(p.getPrimerNombre()) + " " + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " " + FacesUtil.emptyString(p.getSegundoApellido());
            listaEmpleados.add(new SelectItem(e.getCodigoMarcacion(), nombre, ""));
        }
        session.close();
       
    }

    public void cargarSecciones() {
        this.listaSecciones = new DualListModel<Tblseccion>();
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        List<Tblseccion> seccionesSource = new ArrayList<Tblseccion>();
        List<Tblseccion> seccionesTarget = new ArrayList<Tblseccion>();

        Session session = HibernateUtil.getSessionFactory().openSession();

        String sql = "SELECT s FROM Tblseccion s ORDER BY s.nombreSeccion";
        result = (ArrayList) session.createQuery(sql).list();

        for (Object r : result) {
            Tblseccion s = (Tblseccion) r;
            seccionesSource.add(s);
        }
        listaSecciones = new DualListModel<Tblseccion>(seccionesSource, seccionesTarget);
        session.close();
    }

    public void cargarAsignadas() {
        this.listaAsignadas = new ArrayList<SeccionAsignada>();
        Session session = HibernateUtil.getSessionFactory().openSession();

        System.out.println("ENTRA A CARGAR ASIGNADAS");

        Calendar cInicio = Calendar.getInstance();
        cInicio.setTime(this.fecha);
        cInicio.set(Calendar.MILLISECOND, 0);
        cInicio.set(Calendar.SECOND, 0);
        cInicio.set(Calendar.MINUTE, 0);
        cInicio.set(Calendar.HOUR, 0);

        Calendar cFinal = Calendar.getInstance();
        cFinal.setTime(this.fecha);
        cFinal.set(Calendar.MILLISECOND, 999);
        cFinal.set(Calendar.SECOND, 59);
        cFinal.set(Calendar.MINUTE, 59);
        cFinal.set(Calendar.HOUR, 23);

        String sql = "SELECT p, e, pt FROM Tblpersona p "
                + "join p.tblempleados e "
                + "join e.tblplanificaciontrabajos pt "
                + "WHERE pt.fechaEntrada BETWEEN :inicio AND :final "
                + "GROUP BY e.codigoMarcacion "
                + "ORDER BY p.primerApellido";
        Query query = session.createQuery(sql);
        query.setParameter("inicio", cInicio.getTime());
        query.setParameter("final", cFinal.getTime());
        List result = (ArrayList) query.list();
        System.out.println("CARGA CONSULTA:" + result.size());
        Iterator itEmpleado = result.iterator();
        while (itEmpleado.hasNext()) {
            Object[] o = (Object[]) itEmpleado.next();
            Tblpersona p = (Tblpersona) o[0];
            Tblempleado e = (Tblempleado) o[1];
            Tblplanificaciontrabajo pt = (Tblplanificaciontrabajo) o[2];
            Set<Tblseccion> s = pt.getTblseccions();
            System.out.println("CARGA ALGO" + pt.getFechaEntrada());

            ArrayList<String> secAsig = new ArrayList<String>();

            Iterator iterSec = s.iterator();
            while (iterSec.hasNext()) {
                Tblseccion tempSec = (Tblseccion) iterSec.next();
                secAsig.add(tempSec.getNombreSeccion());
                System.out.println("SECCION ASIGNADA" + tempSec.getNombreSeccion());
            }

            String nombre = FacesUtil.emptyString(p.getPrimerNombre()) + " " + FacesUtil.emptyString(p.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(p.getPrimerApellido()) + " " + FacesUtil.emptyString(p.getSegundoApellido());

            
            listaAsignadas.add(new SeccionAsignada(p, e, pt, secAsig));
            System.out.println("size de lista asignada" + listaAsignadas.size());
            Iterator iterImp = listaAsignadas.iterator();
            while (iterImp.hasNext()) {
                SeccionAsignada sec = (SeccionAsignada) iterImp.next();
                System.out.println("NOMBRE EMPLEADO" + sec.getNombre());
            }
        }
        session.close();
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public DualListModel<Tblseccion> getListaSecciones() {
        return listaSecciones;
    }

    public void setListaSecciones(DualListModel<Tblseccion> listaSecciones) {
        this.listaSecciones = listaSecciones;
    }

    public ArrayList getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList empleados) {
        this.empleados = empleados;
    }

    public List<Short> getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(List<Short> selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ArrayList<SeccionAsignada> getListaAsignadas() {
        return listaAsignadas;
    }

    public void setListaAsignadas(ArrayList<SeccionAsignada> listaAsignadas) {
        this.listaAsignadas = listaAsignadas;
    }

    public ArrayList<SelectItem> getListaEmpleados() {
        return listaEmpleados;
    }

    public void setListaEmpleados(ArrayList<SelectItem> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
    
    

    public class SeccionAsignada implements Serializable {

        private String nombre;
        private Tblpersona persona;
        private Tblempleado empleado;
        private Tblplanificaciontrabajo planificacion;
        private ArrayList<String> secAsignada;

        public SeccionAsignada() {
        }

        public SeccionAsignada(Tblpersona persona, Tblempleado empleado, Tblplanificaciontrabajo planificacion, ArrayList<String> secAsignada) {
            this.persona = persona;
            this.empleado = empleado;
            this.planificacion = planificacion;
            this.secAsignada = secAsignada;
            System.out.println("SECCION ASIGNADA en la clase:" + secAsignada);
        }

        public Tblempleado getEmpleado() {
            return empleado;
        }

        public void setEmpleado(Tblempleado empleado) {
            this.empleado = empleado;
        }

        public String getNombre() {
            String cadena = "";
            cadena = FacesUtil.emptyString(this.persona.getPrimerNombre()) + " "
                    + FacesUtil.emptyString(this.persona.getSegundoNombre()) + " "
                    + FacesUtil.emptyString(this.persona.getPrimerApellido()) + " "
                    + FacesUtil.emptyString(this.persona.getSegundoApellido());
            nombre = cadena;
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Tblpersona getPersona() {
            return persona;
        }

        public void setPersona(Tblpersona persona) {
            this.persona = persona;
        }

        public Tblplanificaciontrabajo getPlanificacion() {
            return planificacion;
        }

        public void setPlanificacion(Tblplanificaciontrabajo planificacion) {
            this.planificacion = planificacion;
        }

        public ArrayList<String> getSecAsignada() {
            return secAsignada;
        }

        public void setSecAsignada(ArrayList<String> secAsignada) {
            this.secAsignada = secAsignada;
        }
        
        
    }
}
