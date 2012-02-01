/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblcargalaboral;
import com.silac.model.Tblempleado;
import com.silac.model.Tblpersona;
import com.silac.model.Tblplanificaciontrabajo;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.Cell;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "planificacionBean")
@ViewScoped
public class PlanificacionBean implements Serializable {

    private ArrayList<SelectItem> listaMeses = new ArrayList<SelectItem>();
    private Short selectedAnio;
    private Short selectedMes;
    private String mes;
    private Boolean renderDtPlan = false;
    private List<String> columns;
    private int diasMes;
    private Short selectedDia;
    private Cell selectedCell;
    private Short selectedEmpleado;
    private String fechaEntrada;
    private int horaEntrada;
    private int minEntrada;
    private int horasTurno;
    private int minsTurno;
    private Tblplanificaciontrabajo plan = new Tblplanificaciontrabajo();
    private Boolean renderAsignarCargaLaboral = false;
    private Boolean renderMostrarCargaLaboral = false;
    private String horasAsignadas;
    private Long selectedCarga;
    private ArrayList empleados = new ArrayList();
    private List<Plan[]> planificacion = new ArrayList<Plan[]>();
    private Long selectedPlan;
    private String nombre;
    private Tblcargalaboral cargaLaboral = new Tblcargalaboral();

    /** Creates a new instance of PlanificacionBean */
    public PlanificacionBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.listaMeses = new ArrayList<SelectItem>();
        this.selectedAnio = null;
        this.selectedMes = null;
        this.renderDtPlan = false;
        this.columns = null;
        this.diasMes = 0;
        this.selectedDia = null;
        this.selectedCell = null;
        this.selectedEmpleado = null;
        this.fechaEntrada = null;
        this.horaEntrada = 0;
        this.minEntrada = 0;
        this.horasTurno = 0;
        this.minsTurno = 0;
        this.plan = new Tblplanificaciontrabajo();
        this.renderAsignarCargaLaboral = false;
        this.renderMostrarCargaLaboral = false;
        this.horasAsignadas = null;
        this.selectedCarga = null;
        this.empleados = new ArrayList();
        this.planificacion = new ArrayList<Plan[]>();
        this.selectedPlan = null;
        this.nombre = null;
        this.cargaLaboral = new Tblcargalaboral();
        this.populateListaMeses();
    }

    /**Crea una nueva entrada en la matriz de planificacion*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            if (this.selectedPlan != null) {
                this.plan = (Tblplanificaciontrabajo) session.load(Tblplanificaciontrabajo.class, selectedPlan);
            } else {
                this.plan = new Tblplanificaciontrabajo();
            }
            Calendar entrada = Calendar.getInstance();
            entrada.set(this.selectedAnio, this.selectedMes, this.selectedDia, this.horaEntrada, this.minEntrada, 0);
            Calendar salida = Calendar.getInstance();
            salida.set(this.selectedAnio, this.selectedMes, this.selectedDia, this.horaEntrada, this.minEntrada, 0);
            salida.add(Calendar.MINUTE, this.minsTurno);
            salida.add(Calendar.HOUR_OF_DAY, this.horasTurno);
            this.plan.setFechaEntrada(entrada.getTime());
            this.plan.setFechaSalida(salida.getTime());
            Tblempleado e = (Tblempleado) session.load(Tblempleado.class, this.selectedEmpleado);
            this.plan.setTblempleado(e);
            Tblcargalaboral c = (Tblcargalaboral) session.load(Tblcargalaboral.class, this.selectedCarga);
            this.plan.setTblcargalaboral(c);
            this.plan.setHoraDuracion(Short.parseShort(String.valueOf(this.horasTurno)));
            this.plan.setMinsDuracion(Short.parseShort(String.valueOf(this.minsTurno)));
            session.saveOrUpdate(this.plan);
            tx.commit();
            this.createDynamicColumns();
            this.selectedPlan = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El horario se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    /**Establece el numero de horas asignadas para laborar en el mes, aplica solamente a los turnos rotatios*/
    public void asignarCarga() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            this.cargaLaboral.setAnio(this.selectedAnio);
            this.cargaLaboral.setMes(this.selectedMes.byteValue());
            session.save(this.cargaLaboral);
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La carga laboral se asigno satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
        this.onSelectMes();
    }

    /**Elimina una entrada en la matriz de planificacion*/
    public void eliminar() {
        if (this.selectedPlan != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = (Transaction) session.beginTransaction();

                Tblplanificaciontrabajo p = (Tblplanificaciontrabajo) session.load(Tblplanificaciontrabajo.class, this.selectedPlan);
                session.delete(p);
                tx.commit();
                this.createDynamicColumns();
                this.selectedPlan = null;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El horario se elimino satisfactoriamente"));
            } catch (HibernateException e) {
                tx.rollback();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
            } finally {
                session.close();
            }
        }
    }

    /**Crea la matriz de planificacion correspondiente al mes y el año seleccionado por el usuario*/
    private void createDynamicColumns() {
        this.columns = new ArrayList<String>();
        this.planificacion = new ArrayList<Plan[]>();
        this.diasMes = FacesUtil.getMaxDiasMes(selectedAnio, selectedMes);
        String d;
        for (int i = 1; i <= diasMes; i++) {
            d = FacesUtil.getDia(selectedAnio, selectedMes, i);
            d = d.substring(0, 1);
            columns.add(d.toUpperCase() + " " + String.valueOf(i));
        }
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT e, p FROM Tblpersona p "
                + "JOIN p.tblempleados e "
                + "GROUP BY e.codigoMarcacion "
                + "ORDER BY e.codigoMarcacion";
        empleados = null;
        empleados = (ArrayList) session.createQuery(sql).list();


        Iterator iter = empleados.iterator();
        while (iter.hasNext()) {
            Object[] o = (Object[]) iter.next();
            Tblempleado e = (Tblempleado) o[0];
            Tblpersona p = (Tblpersona) o[1];
            Plan[] tempPlan = new Plan[diasMes];
            for (int i = 0; i < diasMes; i++) {

                //Obtiene el nombre del empleado
                String nombre;
                String pn = FacesUtil.emptyString(p.getPrimerNombre());
                String sn = FacesUtil.emptyString(p.getSegundoNombre());
                String pa = FacesUtil.emptyString(p.getPrimerApellido());
                String sa = FacesUtil.emptyString(p.getSegundoApellido());

                nombre = pn + " " + sn + " " + pa + " " + sa;

                //Obtiene el codigo de marcacion del empleado
                String cm = Short.toString(e.getCodigoMarcacion());

                //Obtiene la hora de entrada y salida para el dia correspondiente a la iteracion actual
                String he = "";
                String hs = "";
                String h = "0";
                String m = "0";
                String hd = "0";
                String md = "0";
                String id = "0";
                Calendar min = Calendar.getInstance();
                min.set(this.selectedAnio, this.selectedMes, i + 1, 0, 0, 0);

                Calendar max = Calendar.getInstance();
                max.set(this.selectedAnio, this.selectedMes, i + 1, 23, 59, 59);

                Criteria cPlan = session.createCriteria(Tblplanificaciontrabajo.class);
                cPlan.add(Restrictions.eq("tblempleado", e));
                cPlan.add(Restrictions.between("fechaEntrada", min.getTime(), max.getTime()));
                Tblplanificaciontrabajo tempFecha = (Tblplanificaciontrabajo) cPlan.uniqueResult();

                if (tempFecha != null) {
                    id = tempFecha.getIdPlanificacionTrabajo().toString();

                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");

                    Calendar entrada = Calendar.getInstance();
                    entrada.setTime(tempFecha.getFechaEntrada());

                    he = sdf.format(entrada.getTime());
                    h = String.valueOf(entrada.get(Calendar.HOUR_OF_DAY));
                    m = String.valueOf(entrada.get(Calendar.MINUTE));

                    Calendar salida = Calendar.getInstance();
                    salida.setTime(tempFecha.getFechaSalida());

                    hs = sdf.format(salida.getTime());

                    hd = Short.toString(tempFecha.getHoraDuracion());
                    md = Short.toString(tempFecha.getMinsDuracion());



                    entrada.set(Calendar.HOUR_OF_DAY, 0);
                    entrada.set(Calendar.MINUTE, 0);
                    entrada.set(Calendar.SECOND, 0);

                    salida.set(Calendar.HOUR_OF_DAY, 0);
                    salida.set(Calendar.MINUTE, 0);
                    salida.set(Calendar.SECOND, 0);
                    if (salida.compareTo(entrada) > 0) {
                        hs = hs + "-DS";
                    }
                }
                tempPlan[i] = new Plan(nombre, cm, he, hs, String.valueOf(i + 1), h, m, hd, md, id);
            }
            this.planificacion.add(tempPlan);
        }
        session.close();
    }

    /**Genera la lista de meses que apareceran en la lista desplegable "Meses"*/
    private void populateListaMeses() {
        String[] months = FacesUtil.getMonths();
        for (int i = 0; i < months.length - 1; i++) {
            listaMeses.add(new SelectItem(i, months[i], ""));
        }
    }

    /**Devuelve la representacion de la fecha de entrada en el formato dd/MM/yyyy*/
    public void populateFechaEntrada() {
        if (this.selectedAnio != null && this.selectedDia != null & this.selectedMes != null) {
            Calendar cal = Calendar.getInstance();
            cal.set(this.selectedAnio, this.selectedMes, this.selectedDia);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
            this.fechaEntrada = dateFormat.format(cal.getTime());
        }
    }

    /**Obtiene la representacion del nombre del mes seleccionado por el usuario en lista desplegable "Meses"*/
    public void cargarMes() {
        this.mes = FacesUtil.getMonth(selectedMes);
    }

    /**Muestra u oculta partes del formulario de planificacion de acuerdo a ciertas condiciones*/
    public void onSelectMes() {
        Boolean exist = false;
        if (this.selectedMes != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria cCarga = session.createCriteria(Tblcargalaboral.class);
            cCarga.add(Restrictions.eq("mes", this.selectedMes));
            cCarga.add(Restrictions.eq("anio", this.selectedAnio));
            Tblcargalaboral carga = (Tblcargalaboral) cCarga.uniqueResult();

            if (carga
                    != null) {
                this.renderMostrarCargaLaboral = true;
                this.renderAsignarCargaLaboral = false;
                this.selectedCarga = carga.getIdCargaLaboral();
                this.horasAsignadas = Short.toString(carga.getHorasAsignadas());
                this.mes = FacesUtil.getMonth(selectedMes);
                exist = true;
                this.renderDtPlan = true;
            } else {
                this.renderMostrarCargaLaboral = false;
                this.renderAsignarCargaLaboral = true;
                this.renderDtPlan = false;
            }

            session.close();
        } else {
            this.renderMostrarCargaLaboral = false;
            this.renderAsignarCargaLaboral = false;
            this.renderDtPlan = false;
        }
        if (exist) {
            this.createDynamicColumns();
        }
    }
    /*
     * Getters & Setters
     */

    public ArrayList<SelectItem> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(ArrayList<SelectItem> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Short getSelectedAnio() {
        return selectedAnio;
    }

    public void setSelectedAnio(Short selectedAnio) {
        this.selectedAnio = selectedAnio;
    }

    public Short getSelectedMes() {
        return selectedMes;
    }

    public void setSelectedMes(Short selectedMes) {
        this.selectedMes = selectedMes;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Boolean getRenderDtPlan() {
        return renderDtPlan;
    }

    public void setRenderDtPlan(Boolean renderDtPlan) {
        this.renderDtPlan = renderDtPlan;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public int getDiasMes() {
        return diasMes;
    }

    public void setDiasMes(int diasMes) {
        this.diasMes = diasMes;
    }

    public Short getSelectedDia() {
        return selectedDia;
    }

    public void setSelectedDia(Short selectedDia) {
        this.selectedDia = selectedDia;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public int getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(int horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public int getHorasTurno() {
        return horasTurno;
    }

    public void setHorasTurno(int horasTurno) {
        this.horasTurno = horasTurno;
    }

    public int getMinEntrada() {
        return minEntrada;
    }

    public void setMinEntrada(int minEntrada) {
        this.minEntrada = minEntrada;
    }

    public int getMinsTurno() {
        return minsTurno;
    }

    public void setMinsTurno(int minsTurno) {
        this.minsTurno = minsTurno;
    }

    public Short getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(Short selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public Tblplanificaciontrabajo getPlan() {
        return plan;
    }

    public void setPlan(Tblplanificaciontrabajo plan) {
        this.plan = plan;
    }

    public Boolean getRenderAsignarCargaLaboral() {
        return renderAsignarCargaLaboral;
    }

    public void setRenderAsignarCargaLaboral(Boolean renderAsignarCargaLaboral) {
        this.renderAsignarCargaLaboral = renderAsignarCargaLaboral;
    }

    public Boolean getRenderMostrarCargaLaboral() {
        return renderMostrarCargaLaboral;
    }

    public void setRenderMostrarCargaLaboral(Boolean renderMostrarCargaLaboral) {
        this.renderMostrarCargaLaboral = renderMostrarCargaLaboral;
    }

    public String getHorasAsignadas() {
        return horasAsignadas;
    }

    public void setHorasAsignadas(String horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }

    public Long getSelectedCarga() {
        return selectedCarga;
    }

    public void setSelectedCarga(Long selectedCarga) {
        this.selectedCarga = selectedCarga;
    }

    public ArrayList getEmpleados() {
        return empleados;
    }

    public void setEmpleados(ArrayList empleados) {
        this.empleados = empleados;
    }

    public List<Plan[]> getPlanificacion() {
        return planificacion;
    }

    public void setPlanificacion(List<Plan[]> planificacion) {
        this.planificacion = planificacion;
    }

    public Long getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(Long selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tblcargalaboral getCargaLaboral() {
        return cargaLaboral;
    }

    public void setCargaLaboral(Tblcargalaboral cargaLaboral) {
        this.cargaLaboral = cargaLaboral;
    }

    public class Plan implements Serializable {

        private String nombre;
        private String codigoMarcacion;
        private String horaEntrada;
        private String horaSalida;
        private String dia;
        private String h;
        private String m;
        private String hd;
        private String md;
        private String idPlan;

        public Plan(String n, String cm, String he, String hs, String d, String h, String m, String hd, String md, String id) {
            this.nombre = n;
            this.codigoMarcacion = cm;
            this.horaEntrada = he;
            this.horaSalida = hs;
            this.dia = d;
            this.h = h;
            this.m = m;
            this.hd = hd;
            this.md = md;
            this.idPlan = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getCodigoMarcacion() {
            return codigoMarcacion;
        }

        public void setCodigoMarcacion(String codigoMarcacion) {
            this.codigoMarcacion = codigoMarcacion;
        }

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

        public String getHoraEntrada() {
            return horaEntrada;
        }

        public void setHoraEntrada(String horaEntrada) {
            this.horaEntrada = horaEntrada;
        }

        public String getHoraSalida() {
            return horaSalida;
        }

        public void setHoraSalida(String horaSalida) {
            this.horaSalida = horaSalida;
        }

        public String getH() {
            return h;
        }

        public void setH(String h) {
            this.h = h;
        }

        public String getHd() {
            return hd;
        }

        public void setHd(String hd) {
            this.hd = hd;
        }

        public String getM() {
            return m;
        }

        public void setM(String m) {
            this.m = m;
        }

        public String getMd() {
            return md;
        }

        public void setMd(String md) {
            this.md = md;
        }

        public String getIdPlan() {
            return idPlan;
        }

        public void setIdPlan(String idPlan) {
            this.idPlan = idPlan;
        }
    }
}
