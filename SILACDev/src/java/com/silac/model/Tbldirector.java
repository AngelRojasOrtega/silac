package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Tbldirector generated by hbm2java
 */
public class Tbldirector  implements java.io.Serializable {


     private Integer idDirector;
     private Tblpersona tblpersona;
     private Tblestablecimiento tblestablecimiento;
     private Date fechaInicio;
     private Date fechaFinal;
     private boolean estado;

    public Tbldirector() {
    }

	
    public Tbldirector(Tblpersona tblpersona, Tblestablecimiento tblestablecimiento, Date fechaInicio, boolean estado) {
        this.tblpersona = tblpersona;
        this.tblestablecimiento = tblestablecimiento;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }
    public Tbldirector(Tblpersona tblpersona, Tblestablecimiento tblestablecimiento, Date fechaInicio, Date fechaFinal, boolean estado) {
       this.tblpersona = tblpersona;
       this.tblestablecimiento = tblestablecimiento;
       this.fechaInicio = fechaInicio;
       this.fechaFinal = fechaFinal;
       this.estado = estado;
    }
   
    public Integer getIdDirector() {
        return this.idDirector;
    }
    
    public void setIdDirector(Integer idDirector) {
        this.idDirector = idDirector;
    }
    public Tblpersona getTblpersona() {
        return this.tblpersona;
    }
    
    public void setTblpersona(Tblpersona tblpersona) {
        this.tblpersona = tblpersona;
    }
    public Tblestablecimiento getTblestablecimiento() {
        return this.tblestablecimiento;
    }
    
    public void setTblestablecimiento(Tblestablecimiento tblestablecimiento) {
        this.tblestablecimiento = tblestablecimiento;
    }
    public Date getFechaInicio() {
        return this.fechaInicio;
    }
    
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public Date getFechaFinal() {
        return this.fechaFinal;
    }
    
    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    public boolean isEstado() {
        return this.estado;
    }
    
    public void setEstado(boolean estado) {
        this.estado = estado;
    }




}


