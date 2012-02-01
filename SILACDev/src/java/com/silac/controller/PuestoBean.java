package com.silac.controller;

import com.silac.model.Tblpuesto;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "puestoBean")
@ViewScoped
public class PuestoBean implements Serializable {

    private Tblpuesto puesto = new Tblpuesto();
    private Tblpuesto selectedPuesto;
    private List<Tblpuesto> puestos;

    /** Creates a new instance of PuestoBean */
    public PuestoBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.puesto = new Tblpuesto();
        this.selectedPuesto = null;
        this.puestos = null;
        populatePuestos();
    }

    public void crear() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(puesto);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El puesto se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            puestos = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevo() {
        this.init();
    }

    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idPuesto");
        Tblpuesto p = (Tblpuesto) session.load(Tblpuesto.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(p);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public ArrayList<SelectItem> cargarListaPuestos() {
        ArrayList<SelectItem> listaPuestos = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cDeptos = session.createCriteria(Tblpuesto.class);
        Iterator iter = cDeptos.list().iterator();
        while (iter.hasNext()) {
            Tblpuesto p = (Tblpuesto) iter.next();
            listaPuestos.add(new SelectItem(p.getIdPuesto(), p.getNombrePuesto(), ""));
        }
        session.close();
        return listaPuestos;
    }

    private void populatePuestos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT p from Tblpuesto p ORDER BY p.idPuesto";
        puestos = null;
        Query query = session.createQuery(sql);
        puestos = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    /**
     * Getters & Setters
     */
    public Tblpuesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Tblpuesto puesto) {
        this.puesto = puesto;
    }

    public List<Tblpuesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<Tblpuesto> puestos) {
        this.puestos = puestos;
    }

    public Tblpuesto getSelectedPuesto() {
        return selectedPuesto;
    }

    public void setSelectedPuesto(Tblpuesto selectedPuesto) {
        this.selectedPuesto = selectedPuesto;
    }
}
