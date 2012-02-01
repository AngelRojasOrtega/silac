/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;


import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author siman
 */
@ManagedBean(name = "informePedidoBean")
@ViewScoped
public class InformePedidoBean implements Serializable {

    private ArrayList<SelectItem> listaMeses = new ArrayList<SelectItem>();
    private Short selectedAnio;
    private Short selectedMes;
    private ArrayList listaPedidos = new ArrayList();

    /** Creates a new instance of InformePedidoBean */
    public InformePedidoBean() {
        this.init();
    }

    private void init() {
        this.listaMeses = new ArrayList<SelectItem>();
        this.selectedAnio = null;
        this.selectedMes = null;
        this.listaPedidos = new ArrayList();
        this.populateListaMeses();
    }

    public void populateInformePedido() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT c.categoria, a.nombreArticulo, p.fecha, d.consumoReal, d.cantidadSolicitada "
                + "FROM tblcatalogo c "
                + "INNER JOIN tblarticulo a ON a.idCatalogo = c.idCatalogo "
                + "INNER JOIN tbldetallepedido d ON d.idArticulo = a.idArticulo "
                + "INNER JOIN tblpedido p ON p.idPedido = d.idPedido "
                + "WHERE MONTH(p.fecha) = :mes AND YEAR(p.fecha) = :year ";
        listaPedidos = new ArrayList();
        Query query = session.createSQLQuery(sql);
        query.setParameter("mes", selectedMes+1);
        query.setParameter("year", selectedAnio);
        listaPedidos = (ArrayList) query.list();
        session.close();
    }

    private void populateListaMeses() {
        String[] months = FacesUtil.getMonths();
        for (int i = 0; i < months.length - 1; i++) {
            listaMeses.add(new SelectItem(i, months[i], ""));
        }
    }

    public ArrayList<SelectItem> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(ArrayList<SelectItem> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public ArrayList getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(ArrayList listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    public Short getSelectedAnio() {
        return selectedAnio;
    }

    public void setSelectedAnio(Short selectedAnio) {
        this.selectedAnio = selectedAnio;
    }

    public Short getSelectedMes() {
        return selectedMes;
    }

    public void setSelectedMes(Short selectedMes) {
        this.selectedMes = selectedMes;
    }
}
