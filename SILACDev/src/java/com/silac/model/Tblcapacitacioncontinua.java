package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Tblcapacitacioncontinua generated by hbm2java
 */
public class Tblcapacitacioncontinua  implements java.io.Serializable {


     private Long idCapacitacion;
     private Tblinstitucion tblinstitucion;
     private String nombreEvento;
     private String lugarEvento;
     private String direccionEvento;
     private Date fechaInicio;
     private Date fechaFinal;
     private Set tblempleadoCapacitacions = new HashSet(0);

    public Tblcapacitacioncontinua() {
    }

	
    public Tblcapacitacioncontinua(Tblinstitucion tblinstitucion, String nombreEvento, String lugarEvento, Date fechaInicio, Date fechaFinal) {
        this.tblinstitucion = tblinstitucion;
        this.nombreEvento = nombreEvento;
        this.lugarEvento = lugarEvento;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
    }
    public Tblcapacitacioncontinua(Tblinstitucion tblinstitucion, String nombreEvento, String lugarEvento, String direccionEvento, Date fechaInicio, Date fechaFinal, Set tblempleadoCapacitacions) {
       this.tblinstitucion = tblinstitucion;
       this.nombreEvento = nombreEvento;
       this.lugarEvento = lugarEvento;
       this.direccionEvento = direccionEvento;
       this.fechaInicio = fechaInicio;
       this.fechaFinal = fechaFinal;
       this.tblempleadoCapacitacions = tblempleadoCapacitacions;
    }
   
    public Long getIdCapacitacion() {
        return this.idCapacitacion;
    }
    
    public void setIdCapacitacion(Long idCapacitacion) {
        this.idCapacitacion = idCapacitacion;
    }
    public Tblinstitucion getTblinstitucion() {
        return this.tblinstitucion;
    }
    
    public void setTblinstitucion(Tblinstitucion tblinstitucion) {
        this.tblinstitucion = tblinstitucion;
    }
    public String getNombreEvento() {
        return this.nombreEvento;
    }
    
    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }
    public String getLugarEvento() {
        return this.lugarEvento;
    }
    
    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }
    public String getDireccionEvento() {
        return this.direccionEvento;
    }
    
    public void setDireccionEvento(String direccionEvento) {
        this.direccionEvento = direccionEvento;
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
    public Set getTblempleadoCapacitacions() {
        return this.tblempleadoCapacitacions;
    }
    
    public void setTblempleadoCapacitacions(Set tblempleadoCapacitacions) {
        this.tblempleadoCapacitacions = tblempleadoCapacitacions;
    }




}


