package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;

/**
 * Tbljefe generated by hbm2java
 */
public class Tbljefe  implements java.io.Serializable {


     private Integer idJefe;
     private Tblempleado tblempleado;
     private Tblestablecimiento tblestablecimiento;
     private Date fechaInicio;
     private Date fechaFinal;
     private boolean estado;

    public Tbljefe() {
    }

    public Tbljefe(Tblempleado tblempleado, Tblestablecimiento tblestablecimiento, Date fechaInicio, Date fechaFinal, boolean estado) {
       this.tblempleado = tblempleado;
       this.tblestablecimiento = tblestablecimiento;
       this.fechaInicio = fechaInicio;
       this.fechaFinal = fechaFinal;
       this.estado = estado;
    }
   
    public Integer getIdJefe() {
        return this.idJefe;
    }
    
    public void setIdJefe(Integer idJefe) {
        this.idJefe = idJefe;
    }
    public Tblempleado getTblempleado() {
        return this.tblempleado;
    }
    
    public void setTblempleado(Tblempleado tblempleado) {
        this.tblempleado = tblempleado;
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


