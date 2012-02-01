package com.silac.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import com.silac.model.Tblinstitucion;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
import org.hibernate.criterion.Order;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "institucionesBean")
@ViewScoped
public class InstitucionesBean implements Serializable {

    private Tblinstitucion inst = new Tblinstitucion();
    private List<Tblinstitucion> insts;
    private Tblinstitucion selectedInst;

    /** Creates a new instance of institucionesBean */
    public InstitucionesBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.inst = new Tblinstitucion();
        this.insts = null;
        this.selectedInst = null;
        this.populateInstituciones();
    }

    public String crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(inst);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La institución se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
        return null;
    }

    public void nuevo() {
        this.init();
    }

    /**Elimina el registro seleccionado por el usuario.*/
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idInst");
        Tblinstitucion i = (Tblinstitucion) session.load(Tblinstitucion.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(i);
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

    public void populateInstituciones() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT i from Tblinstitucion i ORDER BY i.idInstitucion";
        insts = null;
        Query query = session.createQuery(sql);
        insts = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    public ArrayList<SelectItem> listaInstituciones() {
        ArrayList<SelectItem> listaInstituciones = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        //Obtiene la lista de instituciones
        Criteria cInst = session.createCriteria(Tblinstitucion.class);
        cInst.addOrder(Order.asc("idInstitucion"));
        Iterator itInst = cInst.list().iterator();


        while (itInst.hasNext()) {
            Tblinstitucion i = (Tblinstitucion) itInst.next();
            listaInstituciones.add(new SelectItem(i.getIdInstitucion(), i.getNombre(), ""));
        }

        session.close();

        return listaInstituciones;
    }

    /**
     * Getters & Setters
     */
    public Tblinstitucion getInst() {
        return inst;
    }

    public void setInst(Tblinstitucion inst) {
        this.inst = inst;
    }

    public List<Tblinstitucion> getInsts() {
        return insts;
    }

    public void setInsts(List<Tblinstitucion> insts) {
        this.insts = insts;
    }

    public Tblinstitucion getSelectedInst() {
        return selectedInst;
    }

    public void setSelectedInst(Tblinstitucion selectedInst) {
        this.selectedInst = selectedInst;
    }
}
