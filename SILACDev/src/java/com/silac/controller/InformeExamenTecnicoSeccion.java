/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author siman
 */
@ManagedBean(name="informeExamenTecnicoSeccionBean")
@ViewScoped
public class InformeExamenTecnicoSeccion implements Serializable {
    private Short selectedSeccion;
    private Date selectedFecha;
    private String selectedTipo;
    private String url;
    /** Creates a new instance of InformeExamenTecnicoSeccion */
    public InformeExamenTecnicoSeccion() {
        this.init();
    }
    
    private void init(){
        
        this.selectedSeccion = null;
        this.selectedFecha = null;        
        this.selectedTipo = null;
        this.url = null;
        
    }
    
    public void imprimir() {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.url = contextPath + "/faces/createReportServlet?fileName=";
        String fileName = "informeExamenTecnicoSeccion";
        String tipo = this.selectedTipo;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
                String sFecha = sdf.format(this.selectedFecha);
        
        String params = "&tipo=" + tipo + "&seccion=" + selectedSeccion + "&fecha=" + sFecha;
        this.url = this.url + fileName + params;
    }

    public Date getSelectedFecha() {
        return selectedFecha;
    }

    public void setSelectedFecha(Date selectedFecha) {
        this.selectedFecha = selectedFecha;
    }

    public Short getSelectedSeccion() {
        return selectedSeccion;
    }

    public void setSelectedSeccion(Short selectedSeccion) {
        this.selectedSeccion = selectedSeccion;
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


