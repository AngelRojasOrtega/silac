package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Tblempleado generated by hbm2java
 */
public class Tblempleado  implements java.io.Serializable {


     private short codigoMarcacion;
     private Tblpersona tblpersona;
     private Date fechaIngreso;
     private String nombreAfp;
     private Long nup;
     private Long isss;
     private String codigoProfesional;
     private Set tbljefes = new HashSet(0);
     private Set tblpermisos = new HashSet(0);
     private Set tblresultados = new HashSet(0);
     private Set tblpuestoempleados = new HashSet(0);
     private Set tblexamenOrdens = new HashSet(0);
     private Set tblusuarios = new HashSet(0);
     private Set tblplanificaciontrabajos = new HashSet(0);
     private Set tblempleadoCapacitacions = new HashSet(0);
     private Set tblevaluaciondonantes = new HashSet(0);
     private Set tbltransfusions = new HashSet(0);
     private Set tblordens = new HashSet(0);

    public Tblempleado() {
    }

	
    public Tblempleado(short codigoMarcacion, Tblpersona tblpersona, Date fechaIngreso) {
        this.codigoMarcacion = codigoMarcacion;
        this.tblpersona = tblpersona;
        this.fechaIngreso = fechaIngreso;
    }
    public Tblempleado(short codigoMarcacion, Tblpersona tblpersona, Date fechaIngreso, String nombreAfp, Long nup, Long isss, String codigoProfesional, Set tbljefes, Set tblpermisos, Set tblresultados, Set tblpuestoempleados, Set tblexamenOrdens, Set tblusuarios, Set tblplanificaciontrabajos, Set tblempleadoCapacitacions, Set tblevaluaciondonantes, Set tbltransfusions, Set tblordens) {
       this.codigoMarcacion = codigoMarcacion;
       this.tblpersona = tblpersona;
       this.fechaIngreso = fechaIngreso;
       this.nombreAfp = nombreAfp;
       this.nup = nup;
       this.isss = isss;
       this.codigoProfesional = codigoProfesional;
       this.tbljefes = tbljefes;
       this.tblpermisos = tblpermisos;
       this.tblresultados = tblresultados;
       this.tblpuestoempleados = tblpuestoempleados;
       this.tblexamenOrdens = tblexamenOrdens;
       this.tblusuarios = tblusuarios;
       this.tblplanificaciontrabajos = tblplanificaciontrabajos;
       this.tblempleadoCapacitacions = tblempleadoCapacitacions;
       this.tblevaluaciondonantes = tblevaluaciondonantes;
       this.tbltransfusions = tbltransfusions;
       this.tblordens = tblordens;
    }
   
    public short getCodigoMarcacion() {
        return this.codigoMarcacion;
    }
    
    public void setCodigoMarcacion(short codigoMarcacion) {
        this.codigoMarcacion = codigoMarcacion;
    }
    public Tblpersona getTblpersona() {
        return this.tblpersona;
    }
    
    public void setTblpersona(Tblpersona tblpersona) {
        this.tblpersona = tblpersona;
    }
    public Date getFechaIngreso() {
        return this.fechaIngreso;
    }
    
    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
    public String getNombreAfp() {
        return this.nombreAfp;
    }
    
    public void setNombreAfp(String nombreAfp) {
        this.nombreAfp = nombreAfp;
    }
    public Long getNup() {
        return this.nup;
    }
    
    public void setNup(Long nup) {
        this.nup = nup;
    }
    public Long getIsss() {
        return this.isss;
    }
    
    public void setIsss(Long isss) {
        this.isss = isss;
    }
    public String getCodigoProfesional() {
        return this.codigoProfesional;
    }
    
    public void setCodigoProfesional(String codigoProfesional) {
        this.codigoProfesional = codigoProfesional;
    }
    public Set getTbljefes() {
        return this.tbljefes;
    }
    
    public void setTbljefes(Set tbljefes) {
        this.tbljefes = tbljefes;
    }
    public Set getTblpermisos() {
        return this.tblpermisos;
    }
    
    public void setTblpermisos(Set tblpermisos) {
        this.tblpermisos = tblpermisos;
    }
    public Set getTblresultados() {
        return this.tblresultados;
    }
    
    public void setTblresultados(Set tblresultados) {
        this.tblresultados = tblresultados;
    }
    public Set getTblpuestoempleados() {
        return this.tblpuestoempleados;
    }
    
    public void setTblpuestoempleados(Set tblpuestoempleados) {
        this.tblpuestoempleados = tblpuestoempleados;
    }
    public Set getTblexamenOrdens() {
        return this.tblexamenOrdens;
    }
    
    public void setTblexamenOrdens(Set tblexamenOrdens) {
        this.tblexamenOrdens = tblexamenOrdens;
    }
    public Set getTblusuarios() {
        return this.tblusuarios;
    }
    
    public void setTblusuarios(Set tblusuarios) {
        this.tblusuarios = tblusuarios;
    }
    public Set getTblplanificaciontrabajos() {
        return this.tblplanificaciontrabajos;
    }
    
    public void setTblplanificaciontrabajos(Set tblplanificaciontrabajos) {
        this.tblplanificaciontrabajos = tblplanificaciontrabajos;
    }
    public Set getTblempleadoCapacitacions() {
        return this.tblempleadoCapacitacions;
    }
    
    public void setTblempleadoCapacitacions(Set tblempleadoCapacitacions) {
        this.tblempleadoCapacitacions = tblempleadoCapacitacions;
    }
    public Set getTblevaluaciondonantes() {
        return this.tblevaluaciondonantes;
    }
    
    public void setTblevaluaciondonantes(Set tblevaluaciondonantes) {
        this.tblevaluaciondonantes = tblevaluaciondonantes;
    }
    public Set getTbltransfusions() {
        return this.tbltransfusions;
    }
    
    public void setTbltransfusions(Set tbltransfusions) {
        this.tbltransfusions = tbltransfusions;
    }
    public Set getTblordens() {
        return this.tblordens;
    }
    
    public void setTblordens(Set tblordens) {
        this.tblordens = tblordens;
    }




}


