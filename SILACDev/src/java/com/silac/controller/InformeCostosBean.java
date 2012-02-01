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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author siman
 */
@ManagedBean(name = "informeCostosBean")
@ViewScoped
public class InformeCostosBean implements Serializable {

    private ArrayList<SelectItem> listaMeses = new ArrayList<SelectItem>();
    private Short selectedAnio;
    private Short selectedMes;
    private ArrayList listaCostos = new ArrayList();
    private String selectedTipo;
    private String url;

    /** Creates a new instance of InformeCostosBean */
    public InformeCostosBean() {
        this.init();
    }

    private void init() {
        this.listaMeses = new ArrayList<SelectItem>();
        this.selectedAnio = null;
        this.selectedMes = null;
        this.listaCostos = new ArrayList();
        this.selectedTipo = null;
        this.populateListaMeses();
    }

    public void populateInformeCostos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT m.fecha as fecha, c.categoria as categoria, a.nombreArticulo, IFNULL(SUM(s.cantidad),0) as cantidad, e.precio as precio, (CEIL((IFNULL(SUM(s.cantidad),0)*precio)*100)/100) as total "
                + "FROM tblcatalogo c "
                + "INNER JOIN tblarticulo a ON a.idCatalogo = c.idCatalogo "
                + "INNER JOIN tblmovimiento m ON m.idArticulo = a.idArticulo "
                + "INNER JOIN tblsalida s ON s.idMovimiento = m.idMovimiento "
                + "INNER JOIN tblentrada e ON e.idEntrada = s.idEntrada "
                + "WHERE MONTH(m.fecha) = :mes AND YEAR(m.fecha) = :year "
                + "GROUP BY c.idCatalogo, a.idArticulo, e.precio ";
        listaCostos = new ArrayList();
        Query query = session.createSQLQuery(sql);
        query.setParameter("mes", selectedMes+1);
        query.setParameter("year", selectedAnio);
        listaCostos = (ArrayList) query.list();
        session.close();
    }

    private void populateListaMeses() {
        String[] months = FacesUtil.getMonths();
        for (int i = 0; i < months.length - 1; i++) {
            listaMeses.add(new SelectItem(i, months[i], ""));
        }
    }
    
     public void imprimir() {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.url = contextPath + "/faces/createReportServlet?fileName=";
        String fileName = "informeCostos";
        String tipo = this.selectedTipo;       
       
        
        String params = "&tipo=" + tipo + "&mes=" + selectedMes + "&anio=" + selectedAnio;
        this.url = this.url + fileName + params;
    }

    public ArrayList getListaCostos() {
        return listaCostos;
    }

    public void setListaCostos(ArrayList listaCostos) {
        this.listaCostos = listaCostos;
    }

    public Short getSelectedMes() {
        return selectedMes;
    }

    public void setSelectedMes(Short selectedMes) {
        this.selectedMes = selectedMes;
    }

    public ArrayList<SelectItem> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(ArrayList<SelectItem> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public Short getSelectedAnio() {
        return selectedAnio;
    }

    public void setSelectedAnio(Short selectedAnio) {
        this.selectedAnio = selectedAnio;
    }

    public String getSelectedTipo() {
        return selectedTipo;
    }

    public void setSelectedTipo(String selectedTipo) {
        this.selectedTipo = selectedTipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
    
}
