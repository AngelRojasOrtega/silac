/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.util.FacesUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author siman
 */
@ManagedBean(name="informeSeccionAsignadaMesBean")
@ViewScoped
public class InformeSeccionAsignadaMes implements Serializable{
    private Short selectedMes;
    private Short selectedAnio;
    private String selectedTipo;
    private String url;
    private ArrayList<SelectItem> listaMeses = new ArrayList<SelectItem>();

    /** Creates a new instance of InformeSeccionAsignadaMes */
    public InformeSeccionAsignadaMes() {
        this.init();
    }

     private void init() {
        
        this.listaMeses = new ArrayList<SelectItem>();
        this.selectedAnio = null;
        this.selectedMes = null;
        this.url = null;
        this.selectedTipo = null;
        this.populateListaMeses();
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
        String fileName = "informeSeccionAsignadaMes";
        String tipo = this.selectedTipo;       
       
        
        String params = "&tipo=" + tipo + "&mes=" + (selectedMes+1) + "&anio=" + selectedAnio;
        this.url = this.url + fileName + params;
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

    public Short getSelectedMes() {
        return selectedMes;
    }

    public void setSelectedMes(Short selectedMes) {
        this.selectedMes = selectedMes;
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
