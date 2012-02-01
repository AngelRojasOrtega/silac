/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblarticulo;
import com.silac.model.Tblentrada;
import com.silac.model.Tblmovimiento;
import com.silac.model.Tblsalida;
import com.silac.model.Tblseccion;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
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
@ManagedBean(name = "salidaBean")
@ViewScoped
public class SalidaBean implements Serializable {

    private String existencias;
    private Integer selectedArticulo;
    private Short selectedCategoria;
    private String selectedDestino;
    private String selectedMotivo;
    private String verificar;
    private ArrayList<SelectItem> listaArticulos = new ArrayList<SelectItem>();
    private ArrayList<Articulo> listaExistencias = new ArrayList<Articulo>();
    private ArrayList<Articulo> listaEntradas = new ArrayList<Articulo>();
    private List entradas;
    private List movimientos;
    private List salidas;
    private Object selectedEntrada;
    private Object SelectedMovimiento;
    private Object selectedSalida;
    private Tblentrada entrada = new Tblentrada();
    private Tblmovimiento movimiento = new Tblmovimiento();
    private Tblsalida salida = new Tblsalida();
    private ArticuloBean articuloBean = (ArticuloBean) FacesUtil.getBean("articuloBean");

    /**Crea nueva instancia de SalidaBean*/
    public SalidaBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.existencias = null;
        this.selectedArticulo = null;
        this.selectedCategoria = null;
        this.selectedDestino = null;
        this.selectedMotivo = null;
        this.verificar = null;
        this.listaArticulos = new ArrayList<SelectItem>();
        this.listaExistencias = new ArrayList<Articulo>();
        this.listaEntradas = new ArrayList<Articulo>();
        this.entradas = null;
        this.movimientos = null;
        this.salidas = null;
        this.selectedEntrada = null;
        this.SelectedMovimiento = null;
        this.selectedSalida = null;
        this.entrada = new Tblentrada();
        this.movimiento = new Tblmovimiento();
        this.salida = new Tblsalida();
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        cargarExistencias();
        populateListaExistencias();
    }

    /**Crea una nueva salida*/
    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {

            if (existencias.equals("0.0")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ADVERTENCIA:", "No hay existencias suficientes para registrar esta salida"));
            } else {

                listaEntradas.clear();
                tx = (Transaction) session.beginTransaction();
                //CONSULTA PARA RECORRER ENTRADAS
                Double contador = 0.0;
                Double cant = Double.parseDouble(verificar);
                System.out.println("valor de verificar" + verificar);
                Double cantReq = Double.parseDouble(verificar);
                Double controlExis = 0.0;
                System.out.println("CONTADOR INICIAL" + contador);
                System.out.println("CANTIDAD" + cant);
                System.out.println("CANTIDAD REQUERIDA" + cantReq);


                String sql = "SELECT a, e, m.fecha from Tblarticulo a "
                        + "JOIN a.tblmovimientos m "
                        + "JOIN m.tblentradas e "
                        + "WHERE a.idArticulo = " + this.selectedArticulo + " AND e.existencia > 0.0 "
                        + "order by e.fechaRecepcion asc, e.fechaVencimiento asc";
                Query query = session.createQuery(sql);
                Iterator ite = session.createQuery(sql).list().iterator();

                while (ite.hasNext()) {
                    Object[] obj = (Object[]) ite.next();
                    Tblarticulo articulo = (Tblarticulo) obj[0];
                    Tblentrada ent = (Tblentrada) obj[1];
                    Date fecha = (Date) obj[2];
                    listaEntradas.add(new Articulo(articulo, ent, fecha));

                }

                //populateListaExistencias(); //carga la lista de existencias de articulo
                //pasa la lista de existencias al iterador
                Iterator iter = listaEntradas.iterator();
                System.out.println("SIZE DE LA LISTA" + listaEntradas.size());
                //guarda el movimiento
                Tblarticulo armo = (Tblarticulo) session.load(Tblarticulo.class, selectedArticulo);
                movimiento.setTblarticulo(armo);
                movimiento.setFecha(new Date());
                movimiento.setTipoMovimiento(false);
                session.save(movimiento);
                System.out.println(movimiento.getIdMovimiento());
                System.out.println(movimiento.getTblarticulo());
                System.out.println(movimiento.getTblentradas());
                System.out.println(movimiento.getTblsalidas());
                System.out.println(movimiento.getConcepto());
                System.out.println(movimiento.getFecha());

                //inicia las iteraciones           
                while (iter.hasNext()) {
                    Articulo a = (Articulo) iter.next();
                    //carga las tablas necesarias para la salida
                    Tblentrada e = (Tblentrada) session.load(Tblentrada.class, a.entrada.getIdEntrada());
                    System.out.println("ID DE LA ENTRADA" + a.entrada.getIdEntrada());
                    Tblarticulo ar = (Tblarticulo) session.load(Tblarticulo.class, selectedArticulo);
                    System.out.println("ARTICULO SELECCIONADO" + selectedArticulo);
                    Tblseccion s = (Tblseccion) session.load(Tblseccion.class, Short.parseShort(selectedDestino));
                    System.out.println("DESTINO SELECCIONADO" + selectedDestino);
                    //verifica si el estado es bueno, por caducar o si el articulo no es perecedero 
                    if (a.determinarEstado().equals("Por Caducar") || a.determinarEstado().equals("Bueno") || !a.articulo.isPerecedero());
                    {
                        if (cantReq > contador) {
                            System.out.println("CANTIDAD REQUERIDA" + cantReq);
                            System.out.println("CONTADOR" + contador);

                            //si la cantidad de control es mayor que las existencias de la entrada
                            if (cant >= a.entrada.getExistencia()) {
                                System.out.println("SI LA CANTIDAD ES MAYOR O IGUAL QUE LA EXISTENCIA EN LA ENTRADA");
                                System.out.println("CANTIDAD" + cant);
                                System.out.println("existencias de la entrada" + a.entrada.getExistencia());
                                cant = cant - a.entrada.getExistencia();
                                System.out.println("CANTIDAD MENOS EXISTENCIA" + cant);
                                controlExis = a.entrada.getExistencia();
                                System.out.println("CONTROL DE EXISTENCIAS IGUAL A EXISTENCIAS DE LA ENTRADA" + controlExis);
                                contador = contador + a.entrada.getExistencia();
                                System.out.println("CONTADOR ACTUALIZADO" + contador);

                                //actualiza las existencias de la entrada a cero
                                System.out.println("ENTRADA A EXTRAER" + e.getExistencia());
                                e.setExistencia(e.getExistencia() - controlExis);
                                session.saveOrUpdate(e);
                                System.out.println("ENTRADA ACTUALIZADA" + e.getExistencia());
                                //actualiza y resta la salida a las existencias totales
                                ar.setExistencias(ar.getExistencias() - controlExis);
                                session.saveOrUpdate(ar);
                                //guarda la salida
                                salida = new Tblsalida();
                                salida.setTblentrada(e);
                                salida.setTblmovimiento(movimiento);
                                salida.setDestino(s.getNombreSeccion());
                                salida.setCantidad(controlExis);
                                salida.setMotivo(selectedMotivo);
                                session.save(salida);
                                System.out.println("CANTIDAD EN LA SALIDA" + salida.getCantidad());
                            } else {
                                System.out.println("SI LA CANTIDAD ES MENOR QUE LA EXISTENCIA DE LA ENTRADA");
                                System.out.println("CONTADOR QUE ENTRA" + contador);
                                contador = contador + cant;
                                System.out.println("CONTADOR MAS CANTIDAD" + contador);
                                System.out.println("CANTIDAD" + cant);
                                //actualiza la entrada restando lo que falta para completar la salida
                                e.setExistencia(e.getExistencia() - cant);
                                session.saveOrUpdate(e);
                                System.out.println("EXISTENCIAS ACTUALIZADA LA ENTRADA" + e.getExistencia());
                                //actualiza y resta la salida a las existencias totales
                                ar.setExistencias(ar.getExistencias() - cant);
                                session.saveOrUpdate(ar);
                                //guarda la salida
                                salida = new Tblsalida();
                                salida.setTblentrada(e);
                                salida.setTblmovimiento(movimiento);
                                salida.setDestino(s.getNombreSeccion());
                                salida.setCantidad(cant);
                                salida.setMotivo(selectedMotivo);
                                session.save(salida);
                                System.out.println("CANTIDAD REGISTRADA EN LA SALIDA" + salida.getCantidad());
                                cant = 0.0;
                                System.out.println("CANTIDAD A CERO" + cant);
                            }
                        }
                    }
                }
                session.flush();
                session.clear();
                tx.commit();
                this.init();
                try {
                    ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
                    String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
                    ctx.redirect(ctxPath + "/faces/inventario/frmKardex.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La salida se registro satisfactoriamente"));
            }
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

    /**Muestra las existencias disponibles del producto seleciconado*/
    private void cargarExistencias() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (selectedArticulo != null) {
            Tblarticulo articulo = (Tblarticulo) session.load(Tblarticulo.class, selectedArticulo);
            existencias = Double.toString(articulo.getExistencias());
            //if (existencias.equals("0.0")) {
            // existencias = "No hay existencias disponibles";
            //}
        }
        session.close();
    }

    /**Muestra la lista de existencias del articulo seleccionado*/
    public void populateListaExistencias() {
        listaExistencias = new ArrayList<Articulo>();
        if (selectedArticulo != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String sql = "SELECT a, e, m.fecha FROM Tblarticulo a "
                    + "JOIN a.tblmovimientos m "
                    + "JOIN m.tblentradas e "
                    + "WHERE a.idArticulo = " + this.selectedArticulo + " AND e.existencia > 0.0 "
                    + "ORDER BY e.fechaRecepcion ASC, e.fechaVencimiento ASC";
            Query query = session.createQuery(sql);
            //query.setParameter("idArticulo", this.selectedArticulo);
            System.out.println("ARTICULO SELECCIONADO" + selectedArticulo);
            Iterator iter = session.createQuery(sql).list().iterator();
            while (iter.hasNext()) {
                Object[] obj = (Object[]) iter.next();
                Tblarticulo a = (Tblarticulo) obj[0];
                Tblentrada e = (Tblentrada) obj[1];
                Date f = (Date) obj[2];
                listaExistencias.add(new Articulo(a, e, f));
                System.out.println("ID ENTRADA  " + e.getIdEntrada());
                System.out.println("EXISTENCIA  " + e.getExistencia());
            }
            session.close();
        }
    }

    public void populateListaExistenciasEstado() {
        listaExistencias = new ArrayList<Articulo>();
        if (selectedArticulo != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String sql = "SELECT a, e, m.fecha FROM Tblarticulo a "
                    + "JOIN a.tblmovimientos m "
                    + "JOIN m.tblentradas e "
                    + "WHERE a.idArticulo = " + this.selectedArticulo
                    + " ORDER BY e.fechaRecepcion ASC, e.fechaVencimiento ASC";
            Query query = session.createQuery(sql);
            //query.setParameter("idArticulo", this.selectedArticulo);
            System.out.println("ARTICULO SELECCIONADO" + selectedArticulo);
            Iterator iter = session.createQuery(sql).list().iterator();
            while (iter.hasNext()) {
                Object[] obj = (Object[]) iter.next();
                Tblarticulo a = (Tblarticulo) obj[0];
                Tblentrada e = (Tblentrada) obj[1];
                Date f = (Date) obj[2];
                listaExistencias.add(new Articulo(a, e, f));
                System.out.println("ID ENTRADA  " + e.getIdEntrada());
                System.out.println("EXISTENCIA  " + e.getExistencia());
            }
            session.close();
        }
    }

    /**Actualiza la lista desplegable de articlos*/
    public void renderCboArticulo() {
        this.listaExistencias = new ArrayList<Articulo>();
        this.listaArticulos = new ArrayList<SelectItem>();
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        this.existencias = "0.0";
    }

    /**Actualiza las existencias de los articulos*/
    public void renderTxtExistencias() {
        this.listaExistencias = new ArrayList<Articulo>();
        this.existencias = "0.0";
        cargarExistencias();
    }

    /**
     * Getters & Setters
     */
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

    public ArticuloBean getArticuloBean() {
        return articuloBean;
    }

    public void setArticuloBean(ArticuloBean articuloBean) {
        this.articuloBean = articuloBean;
    }

    public ArrayList<Articulo> getListaEntradas() {
        return listaEntradas;
    }

    public void setListaEntradas(ArrayList<Articulo> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }

    public void setSelectedCategoria(Short selectedCategoria) {
        this.selectedCategoria = selectedCategoria;
    }

    public String getSelectedDestino() {
        return selectedDestino;
    }

    public void setSelectedDestino(String selectedDestino) {
        this.selectedDestino = selectedDestino;
    }

    public String getExistencias() {
        return existencias;
    }

    public void setExistencias(String existencias) {
        this.existencias = existencias;
    }

    public ArrayList<Articulo> getListaExistencias() {
        return listaExistencias;
    }

    public void setListaExistencias(ArrayList<Articulo> listaExistencias) {
        this.listaExistencias = listaExistencias;
    }

    public Object getSelectedMovimiento() {
        return SelectedMovimiento;
    }

    public void setSelectedMovimiento(Object SelectedMovimiento) {
        this.SelectedMovimiento = SelectedMovimiento;
    }

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

    public Tblsalida getSalida() {
        return salida;
    }

    public void setSalida(Tblsalida salida) {
        this.salida = salida;
    }

    public List getSalidas() {
        return salidas;
    }

    public void setSalidas(List salidas) {
        this.salidas = salidas;
    }

    public Object getSelectedEntrada() {
        return selectedEntrada;
    }

    public void setSelectedEntrada(Object selectedEntrada) {
        this.selectedEntrada = selectedEntrada;
    }

    public Object getSelectedSalida() {
        return selectedSalida;
    }

    public void setSelectedSalida(Object selectedSalida) {
        this.selectedSalida = selectedSalida;
    }

    public String getSelectedMotivo() {
        return selectedMotivo;
    }

    public void setSelectedMotivo(String selectedMotivo) {
        this.selectedMotivo = selectedMotivo;
    }

    public String getVerificar() {
        return verificar;
    }

    public void setVerificar(String verificar) {
        this.verificar = verificar;
    }

    public class Articulo implements Serializable {

        private Tblarticulo articulo;
        private Tblentrada entrada;
        private Date fecha;

        public Articulo(Tblarticulo articulo, Tblentrada entrada, Date fecha) {
            this.articulo = articulo;
            this.entrada = entrada;
            this.fecha = fecha;
        }

        public String determinarEstado() {
            String estado = "";
            if (articulo.isPerecedero()) {
                Calendar hoy = Calendar.getInstance();
                Calendar fechaVencimiento = Calendar.getInstance();
                Calendar fechaVencMaximo = Calendar.getInstance();

                hoy.setTime(new Date());
                fechaVencimiento.setTime(entrada.getFechaVencimiento());
                fechaVencMaximo.setTime(entrada.getVencimientoMaximo());

                if (hoy.compareTo(fechaVencMaximo) > 0) {
                    estado = "Caducado";
                } else if (hoy.compareTo(fechaVencimiento) > 0) {
                    estado = "Por Caducar";
                } else {
                    estado = "Bueno";
                }
            }

            return estado;
        }

        public Tblarticulo getArticulo() {
            return articulo;
        }

        public void setArticulo(Tblarticulo articulo) {
            this.articulo = articulo;
        }

        public Tblentrada getEntrada() {
            return entrada;
        }

        public void setEntrada(Tblentrada entrada) {
            this.entrada = entrada;
        }

        public Date getFecha() {
            return fecha;
        }

        public void setFecha(Date fecha) {
            this.fecha = fecha;
        }
    }
}
