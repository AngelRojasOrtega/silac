package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * TblcuestionarioEvaluacion generated by hbm2java
 */
public class TblcuestionarioEvaluacion  implements java.io.Serializable {


     private Integer idCuestionario;
     private String pregunta;
     private int numeroPregunta;
     private boolean rechazarDonante;
     private boolean donanteMujer;
     private Set tblrespuestaCuestionarios = new HashSet(0);
     private Set tblcriterios = new HashSet(0);
     private Set tbldetalleCuestionarios = new HashSet(0);

    public TblcuestionarioEvaluacion() {
    }

	
    public TblcuestionarioEvaluacion(String pregunta, int numeroPregunta, boolean rechazarDonante, boolean donanteMujer) {
        this.pregunta = pregunta;
        this.numeroPregunta = numeroPregunta;
        this.rechazarDonante = rechazarDonante;
        this.donanteMujer = donanteMujer;
    }
    public TblcuestionarioEvaluacion(String pregunta, int numeroPregunta, boolean rechazarDonante, boolean donanteMujer, Set tblrespuestaCuestionarios, Set tblcriterios, Set tbldetalleCuestionarios) {
       this.pregunta = pregunta;
       this.numeroPregunta = numeroPregunta;
       this.rechazarDonante = rechazarDonante;
       this.donanteMujer = donanteMujer;
       this.tblrespuestaCuestionarios = tblrespuestaCuestionarios;
       this.tblcriterios = tblcriterios;
       this.tbldetalleCuestionarios = tbldetalleCuestionarios;
    }
   
    public Integer getIdCuestionario() {
        return this.idCuestionario;
    }
    
    public void setIdCuestionario(Integer idCuestionario) {
        this.idCuestionario = idCuestionario;
    }
    public String getPregunta() {
        return this.pregunta;
    }
    
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
    public int getNumeroPregunta() {
        return this.numeroPregunta;
    }
    
    public void setNumeroPregunta(int numeroPregunta) {
        this.numeroPregunta = numeroPregunta;
    }
    public boolean isRechazarDonante() {
        return this.rechazarDonante;
    }
    
    public void setRechazarDonante(boolean rechazarDonante) {
        this.rechazarDonante = rechazarDonante;
    }
    public boolean isDonanteMujer() {
        return this.donanteMujer;
    }
    
    public void setDonanteMujer(boolean donanteMujer) {
        this.donanteMujer = donanteMujer;
    }
    public Set getTblrespuestaCuestionarios() {
        return this.tblrespuestaCuestionarios;
    }
    
    public void setTblrespuestaCuestionarios(Set tblrespuestaCuestionarios) {
        this.tblrespuestaCuestionarios = tblrespuestaCuestionarios;
    }
    public Set getTblcriterios() {
        return this.tblcriterios;
    }
    
    public void setTblcriterios(Set tblcriterios) {
        this.tblcriterios = tblcriterios;
    }
    public Set getTbldetalleCuestionarios() {
        return this.tbldetalleCuestionarios;
    }
    
    public void setTbldetalleCuestionarios(Set tbldetalleCuestionarios) {
        this.tbldetalleCuestionarios = tbldetalleCuestionarios;
    }




}


