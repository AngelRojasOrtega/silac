package com.silac.model;
// Generated 01-29-2012 03:43:05 PM by Hibernate Tools 3.2.1.GA


import java.util.HashSet;
import java.util.Set;

/**
 * Tblcatalogo generated by hbm2java
 */
public class Tblcatalogo  implements java.io.Serializable {


     private Short idCatalogo;
     private String categoria;
     private Set tblarticulos = new HashSet(0);

    public Tblcatalogo() {
    }

	
    public Tblcatalogo(String categoria) {
        this.categoria = categoria;
    }
    public Tblcatalogo(String categoria, Set tblarticulos) {
       this.categoria = categoria;
       this.tblarticulos = tblarticulos;
    }
   
    public Short getIdCatalogo() {
        return this.idCatalogo;
    }
    
    public void setIdCatalogo(Short idCatalogo) {
        this.idCatalogo = idCatalogo;
    }
    public String getCategoria() {
        return this.categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Set getTblarticulos() {
        return this.tblarticulos;
    }
    
    public void setTblarticulos(Set tblarticulos) {
        this.tblarticulos = tblarticulos;
    }




}


