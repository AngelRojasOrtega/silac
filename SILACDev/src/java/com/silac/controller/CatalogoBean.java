/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblcatalogo;
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
@ManagedBean(name = "catalogoBean")
@ViewScoped
public class CatalogoBean implements Serializable {

    private List<Tblcatalogo> cats;
    private Tblcatalogo cat = new Tblcatalogo();
    private Tblcatalogo selectedCat;

    /** Crear nueva instancia CatalogoBean*/
    public CatalogoBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.cats = null;
        this.cat = new Tblcatalogo();
        this.selectedCat = null;
        populateCatalogo();
    }

    /**Crea una nueva categoría*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(cat);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "La categoría se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            cats = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }
    
    public void nuevo(){
        this.init();
    }

    /**Elimina una categoría*/
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idCat");
        Tblcatalogo c = (Tblcatalogo) session.load(Tblcatalogo.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(c);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    /**
     * Carga la lista de categorias que seran mostradas en un cuadro de lista desplegable
     * @return listaCategorias Lista de categorias
     */
    public ArrayList<SelectItem> cargarCategorias() {
        ArrayList<SelectItem> listaCategorias = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cCategorias = session.createCriteria(Tblcatalogo.class);
        Iterator iter = cCategorias.list().iterator();
        while (iter.hasNext()) {
            Tblcatalogo c = (Tblcatalogo) iter.next();
            listaCategorias.add(new SelectItem(c.getIdCatalogo(), c.getCategoria(), ""));
        }
        session.close();
        return listaCategorias;
    }

    /**Muestra la categoria ingresada al catalogo*/
    private void populateCatalogo() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT c from Tblcatalogo c ORDER BY c.idCatalogo";
        cats = null;
        Query query = session.createQuery(sql);
        cats = (ArrayList) session.createQuery(sql).list();
        session.close();

    }

    /**
     * Getters & Setters
     */
    public Tblcatalogo getCat() {
        return cat;
    }

    public void setCat(Tblcatalogo cat) {
        this.cat = cat;
    }

    public List<Tblcatalogo> getCats() {
        return cats;
    }

    public void setCats(List<Tblcatalogo> cats) {
        this.cats = cats;
    }

    public Tblcatalogo getSelectedCat() {
        return selectedCat;
    }

    public void setSelectedCat(Tblcatalogo selectedCat) {
        this.selectedCat = selectedCat;
    }
}
