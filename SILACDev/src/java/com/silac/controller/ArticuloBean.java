/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller; 

import com.silac.model.Tblarticulo;
import com.silac.model.Tblcatalogo;
import com.silac.model.Tblunidadmedida;
import com.silac.util.HibernateUtil;
import com.silac.util.FacesUtil;
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
import org.hibernate.criterion.Order;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "articuloBean")
@ViewScoped
public class ArticuloBean implements Serializable {

    private String selectedCategoria;
    private Short selectedUnidadMedidaUso;
    private Short selectedUnidadMedidaCompra;
    private ArrayList listaUnidades = new ArrayList();
    private Articulo selectedArticulo;
    private ArrayList<Articulo> articulos;
    private Tblarticulo articulo = new Tblarticulo();

    /** Crea nueva instancia de ArticuloBean */
    public ArticuloBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.selectedCategoria = null;
        this.selectedUnidadMedidaUso = null;
        this.selectedUnidadMedidaCompra = null;
        this.listaUnidades = new ArrayList();
        this.selectedArticulo = null;
        this.articulos = new ArrayList<Articulo>();
        this.articulo = new Tblarticulo();
        populateArticulo();
    }

    /**Crea un nuevo artículo*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tblcatalogo c = (Tblcatalogo) session.load(Tblcatalogo.class, Short.parseShort(selectedCategoria));
            this.articulo.setTblcatalogo(c);
            Tblunidadmedida unidadUso = (Tblunidadmedida) session.load(Tblunidadmedida.class, this.selectedUnidadMedidaUso);
            Tblunidadmedida unidadCompra = (Tblunidadmedida) session.load(Tblunidadmedida.class, this.selectedUnidadMedidaCompra);
            this.articulo.setTblunidadmedidaByUnidadMinimoUso(unidadUso);
            this.articulo.setTblunidadmedidaByUnidadMinimoCompra(unidadCompra);
            articulo.setExistencias(0.0);
            session.save(articulo);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "El articulo se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevo() {
        this.init();
    }

    /**Elimina un artículo*/
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(this.selectedArticulo.getArticulo());
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

    /**
     * Carga la lista de artículos que seran mostrados en un cuadro de lista desplegable
     * @param idCategoria Representa el Id de la categoria seleccionada por el usario, 
     * el cual servira para filtrar la lista de articulos
     * @return listaArticulos Lista de articulso filtrados por categoria
     */
    public ArrayList<SelectItem> cargarListaArticulo(Short idCategoria) {
        ArrayList<SelectItem> listaArticulos = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (idCategoria != null) {
            Tblcatalogo catalogo = (Tblcatalogo) session.load(Tblcatalogo.class, idCategoria);
            Iterator iter = catalogo.getTblarticulos().iterator();
            while (iter.hasNext()) {
                Tblarticulo a = (Tblarticulo) iter.next();
                listaArticulos.add(new SelectItem(a.getIdArticulo(), a.getNombreArticulo(), ""));
            }
        }
        session.close();
        return listaArticulos;
    }

    /**Muestra la lista de artículos ingresados*/
    private void populateArticulo() {
        this.articulos = new ArrayList<Articulo>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cArticulo = session.createCriteria(Tblarticulo.class);
        cArticulo.addOrder(Order.asc("tblcatalogo"));
        Iterator iterArticulo = cArticulo.list().iterator();
        while (iterArticulo.hasNext()) {
            Tblarticulo a = (Tblarticulo) iterArticulo.next();
            this.articulos.add(new Articulo(a.getTblunidadmedidaByUnidadMinimoUso().getAbreviatura(), 
                    a.getTblunidadmedidaByUnidadMinimoCompra().getAbreviatura(), 
                    a.getTblcatalogo().getCategoria(), a));
        }
        session.close();
    }

    /**
     *
     * geters y seters
     */
    public Tblarticulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Tblarticulo articulo) {
        this.articulo = articulo;
    }

    public ArrayList<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(ArrayList<Articulo> articulos) {
        this.articulos = articulos;
    }

    public ArrayList getListaUnidades() {
        return listaUnidades;
    }

    public void setListaUnidades(ArrayList listaUnidades) {
        this.listaUnidades = listaUnidades;
    }

    public Articulo getSelectedArticulo() {
        return selectedArticulo;
    }

    public void setSelectedArticulo(Articulo selectedArticulo) {
        this.selectedArticulo = selectedArticulo;
    }

    public String getSelectedCategoria() {
        return selectedCategoria;
    }

    public void setSelectedCategoria(String selectedCategoria) {
        this.selectedCategoria = selectedCategoria;
    }

    public Short getSelectedUnidadMedidaCompra() {
        return selectedUnidadMedidaCompra;
    }

    public void setSelectedUnidadMedidaCompra(Short selectedUnidadMedidaCompra) {
        this.selectedUnidadMedidaCompra = selectedUnidadMedidaCompra;
    }

    public Short getSelectedUnidadMedidaUso() {
        return selectedUnidadMedidaUso;
    }

    public void setSelectedUnidadMedidaUso(Short selectedUnidadMedidaUso) {
        this.selectedUnidadMedidaUso = selectedUnidadMedidaUso;
    }

    public class Articulo implements Serializable {

        private String unidadUso;
        private String unidadCompra;
        private String Categoria;
        private Tblarticulo articulo;

        public Articulo(String unidadUso, String unidadCompra, String Categoria, Tblarticulo articulo) {
            this.unidadUso = unidadUso;
            this.unidadCompra = unidadCompra;
            this.Categoria = Categoria;
            this.articulo = articulo;
        }

        public Tblarticulo getArticulo() {
            return articulo;
        }

        public void setArticulo(Tblarticulo articulo) {
            this.articulo = articulo;
        }

        public String getUnidadCompra() {
            return unidadCompra;
        }

        public void setUnidadCompra(String unidadCompra) {
            this.unidadCompra = unidadCompra;
        }

        public String getUnidadUso() {
            return unidadUso;
        }

        public void setUnidadUso(String unidadUso) {
            this.unidadUso = unidadUso;
        }

        public String getCategoria() {
            return Categoria;
        }

        public void setCategoria(String Categoria) {
            this.Categoria = Categoria;
        }
    }
}