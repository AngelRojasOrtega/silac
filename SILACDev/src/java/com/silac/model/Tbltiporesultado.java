package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Tbltiporesultado generated by hbm2java
 */
public class Tbltiporesultado  implements java.io.Serializable {


     private Short idTipoResultado;
     private short tipoResultado;
     private String nombreResultado;
     private Set tblresultados = new HashSet(0);

    public Tbltiporesultado() {
    }

	
    public Tbltiporesultado(short tipoResultado, String nombreResultado) {
        this.tipoResultado = tipoResultado;
        this.nombreResultado = nombreResultado;
    }
    public Tbltiporesultado(short tipoResultado, String nombreResultado, Set tblresultados) {
       this.tipoResultado = tipoResultado;
       this.nombreResultado = nombreResultado;
       this.tblresultados = tblresultados;
    }
   
    public Short getIdTipoResultado() {
        return this.idTipoResultado;
    }
    
    public void setIdTipoResultado(Short idTipoResultado) {
        this.idTipoResultado = idTipoResultado;
    }
    public short getTipoResultado() {
        return this.tipoResultado;
    }
    
    public void setTipoResultado(short tipoResultado) {
        this.tipoResultado = tipoResultado;
    }
    public String getNombreResultado() {
        return this.nombreResultado;
    }
    
    public void setNombreResultado(String nombreResultado) {
        this.nombreResultado = nombreResultado;
    }
    public Set getTblresultados() {
        return this.tblresultados;
    }
    
    public void setTblresultados(Set tblresultados) {
        this.tblresultados = tblresultados;
    }




}


