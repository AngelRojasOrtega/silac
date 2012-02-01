package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Tblservicio generated by hbm2java
 */
public class Tblservicio  implements java.io.Serializable {


     private Short idServicio;
     private Tbltiposervicio tbltiposervicio;
     private String codigoServicio;
     private String nombreServicio;
     private Set tblordens = new HashSet(0);
     private Set tblclinicaexternas = new HashSet(0);

    public Tblservicio() {
    }

	
    public Tblservicio(Tbltiposervicio tbltiposervicio, String codigoServicio, String nombreServicio) {
        this.tbltiposervicio = tbltiposervicio;
        this.codigoServicio = codigoServicio;
        this.nombreServicio = nombreServicio;
    }
    public Tblservicio(Tbltiposervicio tbltiposervicio, String codigoServicio, String nombreServicio, Set tblordens, Set tblclinicaexternas) {
       this.tbltiposervicio = tbltiposervicio;
       this.codigoServicio = codigoServicio;
       this.nombreServicio = nombreServicio;
       this.tblordens = tblordens;
       this.tblclinicaexternas = tblclinicaexternas;
    }
   
    public Short getIdServicio() {
        return this.idServicio;
    }
    
    public void setIdServicio(Short idServicio) {
        this.idServicio = idServicio;
    }
    public Tbltiposervicio getTbltiposervicio() {
        return this.tbltiposervicio;
    }
    
    public void setTbltiposervicio(Tbltiposervicio tbltiposervicio) {
        this.tbltiposervicio = tbltiposervicio;
    }
    public String getCodigoServicio() {
        return this.codigoServicio;
    }
    
    public void setCodigoServicio(String codigoServicio) {
        this.codigoServicio = codigoServicio;
    }
    public String getNombreServicio() {
        return this.nombreServicio;
    }
    
    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }
    public Set getTblordens() {
        return this.tblordens;
    }
    
    public void setTblordens(Set tblordens) {
        this.tblordens = tblordens;
    }
    public Set getTblclinicaexternas() {
        return this.tblclinicaexternas;
    }
    
    public void setTblclinicaexternas(Set tblclinicaexternas) {
        this.tblclinicaexternas = tblclinicaexternas;
    }




}

