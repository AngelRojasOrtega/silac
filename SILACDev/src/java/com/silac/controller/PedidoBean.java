/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblarticulo;
import com.silac.model.Tbldetallepedido;
import com.silac.model.Tblexamen;
import com.silac.model.TblexamenOrden;
import com.silac.model.Tblexamenarticulo;
import com.silac.model.Tblmovimiento;
import com.silac.model.Tblorden;
import com.silac.model.Tblpedido;
import com.silac.model.Tblsalida;

import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.DateSelectEvent;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "pedidoBean")
@ViewScoped
public class PedidoBean implements Serializable {

    private Short selectedCategoria;
    private Integer selectedArticulo;
    private ArrayList listaArticulos = new ArrayList();
    private String existencias;
    private String consumo;
    private String salidas;
    private String cantidad;
    private Date hoy;
    private Date minDate;
    private ArticuloBean articuloBean = (ArticuloBean) FacesUtil.getBean("articuloBean");
    private Date desde;
    private Date hasta;
    private ArticuloPedido selectedArticuloPedido;
    private ArrayList<ArticuloPedido> listaArticulosPedidos;
    private Tbldetallepedido detallepedido = new Tbldetallepedido();
    private Tblpedido pedido = new Tblpedido();

    /** Creates a new instance of PedidoBean */
    public PedidoBean() {
        this.detallepedido = new Tbldetallepedido();
        this.pedido = new Tblpedido();
        this.selectedArticulo = null;
        this.existencias = null;
        this.consumo = null;
        this.salidas = null;
        this.cantidad = null;
        this.hoy = new Date();
        this.minDate = null;
        this.articuloBean = (ArticuloBean) FacesUtil.getBean("articuloBean");
        this.selectedCategoria = null;
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        this.desde = new Date();
        this.hasta = new Date();
        this.listaArticulosPedidos = new ArrayList<ArticuloPedido>();
        cargarExistencias();
        //cargarConsumo();
    }

    /** Creates a new instance of ObjetosBeans */
    public void init() {
        this.selectedArticulo = null;
        this.existencias = null;
        this.hoy = new Date();
        this.minDate = null;
        this.articuloBean = (ArticuloBean) FacesUtil.getBean("articuloBean");
        this.selectedCategoria = null;
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        this.desde = new Date();
        this.hasta = new Date();
        cargarExistencias();
        //cargarConsumo();
    }

    public void crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

            if (listaArticulosPedidos.size() > 0) {
                pedido.setFecha(this.hoy);
                session.save(pedido);

                System.out.println("PASA AK");
                System.out.println("CARGA PEDIDO" + pedido.getFecha());

                Iterator iterPedido = this.listaArticulosPedidos.iterator();
                System.out.println("SIZE DE LISTA PEDIDOS" + listaArticulosPedidos.size());
                while (iterPedido.hasNext()) {
                    ArticuloPedido ap = (ArticuloPedido) iterPedido.next();
                    Tbldetallepedido temDp = new Tbldetallepedido();
                    temDp.setTblpedido(pedido);
                    Tblarticulo arti = (Tblarticulo) session.load(Tblarticulo.class, ap.getIdArticulo());
                    temDp.setTblarticulo(arti);
                    double consuReal = Double.parseDouble(ap.getConsumoReal());
                    temDp.setConsumoReal(consuReal);
                    double canti = Double.parseDouble(ap.getCantidadSoli());
                    temDp.setCantidadSolicitada(canti);
                    session.save(temDp);
                    //Tbldetallepedido tempDp = ap.

                }
                listaArticulosPedidos.clear();
                session.flush();
                session.clear();
                tx.commit();
                this.init();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Informacion:",
                        "El pedido se registro satisfactoriamente"));
            }
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:",
                    e.getMessage()));
        } finally {
            session.close();
        }
    }

    public void nuevo() {
        this.init();
    }

    public void handleDateSelect(DateSelectEvent event) {
        this.minDate = event.getDate();
        this.desde = event.getDate();
        //cargarConsumo();
        //cargarSalidas();
    }

    public void handleDateSelectDesde(DateSelectEvent event) {
        this.desde = event.getDate();
    }

    public void handleDateSelectHasta(DateSelectEvent event) {
        this.hasta = event.getDate();
        //cargarConsumo();
        //cargarSalidas();
    }

    /**Actualiza la lista de articulos*/
    public void renderCboArticulo() {
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
    }

    /**Determina si un articulo tiene existencia disponibles*/
    private void cargarExistencias() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (selectedArticulo != null) {
            Tblarticulo articulo = (Tblarticulo) session.load(Tblarticulo.class, selectedArticulo);
            existencias = Double.toString(articulo.getExistencias());
            if (existencias.equals("0.0")) {
                existencias = "No hay existencias disponibles";
            }
        }
        session.close();
    }

    public void renderTxtExistencias() {
        cargarExistencias();
    }

    private void cargarConsumo() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //if(selectedArticulo!=null){
        Calendar cDesde = Calendar.getInstance();
        cDesde.setTime(this.desde);
        cDesde.set(Calendar.MILLISECOND, 0);
        cDesde.set(Calendar.SECOND, 0);
        cDesde.set(Calendar.MINUTE, 0);
        cDesde.set(Calendar.HOUR, 0);
        System.out.println("FECHA DE INICIO" + cDesde);
        System.out.println("DESDE" + desde);

        Calendar cHasta = Calendar.getInstance();
        cHasta.setTime(this.hasta);
        cHasta.set(Calendar.MILLISECOND, 999);
        cHasta.set(Calendar.SECOND, 59);
        cHasta.set(Calendar.MINUTE, 59);
        cHasta.set(Calendar.HOUR, 23);
        System.out.println("FECHA DE FINAL" + cHasta);
        System.out.println("HASTA" + hasta);

        String sql = "SELECT a, ea, ex, eo, o, sum(ea.cantidad) FROM Tblarticulo a "
                + "join a.tblexamenarticulos ea "
                + "join ea.tblexamen ex "
                + "join ex.tblexamenOrdens eo "
                + "join eo.tblorden o "
                + "WHERE a.idArticulo = :idArticulo AND "
                + "(o.fechaRecepcion BETWEEN :desde AND :hasta) AND "
                + "(eo.estado LIKE 'pendiente') "
                + "GROUP BY ex.idExamen ";
        Query query = session.createQuery(sql);
        query.setParameter("idArticulo", selectedArticulo);
        query.setParameter("desde", cDesde.getTime());
        query.setParameter("hasta", cHasta.getTime());
        List result = query.list();
        System.out.println("HASTA PARA CONSULTA" + cHasta.getTime());
        Iterator iterConsumo = result.iterator();
        System.out.println("CARGA SIZE" + result.size());
        double suma = 0.0;
        while (iterConsumo.hasNext()) {
            Object[] ob = (Object[]) iterConsumo.next();
            Tblarticulo a = (Tblarticulo) ob[0];
            Tblexamenarticulo ea = (Tblexamenarticulo) ob[1];
            Tblexamen ex = (Tblexamen) ob[2];
            TblexamenOrden eo = (TblexamenOrden) ob[3];
            Tblorden o = (Tblorden) ob[4];
            double consumoReal = ((Double) ob[5]).doubleValue();
            System.out.println("CARGA FECHA DE RECEPCION" + o.getFechaRecepcion());
            System.out.println("CARGA CANTIDAD" + ea.getCantidad());
            System.out.println("NOMBRE ARTICULO" + a.getNombreArticulo());
            System.out.println("NOMBRE EXAMEN" + ex.getNombreExamen());
            System.out.println("CONSUMO REAL" + consumoReal);
            if (consumoReal != 0.0);
            {
                suma = suma + consumoReal;
                System.out.println("SUMA" + suma);
            }

        }
        consumo = Double.toString(suma);
        System.out.println("CONSUMO TOTAL" + consumo);
        session.close();
    }

    public void renderTxtConsumoReal() {
        cargarConsumo();
    }

    private void cargarSalidas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        //if(selectedArticulo!=null){
        Calendar cDesde = Calendar.getInstance();
        cDesde.setTime(this.desde);
        cDesde.set(Calendar.MILLISECOND, 0);
        cDesde.set(Calendar.SECOND, 0);
        cDesde.set(Calendar.MINUTE, 0);
        cDesde.set(Calendar.HOUR, 0);
        System.out.println("FECHA DE INICIO" + cDesde);
        System.out.println("DESDE" + desde);

        Calendar cHasta = Calendar.getInstance();
        cHasta.setTime(this.hasta);
        cHasta.set(Calendar.MILLISECOND, 999);
        cHasta.set(Calendar.SECOND, 59);
        cHasta.set(Calendar.MINUTE, 59);
        cHasta.set(Calendar.HOUR, 23);
        System.out.println("FECHA DE FINAL" + cHasta);
        System.out.println("HASTA" + hasta);

        String sql = "SELECT a, m, s, sum(s.cantidad) FROM Tblarticulo a "
                + "join a.tblmovimientos m "
                + "join m.tblsalidas s "
                + "WHERE a.idArticulo = :idArticulo AND "
                + "(m.fecha BETWEEN :desde AND :hasta) "
                + "GROUP BY m.idMovimiento ";
        Query query = session.createQuery(sql);
        query.setParameter("idArticulo", selectedArticulo);
        query.setParameter("desde", cDesde.getTime());
        query.setParameter("hasta", cHasta.getTime());
        List result = query.list();
        System.out.println("HASTA PARA CONSULTA" + cHasta.getTime());
        Iterator iterSalida = result.iterator();
        System.out.println("CARGA SIZE" + result.size());
        double suma = 0.0;
        while (iterSalida.hasNext()) {
            Object[] ob = (Object[]) iterSalida.next();
            Tblarticulo a = (Tblarticulo) ob[0];
            Tblmovimiento m = (Tblmovimiento) ob[1];
            Tblsalida s = (Tblsalida) ob[2];
            double salidasReg = ((Double) ob[3]).doubleValue();
            System.out.println("CARGA FECHA DE MOVIMIENTO" + m.getFecha());
            System.out.println("CARGA CANTIDAD SALIDAS" + s.getCantidad());
            System.out.println("NOMBRE ARTICULO" + a.getNombreArticulo());

            System.out.println("SALIDAS REGISTRADAS" + salidasReg);
            if (salidasReg != 0.0);
            {
                suma = suma + salidasReg;
                System.out.println("SUMA" + suma);
            }

        }
        salidas = Double.toString(suma);
        System.out.println("CONSUMO TOTAL" + salidas);
        session.close();
    }

    public void renderTxtSalidasRegistradas() {
        cargarSalidas();
        //asignarArticulo();
    }

    public void asignarArticulo() {
        boolean exist = false;
        System.out.println("ENTRA A ASIGNAR ARTICULO");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tblarticulo articulo = (Tblarticulo) session.load(Tblarticulo.class, this.selectedArticulo);
        Iterator iterArti = this.listaArticulosPedidos.iterator();
        while (iterArti.hasNext()) {
            ArticuloPedido ap = (ArticuloPedido) iterArti.next();
            System.out.println("ARTICULO" + ap.getNombreArticulo());
            if (ap.getIdArticulo().intValue() == this.selectedArticulo.intValue()) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            System.out.println("ENTRA A ASIGNAR ARTICULO AL IF");
            //this.articulo.setTblexamen(examen);
            this.listaArticulosPedidos.add(new ArticuloPedido(articulo.getIdArticulo(),
                    articulo.getCodigoArticulo(),
                    articulo.getNombreArticulo(),
                    cantidad, consumo));
        }
        session.close();
        this.selectedCategoria = null;
        this.selectedArticulo = null;
        this.existencias = null;
        this.consumo = null;
        this.salidas = null;
        this.cantidad = null;
        //crear();
        //this.examenSolicitado = new TblexamenOrden();
    }

    /**
     * Getters & Setters
     */
    public Tbldetallepedido getDetallepedido() {
        return detallepedido;
    }

    public void setDetallepedido(Tbldetallepedido detallepedido) {
        this.detallepedido = detallepedido;
    }

    public Tblpedido getPedido() {
        return pedido;
    }

    public void setPedido(Tblpedido pedido) {
        this.pedido = pedido;
    }

    public ArrayList getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(ArrayList listaArticulos) {
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

    public String getExistencias() {
        return existencias;
    }

    public void setExistencias(String existencias) {
        this.existencias = existencias;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public ArticuloBean getArticuloBean() {
        return articuloBean;
    }

    public void setArticuloBean(ArticuloBean articuloBean) {
        this.articuloBean = articuloBean;
    }

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public String getConsumo() {
        return consumo;
    }

    public void setConsumo(String consumo) {
        this.consumo = consumo;
    }

    public String getSalidas() {
        return salidas;
    }

    public void setSalidas(String salidas) {
        this.salidas = salidas;
    }

    public ArrayList<ArticuloPedido> getListaArticulosPedidos() {
        return listaArticulosPedidos;
    }

    public void setListaArticulosPedidos(ArrayList<ArticuloPedido> listaArticulosPedidos) {
        this.listaArticulosPedidos = listaArticulosPedidos;
    }

    public ArticuloPedido getSelectedArticuloPedido() {
        return selectedArticuloPedido;
    }

    public void setSelectedArticuloPedido(ArticuloPedido selectedArticuloPedido) {
        this.selectedArticuloPedido = selectedArticuloPedido;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public class ArticuloPedido implements Serializable {

        private Integer idArticulo;
        private String codigoArticulo;
        private String nombreArticulo;
        private String cantidadSoli;
        private String consumoReal;

        public ArticuloPedido(Integer idArticulo, String codigoArticulo, String nombreArticulo, String cantidadSoli, String consumoReal) {
            this.idArticulo = idArticulo;
            this.codigoArticulo = codigoArticulo;
            this.nombreArticulo = nombreArticulo;
            this.cantidadSoli = cantidadSoli;
            this.consumoReal = consumoReal;
            System.out.println("ID ARTICULO AGREGADO A LA LISTA DE PEDIDOS" + idArticulo);
            System.out.println("CODIGO ARTICULO EN LISTA DE PEDIDOS" + codigoArticulo);
            System.out.println("NOMBRE ARTICULO EN LISTA DE PEDIDOS" + nombreArticulo);
            System.out.println("CANTIDAD SOLICITADA" + cantidadSoli);
            System.out.println("CONSUMO REAL" + consumoReal);

        }

        private ArticuloPedido() {
        }

        public String getCodigoArticulo() {
            return codigoArticulo;
        }

        public void setCodigoArticulo(String codigoArticulo) {
            this.codigoArticulo = codigoArticulo;
        }

        public Integer getIdArticulo() {
            return idArticulo;
        }

        public void setIdArticulo(Integer idArticulo) {
            this.idArticulo = idArticulo;
        }

        public String getNombreArticulo() {
            return nombreArticulo;
        }

        public void setNombreArticulo(String nombreArticulo) {
            this.nombreArticulo = nombreArticulo;
        }

        public String getCantidadSoli() {
            return cantidadSoli;
        }

        public void setCantidadSoli(String cantidadSoli) {
            this.cantidadSoli = cantidadSoli;
        }

        public String getConsumoReal() {
            return consumoReal;
        }

        public void setConsumoReal(String consumoReal) {
            this.consumoReal = consumoReal;
        }
    }
}
