/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblarticulo;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author siman
 */
@ManagedBean(name = "kardexBean")
@ViewScoped
public class kardexBean implements Serializable {

    private Integer selectedArticulo;
    private Short selectedCategoria;
    private ArrayList listaMovimientos = new ArrayList();
    private String existencias;
    private ArticuloBean articuloBean = (ArticuloBean) FacesUtil.getBean("articuloBean");
    private ArrayList<SelectItem> listaArticulos = new ArrayList<SelectItem>();

    /** Creates a new instance of kardexBean */
    public kardexBean() {
    }

    public void populateListaKardex() {
        this.existencias = "0.0";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Integer id = 0;
        if (selectedArticulo != null) {
            id = selectedArticulo;
        }
        String sql = "SELECT id, idMovimiento, idArticulo, articulo, fecha, concepto, ubicacion, "
                + "vencimiento, cantidadEntrada, precioEntrada, cantidadSalida, precioSalida, saldo "
                + "FROM ((SELECT "
                + "CONCAT( m.idMovimiento, e.idEntrada) AS id, "
                + "m.idMovimiento AS idMovimiento, "
                + "a.idArticulo AS idArticulo, "
                + "a.nombreArticulo AS articulo, "
                + "m.fecha AS fecha, "
                + "m.concepto AS concepto, "
                + "p.lugarProcedencia AS ubicacion, "
                + "e.fechaVencimiento AS vencimiento, "
                + "e.cantidad AS cantidadEntrada, "
                + "e.precio AS precioEntrada, "
                + "'' AS cantidadSalida, "
                + "'' AS precioSalida, "
                + "(CEIL((SELECT SUM(eEntrada.cantidad*eEntrada.precio) - "
                + "(SELECT IFNULL(SUM(sSalida.cantidad*eSalida.precio),0) AS saldoSalida FROM tblarticulo aSalida "
                + "INNER JOIN tblmovimiento mSalida ON mSalida.idArticulo = aSalida.idArticulo "
                + "INNER JOIN tblsalida sSalida ON sSalida.idMovimiento = mSalida.idMovimiento "
                + "INNER JOIN tblentrada eSalida ON sSalida.idEntrada= eSalida.idEntrada "
                + "WHERE mSalida.fecha<= m.fecha AND aSalida.idArticulo = :idArticulo) "
                + "AS saldoSalida FROM tblarticulo aEntrada "
                + "INNER JOIN tblmovimiento mEntrada ON mEntrada.idArticulo = aEntrada.idArticulo "
                + "INNER JOIN tblentrada eEntrada ON eEntrada.idMovimiento = mEntrada.idMovimiento "
                + "WHERE mEntrada.fecha<= m.fecha AND aEntrada.idArticulo = :idArticulo)*100)/100) AS saldo "
                + "FROM tblarticulo a "
                + "INNER JOIN tblmovimiento m ON m.idArticulo = a.idArticulo "
                + "INNER JOIN tblentrada e ON e.idMovimiento = m.idMovimiento "
                + "INNER JOIN tblprocedenciaarticulo p ON p.idProcedencia = e.idProcedencia) "
                + "union (select "
                + "CONCAT(m.idMovimiento, s.idSalida) AS id, "
                + "m.idMovimiento AS idMovimiento, "
                + "a.idArticulo AS idArticulo, "
                + "a.nombreArticulo AS articulo, "
                + "m.fecha AS fecha, "
                + "m.concepto AS concepto, "
                + "s.destino AS ubicacion, "
                + "'' AS vencimiento, "
                + "'' AS cantidadEntrada, "
                + "'' AS precioEntrada, "
                + "s.cantidad AS cantidadSalida, "
                + "e.precio AS precioSalida, "
                + "(CEIL((SELECT SUM(eEntrada.cantidad*eEntrada.precio) - "
                + "(SELECT IFNULL(SUM(sSalida.cantidad*eSalida.precio),0) AS saldoSalida FROM tblarticulo aSalida "
                + "INNER JOIN tblmovimiento mSalida ON mSalida.idArticulo = aSalida.idArticulo "
                + "INNER JOIN tblsalida sSalida ON sSalida.idMovimiento = mSalida.idMovimiento "
                + "INNER JOIN tblentrada eSalida ON sSalida.idEntrada= eSalida.idEntrada "
                + "WHERE mSalida.fecha<= m.fecha AND aSalida.idArticulo = :idArticulo) "
                + "AS saldoSalida FROM tblarticulo aEntrada "
                + "INNER JOIN tblmovimiento mEntrada ON mEntrada.idArticulo = aEntrada.idArticulo "
                + "INNER JOIN tblentrada eEntrada ON eEntrada.idMovimiento = mEntrada.idMovimiento "
                + "WHERE mEntrada.fecha<= m.fecha AND aEntrada.idArticulo = :idArticulo)*100)/100) AS saldo "
                + "from tblarticulo a "
                + "INNER JOIN tblmovimiento m ON m.idArticulo = a.idArticulo "
                + "INNER JOIN tblsalida s ON s.idMovimiento = m.idMovimiento "
                + "INNER JOIN tblentrada e ON s.idEntrada = e.idEntrada "
                + ") ) as kardex "
                + "WHERE kardex.idArticulo = :idArticulo "
                + "GROUP BY kardex.id "
                + "ORDER BY kardex.fecha ";
        listaMovimientos = new ArrayList();
        Query query = session.createSQLQuery(sql).addScalar("fecha").addScalar("concepto", Hibernate.TEXT).addScalar("ubicacion").addScalar("vencimiento").addScalar("cantidadEntrada").addScalar("precioEntrada").addScalar("cantidadSalida").addScalar("precioSalida").addScalar("saldo");
        query.setParameter("idArticulo", selectedArticulo);
        listaMovimientos = (ArrayList) query.list();
        if (selectedArticulo != null) {
            Tblarticulo articulo = (Tblarticulo) session.load(Tblarticulo.class, selectedArticulo);
            existencias = Double.toString(articulo.getExistencias());
        }
        session.close();
    }

    public void renderCboArticulo() {
        listaMovimientos = new ArrayList();
        this.listaArticulos = new ArrayList<SelectItem>();
        this.listaArticulos = articuloBean.cargarListaArticulo(this.selectedCategoria);
        this.existencias = "0.0";
    }

    public Integer getSelectedArticulo() {
        return selectedArticulo;
    }

    public void setSelectedArticulo(Integer selectedArticulo) {
        this.selectedArticulo = selectedArticulo;
    }

    public ArrayList getListaMovimientos() {
        return listaMovimientos;
    }

    public void setListaMovimientos(ArrayList listaMovimientos) {
        this.listaMovimientos = listaMovimientos;
    }

    public String getExistencias() {
        return existencias;
    }

    public void setExistencias(String existencias) {
        this.existencias = existencias;
    }

    public ArticuloBean getArticuloBean() {
        return articuloBean;
    }

    public void setArticuloBean(ArticuloBean articuloBean) {
        this.articuloBean = articuloBean;
    }

    public ArrayList<SelectItem> getListaArticulos() {
        return listaArticulos;
    }

    public void setListaArticulos(ArrayList<SelectItem> listaArticulos) {
        this.listaArticulos = listaArticulos;
    }

    public Short getSelectedCategoria() {
        return selectedCategoria;
    }

    public void setSelectedCategoria(Short selectedCategoria) {
        this.selectedCategoria = selectedCategoria;
    }
}
