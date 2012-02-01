/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblarticulo;
import com.silac.model.Tblcatalogo;
import com.silac.model.Tblentrada;
import com.silac.model.Tblmovimiento;
import com.silac.model.Tblprocedenciaarticulo;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "entradaBean")
@ViewScoped
public class EntradaBean implements Serializable {

    private Boolean renderVencimiento = false;
    private Date hoy = new Date();
    private Date minDate = new Date();
    private Integer selectedArticulo;
    private Short selectedCategoria;
    private String selectedProcedencia;
    private ArrayList listaArticulos = new ArrayList();
    private ArrayList listaProcedencias = new ArrayList();
    private List entradas;
    private List movimientos;
    private Object selectedEntrada;
    private Object selectedMovimiento;
    private Tblentrada entrada = new Tblentrada();
    private Tblmovimiento movimiento = new Tblmovimiento();
    private ArticuloBean articuloBean;


    /** Crea nueva instancia de EntradaBean */
    public EntradaBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.renderVencimiento = false;
        this.hoy = new Date();
        this.minDate = new Date();
        this.selectedArticulo = null;
        this.selectedProcedencia = null;
        this.listaProcedencias = new ArrayList();
        this.entradas = null;
        this.movimientos = null;
        this.selectedEntrada = null;
        this.selectedMovimiento = null;
        this.entrada = new Tblentrada();
        this.movimiento = new Tblmovimiento();
        this.articuloBean = (ArticuloBean) FacesUtil.getBean("articuloBean");
        this.selectedCategoria = null;
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        cargarProcedencias();
        populateEntrada();
    }

    /**Crea una nueva entrada*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            //actualiza las existencias totales en la tabla articulo
            Tblarticulo a = (Tblarticulo) session.load(Tblarticulo.class, selectedArticulo);
            a.setExistencias(a.getExistencias() + entrada.getCantidad());
            session.update(a);
            //guarda en la tabla moviemiento
            movimiento.setTblarticulo(a);
            movimiento.setFecha(new Date());
            movimiento.setTipoMovimiento(true);
            session.save(movimiento);
            //guarda en la tabla entrada
            Tblprocedenciaarticulo p = (Tblprocedenciaarticulo) session.load(Tblprocedenciaarticulo.class, Short.parseShort(selectedProcedencia));
            entrada.setTblprocedenciaarticulo(p);
            entrada.setTblmovimiento(movimiento);
            entrada.setExistencia(entrada.getCantidad());
            session.save(entrada);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La entrada se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            entradas = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevo() {
        this.init();
    }

    /**Actualiza el valor minimo para la fecha de vencimiento máximo*/
    public void handleDateSelect(DateSelectEvent event) {
        this.minDate = event.getDate();
    }

    /**Establece si la fecha de vencimiento sera requerida o no*/
    public void actualizarVencimiento() {
        renderVencimiento = false;
        if (selectedArticulo != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria cArticulo = session.createCriteria(Tblarticulo.class);
            cArticulo.add(Restrictions.eq("idArticulo", selectedArticulo));
            Tblarticulo temArticulo = (Tblarticulo) cArticulo.uniqueResult();
            if (temArticulo.isPerecedero()) {
                renderVencimiento = true;
            }
            session.close();
        }
    }

    /**Muestra la lista de entradas hechas*/
    private void populateEntrada() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT c.categoria, a.nombreArticulo, m.concepto, e from Tblentrada e "
                + "join e.tblmovimiento m "
                + "join m.tblarticulo a "
                + "join a.tblcatalogo c  GROUP BY e.idEntrada ORDER BY e.idEntrada";
        entradas = null;
        Query query = session.createQuery(sql);
        entradas = (ArrayList) session.createQuery(sql).list();
        session.close();
    }

    /**Actualiza la lista desplegable de articulos*/
    public void renderCboArticulo() {
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        this.renderVencimiento = false;
    }

    /**Carga la lista desplegable de procedencias*/
    private void cargarProcedencias() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //Obtiene la lista de procedencias
        String sql = "SELECT p from Tblprocedenciaarticulo p ORDER BY p.lugarProcedencia";
        Query query = session.createQuery(sql);
        List procedencias = (ArrayList) session.createQuery(sql).list();
        Iterator itProcedencia = procedencias.iterator();
        while (itProcedencia.hasNext()) {
            Tblprocedenciaarticulo p = (Tblprocedenciaarticulo) itProcedencia.next();
            listaProcedencias.add(new SelectItem(p.getIdProcedencia().toString(), p.getLugarProcedencia(), ""));
        }
        session.close();
    }

    /**
     * Getters & Setters
     */
    public Tblentrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Tblentrada entrada) {
        this.entrada = entrada;
    }

    public List getEntradas() {
        return entradas;
    }

    public void setEntradas(List entradas) {
        this.entradas = entradas;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public ArrayList getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(ArrayList listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public ArrayList getListaProcedencias() {
        return listaProcedencias;
    }

    public void setListaProcedencias(ArrayList listaProcedencias) {
        this.listaProcedencias = listaProcedencias;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Tblmovimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Tblmovimiento movimiento) {
        this.movimiento = movimiento;
    }

    public List getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List movimientos) {
        this.movimientos = movimientos;
    }

    public Boolean getRenderVencimiento() {
        return renderVencimiento;
    }

    public void setRenderVencimiento(Boolean requiredFecVencimiento) {
        this.renderVencimiento = requiredFecVencimiento;
    }

    public Integer getSelectedArticulo() {
        return selectedArticulo;
    }

    public void setSelectedArticulo(Integer selectedArticulo) {
        this.selectedArticulo = selectedArticulo;
    }

    public Short getSelectedCategoria() {
        return selectedCategoria;
    }

    public void setSelectedCategoria(Short selectedCategoria) {
        this.selectedCategoria = selectedCategoria;
    }

    public Object getSelectedEntrada() {
        return selectedEntrada;
    }

    public void setSelectedEntrada(Object selectedEntrada) {
        this.selectedEntrada = selectedEntrada;
    }

    public Object getSelectedMovimiento() {
        return selectedMovimiento;
    }

    public void setSelectedMovimiento(Object selectedMovimiento) {
        this.selectedMovimiento = selectedMovimiento;
    }

    public String getSelectedProcedencia() {
        return selectedProcedencia;
    }

    public void setSelectedProcedencia(String selectedProcedencia) {
        this.selectedProcedencia = selectedProcedencia;
    }
   
}
