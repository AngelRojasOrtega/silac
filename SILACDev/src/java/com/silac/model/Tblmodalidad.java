package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Tblmodalidad generated by hbm2java
 */
public class Tblmodalidad  implements java.io.Serializable {


     private Short idModalidad;
     private String nombreModalidad;
     private Set tbltiposervicios = new HashSet(0);

    public Tblmodalidad() {
    }

	
    public Tblmodalidad(String nombreModalidad) {
        this.nombreModalidad = nombreModalidad;
    }
    public Tblmodalidad(String nombreModalidad, Set tbltiposervicios) {
       this.nombreModalidad = nombreModalidad;
       this.tbltiposervicios = tbltiposervicios;
    }
   
    public Short getIdModalidad() {
        return this.idModalidad;
    }
    
    public void setIdModalidad(Short idModalidad) {
        this.idModalidad = idModalidad;
    }
    public String getNombreModalidad() {
        return this.nombreModalidad;
    }
    
    public void setNombreModalidad(String nombreModalidad) {
        this.nombreModalidad = nombreModalidad;
    }
    public Set getTbltiposervicios() {
        return this.tbltiposervicios;
    }
    
    public void setTbltiposervicios(Set tbltiposervicios) {
        this.tbltiposervicios = tbltiposervicios;
    }




}


