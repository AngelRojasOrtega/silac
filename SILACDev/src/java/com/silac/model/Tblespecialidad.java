package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Tblespecialidad generated by hbm2java
 */
public class Tblespecialidad  implements java.io.Serializable {


     private Short idEspecialidad;
     private String codigoEspecialidad;
     private String nombreEspecialidad;
     private Set tblordens = new HashSet(0);

    public Tblespecialidad() {
    }

	
    public Tblespecialidad(String codigoEspecialidad, String nombreEspecialidad) {
        this.codigoEspecialidad = codigoEspecialidad;
        this.nombreEspecialidad = nombreEspecialidad;
    }
    public Tblespecialidad(String codigoEspecialidad, String nombreEspecialidad, Set tblordens) {
       this.codigoEspecialidad = codigoEspecialidad;
       this.nombreEspecialidad = nombreEspecialidad;
       this.tblordens = tblordens;
    }
   
    public Short getIdEspecialidad() {
        return this.idEspecialidad;
    }
    
    public void setIdEspecialidad(Short idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }
    public String getCodigoEspecialidad() {
        return this.codigoEspecialidad;
    }
    
    public void setCodigoEspecialidad(String codigoEspecialidad) {
        this.codigoEspecialidad = codigoEspecialidad;
    }
    public String getNombreEspecialidad() {
        return this.nombreEspecialidad;
    }
    
    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }
    public Set getTblordens() {
        return this.tblordens;
    }
    
    public void setTblordens(Set tblordens) {
        this.tblordens = tblordens;
    }




}

