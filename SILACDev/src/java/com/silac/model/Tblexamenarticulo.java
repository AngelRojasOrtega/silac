package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA



/**
 * Tblexamenarticulo generated by hbm2java
 */
public class Tblexamenarticulo  implements java.io.Serializable {


     private Integer idExamenarticulo;
     private Tblarticulo tblarticulo;
     private Tblexamen tblexamen;
     private double cantidad;

    public Tblexamenarticulo() {
    }

    public Tblexamenarticulo(Tblarticulo tblarticulo, Tblexamen tblexamen, double cantidad) {
       this.tblarticulo = tblarticulo;
       this.tblexamen = tblexamen;
       this.cantidad = cantidad;
    }
   
    public Integer getIdExamenarticulo() {
        return this.idExamenarticulo;
    }
    
    public void setIdExamenarticulo(Integer idExamenarticulo) {
        this.idExamenarticulo = idExamenarticulo;
    }
    public Tblarticulo getTblarticulo() {
        return this.tblarticulo;
    }
    
    public void setTblarticulo(Tblarticulo tblarticulo) {
        this.tblarticulo = tblarticulo;
    }
    public Tblexamen getTblexamen() {
        return this.tblexamen;
    }
    
    public void setTblexamen(Tblexamen tblexamen) {
        this.tblexamen = tblexamen;
    }
    public double getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }




}


