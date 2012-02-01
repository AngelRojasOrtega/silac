/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblarticulo;
import com.silac.model.Tblexamen;
import com.silac.model.Tblexamenarticulo;
import com.silac.model.Tblparametro;
import com.silac.model.Tblseccion;
import com.silac.model.Tblunidadmedida;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "examenesBean")
@ViewScoped
public class ExamenesBean implements Serializable {

    private Tblexamen examen;
    private Tblexamenarticulo articuloAsignado;
    private Tblparametro parametroAsignado;
    private ArrayList<ExamenArticulo> listaArticulosAsignados;
    private ArrayList<Parametro> listaParametrosAsignados;
    private ArrayList<SelectItem> listaSecciones;
    private ArrayList<SelectItem> listaArticulos;
    private ArrayList<Examen> listaExamenes;
    private Short selectedSeccion;
    private Integer selectedArticulo;
    private Short selectedCategoria;
    private Short selectedUnidadMedida;
    private ArticuloBean articuloBean;
    private boolean renderValoresParametro;
    private ExamenArticulo selectedArticuloAsignado;
    private Parametro selectedParametroAsignado;
    private Examen selectedExamen;

    /** Creates a new instance of ExamenesBean */
    public ExamenesBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.examen = new Tblexamen();
        this.articuloAsignado = new Tblexamenarticulo();
        this.articuloAsignado.setCantidad(0.0);
        this.parametroAsignado = new Tblparametro();
        this.listaArticulosAsignados = new ArrayList<ExamenArticulo>();
        this.listaParametrosAsignados = new ArrayList<Parametro>();
        this.listaSecciones = new ArrayList<SelectItem>();
        this.listaExamenes = new ArrayList<Examen>();
        this.selectedCategoria = null;
        this.selectedUnidadMedida = null;
        this.selectedArticulo = null;
        this.selectedSeccion = null;
        this.articuloBean = (ArticuloBean) FacesUtil.getBean("articuloBean");
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        this.renderValoresParametro = false;
        this.selectedArticuloAsignado = new ExamenArticulo();
        this.selectedParametroAsignado = new Parametro();
        this.selectedExamen = new Examen();
        this.populateListaExamenes();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tblseccion s = (Tblseccion) session.load(Tblseccion.class, this.selectedSeccion);
            this.examen.setTblseccion(s);
            session.save(this.examen);


            Iterator iterArticulos = this.listaArticulosAsignados.iterator();
            while (iterArticulos.hasNext()) {
                ExamenArticulo ea = (ExamenArticulo) iterArticulos.next();
                Tblexamenarticulo tempEA = ea.getExamenarticulo();
                Tblarticulo a = (Tblarticulo) session.load(Tblarticulo.class, ea.getCodigoArticulo());
                tempEA.setTblarticulo(a);
                tempEA.setTblexamen(this.examen);
                session.save(tempEA);
            }

            Iterator iterParametros = this.listaParametrosAsignados.iterator();
            while (iterParametros.hasNext()) {
                Parametro p = (Parametro) iterParametros.next();
                Tblparametro tempParam = p.getParametro();
                if (p.getIdUnidadMedida() != null) {
                    Tblunidadmedida m = (Tblunidadmedida) session.load(Tblunidadmedida.class, p.getIdUnidadMedida());
                    tempParam.setTblunidadmedida(m);
                }
                tempParam.setTblexamen(examen);
                session.save(tempParam);
            }
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "El examen se registro satisfactoriamente"));
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

    public void eliminar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(this.selectedExamen.getExamen());
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:",
                    "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:",
                    e.getCause().getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevoArticulo() {
        this.articuloAsignado = new Tblexamenarticulo();
        this.selectedCategoria = null;
        this.selectedArticulo = null;
        this.articuloAsignado.setCantidad(0.0);
    }

    public void nuevoParametro() {
        this.parametroAsignado = new Tblparametro();
        this.selectedUnidadMedida = null;
        this.parametroAsignado.setValorMaximo(0.0);
        this.parametroAsignado.setValorMinimo(0.0);
        this.parametroAsignado.setValorNormal(0.0);
        this.parametroAsignado.setFijo(false);
        this.renderValoresParametro = false;
    }

    public void asignarArticulo() {
        boolean exist = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tblarticulo articulo = (Tblarticulo) session.load(Tblarticulo.class, this.selectedArticulo);
        Iterator iterArticulo = this.listaArticulosAsignados.iterator();
        while (iterArticulo.hasNext()) {
            ExamenArticulo a = (ExamenArticulo) iterArticulo.next();
            if (a.getCodigoArticulo().intValue() == this.selectedArticulo.intValue()) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            this.articuloAsignado.setTblarticulo(articulo);
            this.listaArticulosAsignados.add(new ExamenArticulo(articulo.getIdArticulo(), articulo.getNombreArticulo(), this.articuloAsignado));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El reactivo o insumo ya fue asignado, seleccione otro", ""));
        }
        session.close();
    }

    public void asignarParametro() {
        String unidadMedida = "";
        Short idUnidadMedida = null;
        boolean exist = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (this.parametroAsignado.getTipoParametro().equals("Valor")) {
            Tblunidadmedida medida = (Tblunidadmedida) session.load(Tblunidadmedida.class, this.selectedUnidadMedida);
            this.parametroAsignado.setTblunidadmedida(medida);
            unidadMedida = medida.getAbreviatura();
            idUnidadMedida = medida.getIdUnidadMedida();
        }
        Iterator iterParametro = this.listaParametrosAsignados.iterator();
        while (iterParametro.hasNext()) {
            Parametro p = (Parametro) iterParametro.next();
            if (p.getParametro().getNombreParametro().equals(this.parametroAsignado.getNombreParametro())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            this.listaParametrosAsignados.add(new Parametro(this.parametroAsignado, idUnidadMedida, unidadMedida));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "El parametro ya fue asignado", ""));
        }
        session.close();
    }

    public void removerArticulo() {
        this.listaArticulosAsignados.remove(this.selectedArticuloAsignado);
    }

    public void removerParametro() {
        this.listaParametrosAsignados.remove(this.selectedParametroAsignado);
    }

    public void populateListaExamenes() {
        this.listaExamenes = new ArrayList<Examen>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cExamen = session.createCriteria(Tblexamen.class);
        cExamen.addOrder(Order.asc("codigoExamen"));
        Iterator iterExamen = cExamen.list().iterator();
        while (iterExamen.hasNext()) {
            Tblexamen e = (Tblexamen) iterExamen.next();
            this.listaExamenes.add(new Examen(e.getTblseccion().getIdSeccionLaboratorio(), e.getTblseccion().getNombreSeccion(), e));
        }
        session.close();
    }

    /**
     * Carga la lista de examenes que seran mostrados en un cuadro de lista desplegable
     * @param idSeccion Representa el Id de la seccion seleccionada por el usario, 
     * el cual servira para filtrar la lista de examenes
     * @return listaExamenes Lista de examenes
     */
    public ArrayList<SelectItem> cargarListaExamenes(Short idSeccion) {
        ArrayList<SelectItem> listaExamen = new ArrayList<SelectItem>();
        if (idSeccion != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Criteria cExamen = session.createCriteria(Tblexamen.class);
            Tblseccion s = (Tblseccion) session.load(Tblseccion.class, idSeccion);
            cExamen.add(Restrictions.eq("tblseccion", s));
            cExamen.addOrder(Order.asc("nombreExamen"));
            Iterator iterExamenes = cExamen.list().iterator();
            while (iterExamenes.hasNext()) {
                Tblexamen e = (Tblexamen) iterExamenes.next();
                listaExamen.add(new SelectItem(e.getIdExamen(), e.getCodigoExamen() + " " + e.getNombreExamen(), ""));
            }
            session.close();
        }
        return listaExamen;
    }

    /**Actualiza la lista desplegable de articulos*/
    public void renderCboArticulo() {
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
    }

    public String booleanString(boolean b) {
        return FacesUtil.booleanString(b);
    }

    public void onSelectTipoParametro() {
        this.renderValoresParametro = false;
        if (this.parametroAsignado.getTipoParametro() != null) {
            if (this.parametroAsignado.getTipoParametro().equals("Valor")) {
                this.renderValoresParametro = true;
            }
        }
    }

    /**
     * Getters & Setters
     */
    public ArrayList<SelectItem> getListaSecciones() {
        return listaSecciones;
    }

    public void setListaSecciones(ArrayList<SelectItem> listaSecciones) {
        this.listaSecciones = listaSecciones;
    }

    public Tblexamen getExamen() {
        return examen;
    }

    public void setExamen(Tblexamen examen) {
        this.examen = examen;
    }

    public Short getSelectedSeccion() {
        return selectedSeccion;
    }

    public void setSelectedSeccion(Short selectedSeccion) {
        this.selectedSeccion = selectedSeccion;
    }

    public ArrayList<SelectItem> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(ArrayList<SelectItem> listaArticulos) {
        this.listaArticulos = listaArticulos;
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

    public Short getSelectedUnidadMedida() {
        return selectedUnidadMedida;
    }

    public void setSelectedUnidadMedida(Short selectedUnidadMedida) {
        this.selectedUnidadMedida = selectedUnidadMedida;
    }

    public Tblexamenarticulo getArticuloAsignado() {
        return articuloAsignado;
    }

    public void setArticuloAsignado(Tblexamenarticulo articuloAsignado) {
        this.articuloAsignado = articuloAsignado;
    }

    public ArrayList<ExamenArticulo> getListaArticulosAsignados() {
        return listaArticulosAsignados;
    }

    public void setListaArticulosAsignados(ArrayList<ExamenArticulo> listaArticulosAsignados) {
        this.listaArticulosAsignados = listaArticulosAsignados;
    }

    public ArrayList<Parametro> getListaParametrosAsignados() {
        return listaParametrosAsignados;
    }

    public void setListaParametrosAsignados(ArrayList<Parametro> listaParametrosAsignados) {
        this.listaParametrosAsignados = listaParametrosAsignados;
    }

    public Tblparametro getParametroAsignado() {
        return parametroAsignado;
    }

    public void setParametroAsignado(Tblparametro parametroAsignado) {
        this.parametroAsignado = parametroAsignado;
    }

    public boolean isRenderValoresParametro() {
        return renderValoresParametro;
    }

    public void setRenderValoresParametro(boolean renderValoresParametro) {
        this.renderValoresParametro = renderValoresParametro;
    }

    public ArrayList<Examen> getListaExamenes() {
        return listaExamenes;
    }

    public void setListaExamenes(ArrayList<Examen> listaExamenes) {
        this.listaExamenes = listaExamenes;
    }

    public ExamenArticulo getSelectedArticuloAsignado() {
        return selectedArticuloAsignado;
    }

    public void setSelectedArticuloAsignado(ExamenArticulo selectedArticuloAsignado) {
        this.selectedArticuloAsignado = selectedArticuloAsignado;
    }

    public Parametro getSelectedParametroAsignado() {
        return selectedParametroAsignado;
    }

    public void setSelectedParametroAsignado(Parametro selectedParametroAsignado) {
        this.selectedParametroAsignado = selectedParametroAsignado;
    }

    public Examen getSelectedExamen() {
        return selectedExamen;
    }

    public void setSelectedExamen(Examen selectedExamen) {
        this.selectedExamen = selectedExamen;
    }

    public class ExamenArticulo implements Serializable {

        private Integer codigoArticulo;
        private String nombreArticulo;
        private Tblexamenarticulo examenarticulo;

        public ExamenArticulo(Integer codigoArticulo, String nombreArticulo, Tblexamenarticulo examenarticulo) {
            this.codigoArticulo = codigoArticulo;
            this.nombreArticulo = nombreArticulo;
            this.examenarticulo = examenarticulo;
        }

        private ExamenArticulo() {
        }

        public Integer getCodigoArticulo() {
            return codigoArticulo;
        }

        public void setCodigoArticulo(Integer codigoArticulo) {
            this.codigoArticulo = codigoArticulo;
        }

        public Tblexamenarticulo getExamenarticulo() {
            return examenarticulo;
        }

        public void setExamenarticulo(Tblexamenarticulo examenarticulo) {
            this.examenarticulo = examenarticulo;
        }

        public String getNombreArticulo() {
            return nombreArticulo;
        }

        public void setNombreArticulo(String nombreArticulo) {
            this.nombreArticulo = nombreArticulo;
        }
    }

    public class Parametro implements Serializable {

        Tblparametro parametro;
        Short idUnidadMedida;
        String unidadMedida;

        public Parametro(Tblparametro parametro, Short idUnidadMedida, String unidadMedida) {
            this.parametro = parametro;
            this.idUnidadMedida = idUnidadMedida;
            this.unidadMedida = unidadMedida;
        }

        private Parametro() {
        }

        public Short getIdUnidadMedida() {
            return idUnidadMedida;
        }

        public void setIdUnidadMedida(Short idUnidadMedida) {
            this.idUnidadMedida = idUnidadMedida;
        }

        public Tblparametro getParametro() {
            return parametro;
        }

        public void setParametro(Tblparametro parametro) {
            this.parametro = parametro;
        }

        public String getUnidadMedida() {
            return unidadMedida;
        }

        public void setUnidadMedida(String unidadMedida) {
            this.unidadMedida = unidadMedida;
        }
    }

    public class Examen implements Serializable {

        private Short idSeccion;
        private String nombreSeccion;
        private Tblexamen examen;

        public Examen(Short idSeccion, String nombreSeccion, Tblexamen examen) {
            this.idSeccion = idSeccion;
            this.nombreSeccion = nombreSeccion;
            this.examen = examen;
        }

        private Examen() {
        }

        public Tblexamen getExamen() {
            return examen;
        }

        public void setExamen(Tblexamen examen) {
            this.examen = examen;
        }

        public Short getIdSeccion() {
            return idSeccion;
        }

        public void setIdSeccion(Short idSeccion) {
            this.idSeccion = idSeccion;
        }

        public String getNombreSeccion() {
            return nombreSeccion;
        }

        public void setNombreSeccion(String nombreSeccion) {
            this.nombreSeccion = nombreSeccion;
        }
    }
}
