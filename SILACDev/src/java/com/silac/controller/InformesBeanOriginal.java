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
 * @author Marin
 */
@ManagedBean(name = "informesBeanOriginal")
@ViewScoped
public class InformesBeanOriginal implements Serializable {

    private ArrayList<SelectItem> listaMeses = new ArrayList<SelectItem>();
    private String selectedInforme;
    private String url;
    private int selectedMes;
    private int anio;
    private Short selectedModalidad;
    private String selectedTipo;
       
    /** Creates a new instance of InformesBean */
    public InformesBeanOriginal() {
        this.init();
    }

    public void populateListaMeses() {
        String[] months = FacesUtil.getMonths();
        for (int i = 0; i < months.length - 1; i++) {
            listaMeses.add(new SelectItem(i, months[i], ""));

        }
    }
    public void onSelectInforme (){
        String contextPath=FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.url=contextPath+"/faces/createReportServlet?fileName=";
        String fileName="";
        String tipo=this.selectedTipo;
        String params="&tipo="+ this.selectedTipo + "&anio="+this.anio + "&mes="+ (this.selectedMes+1) + "&idModalidad="+this.selectedModalidad.intValue();
         if(this.selectedInforme !=null){ 
             if("informeMensualLaboratorio".equals(selectedInforme)){
               fileName=selectedInforme;
               this.url=this.url + fileName + params;                
            }
          }
    }

    private void init() {
        this.populateListaMeses();
        this.selectedInforme=null;
        this.selectedMes=0;
        this.url=null;
        this.anio=0;
        this.selectedModalidad=null;
        
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getSelectedMes() {
        return selectedMes;
    }

    public void setSelectedMes(int selectedMes) {
        this.selectedMes = selectedMes;
    }

    public Short getSelectedModalidad() {
        return selectedModalidad;
    }

    public void setSelectedModalidad(Short selectedModalidad) {
        this.selectedModalidad = selectedModalidad;
    }
    

    public String getSelectedInforme() {
        return selectedInforme;
    }

    public void setSelectedInforme(String selectedInforme) {
        this.selectedInforme = selectedInforme;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<SelectItem> getListaMeses() {
        return listaMeses;
    }

    public void setListaMeses(ArrayList<SelectItem> listaMeses) {
        this.listaMeses = listaMeses;
    }

    public String getSelectedTipo() {
        return selectedTipo;
    }

    public void setSelectedTipo(String selectedTipo) {
        this.selectedTipo = selectedTipo;
    }
    
}
