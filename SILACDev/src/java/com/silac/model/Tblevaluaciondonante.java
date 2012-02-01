package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Tblevaluaciondonante generated by hbm2java
 */
public class Tblevaluaciondonante  implements java.io.Serializable {


     private Long idEvaluacionDonante;
     private Tblempleado tblempleado;
     private Tbldonante tbldonante;
     private Tblpaciente tblpaciente;
     private Date fechaEvaluacion;
     private String lugarReferencia;
     private String observaciones;
     private String resultado;
     private Set tblevaluacionfisicas = new HashSet(0);
     private Set tblantecedentesDonantes = new HashSet(0);
     private Set tblrespuestaCuestionarios = new HashSet(0);

    public Tblevaluaciondonante() {
    }

	
    public Tblevaluaciondonante(Tblempleado tblempleado, Tbldonante tbldonante, Tblpaciente tblpaciente, Date fechaEvaluacion, String resultado) {
        this.tblempleado = tblempleado;
        this.tbldonante = tbldonante;
        this.tblpaciente = tblpaciente;
        this.fechaEvaluacion = fechaEvaluacion;
        this.resultado = resultado;
    }
    public Tblevaluaciondonante(Tblempleado tblempleado, Tbldonante tbldonante, Tblpaciente tblpaciente, Date fechaEvaluacion, String lugarReferencia, String observaciones, String resultado, Set tblevaluacionfisicas, Set tblantecedentesDonantes, Set tblrespuestaCuestionarios) {
       this.tblempleado = tblempleado;
       this.tbldonante = tbldonante;
       this.tblpaciente = tblpaciente;
       this.fechaEvaluacion = fechaEvaluacion;
       this.lugarReferencia = lugarReferencia;
       this.observaciones = observaciones;
       this.resultado = resultado;
       this.tblevaluacionfisicas = tblevaluacionfisicas;
       this.tblantecedentesDonantes = tblantecedentesDonantes;
       this.tblrespuestaCuestionarios = tblrespuestaCuestionarios;
    }
   
    public Long getIdEvaluacionDonante() {
        return this.idEvaluacionDonante;
    }
    
    public void setIdEvaluacionDonante(Long idEvaluacionDonante) {
        this.idEvaluacionDonante = idEvaluacionDonante;
    }
    public Tblempleado getTblempleado() {
        return this.tblempleado;
    }
    
    public void setTblempleado(Tblempleado tblempleado) {
        this.tblempleado = tblempleado;
    }
    public Tbldonante getTbldonante() {
        return this.tbldonante;
    }
    
    public void setTbldonante(Tbldonante tbldonante) {
        this.tbldonante = tbldonante;
    }
    public Tblpaciente getTblpaciente() {
        return this.tblpaciente;
    }
    
    public void setTblpaciente(Tblpaciente tblpaciente) {
        this.tblpaciente = tblpaciente;
    }
    public Date getFechaEvaluacion() {
        return this.fechaEvaluacion;
    }
    
    public void setFechaEvaluacion(Date fechaEvaluacion) {
        this.fechaEvaluacion = fechaEvaluacion;
    }
    public String getLugarReferencia() {
        return this.lugarReferencia;
    }
    
    public void setLugarReferencia(String lugarReferencia) {
        this.lugarReferencia = lugarReferencia;
    }
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public String getResultado() {
        return this.resultado;
    }
    
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
    public Set getTblevaluacionfisicas() {
        return this.tblevaluacionfisicas;
    }
    
    public void setTblevaluacionfisicas(Set tblevaluacionfisicas) {
        this.tblevaluacionfisicas = tblevaluacionfisicas;
    }
    public Set getTblantecedentesDonantes() {
        return this.tblantecedentesDonantes;
    }
    
    public void setTblantecedentesDonantes(Set tblantecedentesDonantes) {
        this.tblantecedentesDonantes = tblantecedentesDonantes;
    }
    public Set getTblrespuestaCuestionarios() {
        return this.tblrespuestaCuestionarios;
    }
    
    public void setTblrespuestaCuestionarios(Set tblrespuestaCuestionarios) {
        this.tblrespuestaCuestionarios = tblrespuestaCuestionarios;
    }




}


