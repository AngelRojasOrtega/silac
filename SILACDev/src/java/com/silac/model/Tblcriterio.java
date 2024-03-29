package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Tblcriterio generated by hbm2java
 */
public class Tblcriterio  implements java.io.Serializable {


     private Integer idCriterio;
     private TblcuestionarioEvaluacion tblcuestionarioEvaluacion;
     private String textoCriterio;
     private int tiempoExclusion;
     private String periodoExclusion;
     private String observaciones;
     private Set tblrespuestaCriterios = new HashSet(0);

    public Tblcriterio() {
    }

	
    public Tblcriterio(TblcuestionarioEvaluacion tblcuestionarioEvaluacion, String textoCriterio, int tiempoExclusion, String periodoExclusion) {
        this.tblcuestionarioEvaluacion = tblcuestionarioEvaluacion;
        this.textoCriterio = textoCriterio;
        this.tiempoExclusion = tiempoExclusion;
        this.periodoExclusion = periodoExclusion;
    }
    public Tblcriterio(TblcuestionarioEvaluacion tblcuestionarioEvaluacion, String textoCriterio, int tiempoExclusion, String periodoExclusion, String observaciones, Set tblrespuestaCriterios) {
       this.tblcuestionarioEvaluacion = tblcuestionarioEvaluacion;
       this.textoCriterio = textoCriterio;
       this.tiempoExclusion = tiempoExclusion;
       this.periodoExclusion = periodoExclusion;
       this.observaciones = observaciones;
       this.tblrespuestaCriterios = tblrespuestaCriterios;
    }
   
    public Integer getIdCriterio() {
        return this.idCriterio;
    }
    
    public void setIdCriterio(Integer idCriterio) {
        this.idCriterio = idCriterio;
    }
    public TblcuestionarioEvaluacion getTblcuestionarioEvaluacion() {
        return this.tblcuestionarioEvaluacion;
    }
    
    public void setTblcuestionarioEvaluacion(TblcuestionarioEvaluacion tblcuestionarioEvaluacion) {
        this.tblcuestionarioEvaluacion = tblcuestionarioEvaluacion;
    }
    public String getTextoCriterio() {
        return this.textoCriterio;
    }
    
    public void setTextoCriterio(String textoCriterio) {
        this.textoCriterio = textoCriterio;
    }
    public int getTiempoExclusion() {
        return this.tiempoExclusion;
    }
    
    public void setTiempoExclusion(int tiempoExclusion) {
        this.tiempoExclusion = tiempoExclusion;
    }
    public String getPeriodoExclusion() {
        return this.periodoExclusion;
    }
    
    public void setPeriodoExclusion(String periodoExclusion) {
        this.periodoExclusion = periodoExclusion;
    }
    public String getObservaciones() {
        return this.observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Set getTblrespuestaCriterios() {
        return this.tblrespuestaCriterios;
    }
    
    public void setTblrespuestaCriterios(Set tblrespuestaCriterios) {
        this.tblrespuestaCriterios = tblrespuestaCriterios;
    }




}


