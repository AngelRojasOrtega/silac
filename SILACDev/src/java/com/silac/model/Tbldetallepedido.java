package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA



/**
 * Tbldetallepedido generated by hbm2java
 */
public class Tbldetallepedido  implements java.io.Serializable {


     private Integer idDetallePedido;
     private Tblarticulo tblarticulo;
     private Tblpedido tblpedido;
     private double consumoReal;
     private double cantidadSolicitada;

    public Tbldetallepedido() {
    }

    public Tbldetallepedido(Tblarticulo tblarticulo, Tblpedido tblpedido, double consumoReal, double cantidadSolicitada) {
       this.tblarticulo = tblarticulo;
       this.tblpedido = tblpedido;
       this.consumoReal = consumoReal;
       this.cantidadSolicitada = cantidadSolicitada;
    }
   
    public Integer getIdDetallePedido() {
        return this.idDetallePedido;
    }
    
    public void setIdDetallePedido(Integer idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }
    public Tblarticulo getTblarticulo() {
        return this.tblarticulo;
    }
    
    public void setTblarticulo(Tblarticulo tblarticulo) {
        this.tblarticulo = tblarticulo;
    }
    public Tblpedido getTblpedido() {
        return this.tblpedido;
    }
    
    public void setTblpedido(Tblpedido tblpedido) {
        this.tblpedido = tblpedido;
    }
    public double getConsumoReal() {
        return this.consumoReal;
    }
    
    public void setConsumoReal(double consumoReal) {
        this.consumoReal = consumoReal;
    }
    public double getCantidadSolicitada() {
        return this.cantidadSolicitada;
    }
    
    public void setCantidadSolicitada(double cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }




}

