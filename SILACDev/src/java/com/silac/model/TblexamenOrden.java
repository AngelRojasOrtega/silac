package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * TblexamenOrden generated by hbm2java
 */
public class TblexamenOrden  implements java.io.Serializable {


     private Long idExamenOrden;
     private Tblexamen tblexamen;
     private Tblorden tblorden;
     private Tblempleado tblempleado;
     private long numeroControl;
     private String estado;
     private Date fechaRegistro;
     private Set tblincidencias = new HashSet(0);
     private Set tblentregaresultados = new HashSet(0);
     private Set tblresultados = new HashSet(0);
     private Set tblproduccions = new HashSet(0);

    public TblexamenOrden() {
    }

	
    public TblexamenOrden(Tblexamen tblexamen, Tblorden tblorden, long numeroControl) {
        this.tblexamen = tblexamen;
        this.tblorden = tblorden;
        this.numeroControl = numeroControl;
    }
    public TblexamenOrden(Tblexamen tblexamen, Tblorden tblorden, Tblempleado tblempleado, long numeroControl, String estado, Date fechaRegistro, Set tblincidencias, Set tblentregaresultados, Set tblresultados, Set tblproduccions) {
       this.tblexamen = tblexamen;
       this.tblorden = tblorden;
       this.tblempleado = tblempleado;
       this.numeroControl = numeroControl;
       this.estado = estado;
       this.fechaRegistro = fechaRegistro;
       this.tblincidencias = tblincidencias;
       this.tblentregaresultados = tblentregaresultados;
       this.tblresultados = tblresultados;
       this.tblproduccions = tblproduccions;
    }
   
    public Long getIdExamenOrden() {
        return this.idExamenOrden;
    }
    
    public void setIdExamenOrden(Long idExamenOrden) {
        this.idExamenOrden = idExamenOrden;
    }
    public Tblexamen getTblexamen() {
        return this.tblexamen;
    }
    
    public void setTblexamen(Tblexamen tblexamen) {
        this.tblexamen = tblexamen;
    }
    public Tblorden getTblorden() {
        return this.tblorden;
    }
    
    public void setTblorden(Tblorden tblorden) {
        this.tblorden = tblorden;
    }
    public Tblempleado getTblempleado() {
        return this.tblempleado;
    }
    
    public void setTblempleado(Tblempleado tblempleado) {
        this.tblempleado = tblempleado;
    }
    public long getNumeroControl() {
        return this.numeroControl;
    }
    
    public void setNumeroControl(long numeroControl) {
        this.numeroControl = numeroControl;
    }
    public String getEstado() {
        return this.estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Date getFechaRegistro() {
        return this.fechaRegistro;
    }
    
    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    public Set getTblincidencias() {
        return this.tblincidencias;
    }
    
    public void setTblincidencias(Set tblincidencias) {
        this.tblincidencias = tblincidencias;
    }
    public Set getTblentregaresultados() {
        return this.tblentregaresultados;
    }
    
    public void setTblentregaresultados(Set tblentregaresultados) {
        this.tblentregaresultados = tblentregaresultados;
    }
    public Set getTblresultados() {
        return this.tblresultados;
    }
    
    public void setTblresultados(Set tblresultados) {
        this.tblresultados = tblresultados;
    }
    public Set getTblproduccions() {
        return this.tblproduccions;
    }
    
    public void setTblproduccions(Set tblproduccions) {
        this.tblproduccions = tblproduccions;
    }




}


