/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblcriterio;
import com.silac.model.TblcuestionarioEvaluacion;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "cuestionarioBean")
@ViewScoped
public class CuestionarioBean implements Serializable {

    private TblcuestionarioEvaluacion cuestionario;
    private ArrayList<Cuestionario> listaCuestionario;
    private Cuestionario selectedCuestionario;
    private Tblcriterio selectedCriterio;
    private boolean cargarPregunta;
    private boolean cargarCriterio;

    /** Creates a new instance of CuestionarioBean */
    public CuestionarioBean() {
        this.init();
    }

    private void init() {
        this.cuestionario = new TblcuestionarioEvaluacion();
        this.cargarListaCuestionario();
        this.cargarPregunta = false;
        this.cargarCriterio = false;
        this.selectedCuestionario = new Cuestionario();
        this.selectedCuestionario.setCriterio(new Tblcriterio());
    }
    
    public void nuevo(){
        this.init();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            if (this.cargarPregunta) {
                session.saveOrUpdate(this.cuestionario);
            } else {
                this.cuestionario.setNumeroPregunta(this.generarCorrelativo(session));
                session.save(this.cuestionario);
            }
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "La pregunta se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void agregarCriterio() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tblcriterio criterio = this.selectedCuestionario.getCriterio();
            if (this.cargarCriterio) {
                session.saveOrUpdate(criterio);
            } else {
                TblcuestionarioEvaluacion tempCuestionario = (TblcuestionarioEvaluacion) session.load(TblcuestionarioEvaluacion.class, this.selectedCuestionario.getPregunta().getIdCuestionario());
                criterio.setTblcuestionarioEvaluacion(tempCuestionario);
                session.save(criterio);
            }
            tx.commit();
            this.init();
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void modificarPregunta() {
        this.cuestionario = new TblcuestionarioEvaluacion();
        this.cuestionario = this.selectedCuestionario.getPregunta();
        this.cargarPregunta = true;
    }

    public void modificarCriterio() {
        this.cargarCriterio = true;
        this.selectedCuestionario.setCriterio(this.selectedCriterio);
    }

    /** Elimina el registro seleccionado por el usuario.*/
    public void eliminar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        TblcuestionarioEvaluacion pregunta = (TblcuestionarioEvaluacion) session.load(TblcuestionarioEvaluacion.class, this.selectedCuestionario.getPregunta().getIdCuestionario());
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(pregunta);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public void eliminarCriterio() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        Tblcriterio criterio = (Tblcriterio) session.load(Tblcriterio.class, this.selectedCriterio.getIdCriterio());
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(criterio);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public int generarCorrelativo(Session session) {
        int numero = 1;
        String sql = "SELECT MAX(c.numeroPregunta) FROM Tblcuestionario_evaluacion c";
        Query query = session.createSQLQuery(sql);
        if (query.uniqueResult() != null) {
            numero =  ((Integer) query.uniqueResult()).intValue() + 1;
        }
        return numero;
    }

    public void subir() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        TblcuestionarioEvaluacion pregunta = this.selectedCuestionario.getPregunta();
        int numeroPregunta = this.selectedCuestionario.getPregunta().getNumeroPregunta();
        int max = numeroPregunta;
        String sql = "SELECT MAX(c.numeroPregunta) FROM Tblcuestionario_evaluacion c";
        Query query = session.createSQLQuery(sql);
        if (query.uniqueResult() != null) {
            max = ((Integer) query.uniqueResult()).intValue();
        }
        if (numeroPregunta < max) {
            Transaction tx = null;
            try {
                tx = (Transaction) session.beginTransaction();
                pregunta.setNumeroPregunta(numeroPregunta + 1);
                session.saveOrUpdate(pregunta);
                sql = "UPDATE Tblcuestionario_evaluacion c "
                        + "SET c.numeroPregunta = c.numeroPregunta - 1 "
                        + "WHERE c.numeroPregunta = :numero";
                session.createSQLQuery(sql).setParameter("numero", numeroPregunta + 1).executeUpdate();
                tx.commit();
                this.init();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Informacion:", "La pregunta se movio a la siguiente posicion"));
            } catch (HibernateException e) {
                tx.rollback();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Un error ha ocurrido:", e.getMessage()));
            } finally {
                this.init();
            }
        }
        session.close();
    }

    public void bajar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        TblcuestionarioEvaluacion pregunta = this.selectedCuestionario.getPregunta();
        int numeroPregunta = this.selectedCuestionario.getPregunta().getNumeroPregunta();
        int min = numeroPregunta;
        String sql = "SELECT MIN(c.numeroPregunta) FROM Tblcuestionario_evaluacion c";
        Query query = session.createSQLQuery(sql);
        if (query.uniqueResult() != null) {
            min = ((Integer) query.uniqueResult()).intValue();
        }
        if (numeroPregunta > min) {
            Transaction tx = null;
            try {
                tx = (Transaction) session.beginTransaction();
                pregunta.setNumeroPregunta(numeroPregunta - 1);
                session.saveOrUpdate(pregunta);
                sql = "UPDATE Tblcuestionario_evaluacion c "
                        + "SET c.numeroPregunta = c.numeroPregunta + 1 "
                        + "WHERE c.numeroPregunta = :numero";
                session.createSQLQuery(sql).setParameter("numero", numeroPregunta - 1).executeUpdate();
                tx.commit();
                this.init();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Informacion:", "La pregunta se movio a la posicion anterior"));
            } catch (HibernateException e) {
                tx.rollback();
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Un error ha ocurrido:", e.getMessage()));
            } finally {
                this.init();
            }
        }
        session.close();
    }
    

    public void cargarListaCuestionario() {
        this.listaCuestionario = new ArrayList<Cuestionario>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cCuestionario = session.createCriteria(TblcuestionarioEvaluacion.class);
        cCuestionario.addOrder(Order.asc("numeroPregunta"));
        Iterator iter = cCuestionario.list().iterator();
        while (iter.hasNext()) {
            TblcuestionarioEvaluacion ce = (TblcuestionarioEvaluacion) iter.next();
            ArrayList<Tblcriterio> criterios = new ArrayList<Tblcriterio>();
            if (ce.getTblcriterios() != null) {
                Set<Tblcriterio> cr = (Set<Tblcriterio>) ce.getTblcriterios();
                Iterator itCriterio = cr.iterator();
                while (itCriterio.hasNext()) {
                    Tblcriterio tempCriterio = (Tblcriterio) itCriterio.next();
                    criterios.add(tempCriterio);
                }
            }
            this.listaCuestionario.add(new Cuestionario(ce, criterios, new Tblcriterio()));
        }
        session.close();
    }

    /*
     * Getters & Setters
     */
    public TblcuestionarioEvaluacion getCuestionario() {
        return cuestionario;
    }

    public void setCuestionario(TblcuestionarioEvaluacion cuestionario) {
        this.cuestionario = cuestionario;
    }

    public ArrayList<Cuestionario> getListaCuestionario() {
        return listaCuestionario;
    }

    public void setListaCuestionario(ArrayList<Cuestionario> listaCuestionario) {
        this.listaCuestionario = listaCuestionario;
    }

    public Cuestionario getSelectedCuestionario() {
        return selectedCuestionario;
    }

    public void setSelectedCuestionario(Cuestionario selectedCuestionario) {
        this.selectedCuestionario = selectedCuestionario;
    }

    public Tblcriterio getSelectedCriterio() {
        return selectedCriterio;
    }

    public void setSelectedCriterio(Tblcriterio selectedCriterio) {
        this.selectedCriterio = selectedCriterio;
    }

    public boolean isCargarPregunta() {
        return cargarPregunta;
    }

    public void setCargarPregunta(boolean cargarPregunta) {
        this.cargarPregunta = cargarPregunta;
    }

    public class Cuestionario implements Serializable {

        private TblcuestionarioEvaluacion pregunta;
        private ArrayList<Tblcriterio> listaCriterios;
        private Tblcriterio criterio;

        public Cuestionario() {
        }

        public Cuestionario(TblcuestionarioEvaluacion pregunta, ArrayList<Tblcriterio> listaCriterios, Tblcriterio criterio) {
            this.pregunta = pregunta;
            this.listaCriterios = listaCriterios;
            this.criterio = criterio;
        }

        public Tblcriterio getCriterio() {
            return criterio;
        }

        public void setCriterio(Tblcriterio criterio) {
            this.criterio = criterio;
        }

        public ArrayList<Tblcriterio> getListaCriterios() {
            return listaCriterios;
        }

        public void setListaCriterios(ArrayList<Tblcriterio> listaCriterios) {
            this.listaCriterios = listaCriterios;
        }

        public TblcuestionarioEvaluacion getPregunta() {
            return pregunta;
        }

        public void setPregunta(TblcuestionarioEvaluacion pregunta) {
            this.pregunta = pregunta;
        }
    }
}
