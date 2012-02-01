package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA



/**
 * Tblsalida generated by hbm2java
 */
public class Tblsalida  implements java.io.Serializable {


     private Long idSalida;
     private Tblentrada tblentrada;
     private Tblmovimiento tblmovimiento;
     private String destino;
     private double cantidad;
     private String motivo;

    public Tblsalida() {
    }

    public Tblsalida(Tblentrada tblentrada, Tblmovimiento tblmovimiento, String destino, double cantidad, String motivo) {
       this.tblentrada = tblentrada;
       this.tblmovimiento = tblmovimiento;
       this.destino = destino;
       this.cantidad = cantidad;
       this.motivo = motivo;
    }
   
    public Long getIdSalida() {
        return this.idSalida;
    }
    
    public void setIdSalida(Long idSalida) {
        this.idSalida = idSalida;
    }
    public Tblentrada getTblentrada() {
        return this.tblentrada;
    }
    
    public void setTblentrada(Tblentrada tblentrada) {
        this.tblentrada = tblentrada;
    }
    public Tblmovimiento getTblmovimiento() {
        return this.tblmovimiento;
    }
    
    public void setTblmovimiento(Tblmovimiento tblmovimiento) {
        this.tblmovimiento = tblmovimiento;
    }
    public String getDestino() {
        return this.destino;
    }
    
    public void setDestino(String destino) {
        this.destino = destino;
    }
    public double getCantidad() {
        return this.cantidad;
    }
    
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    public String getMotivo() {
        return this.motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }




}

