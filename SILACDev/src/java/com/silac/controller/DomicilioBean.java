/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tbldepartamento;
import com.silac.model.Tblmunicipio;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "domicilioBean")
@ViewScoped
public class DomicilioBean implements Serializable {

    /** Creates a new instance of DomicilioBean */
    public DomicilioBean() {
    }

    /**
     * Carga la lista de departamentos que seran mostrados en un cuadro de lista desplegable
     * @return 
     */
    public ArrayList<SelectItem> cargarListaDeptos() {
        ArrayList<SelectItem> listaDeptos = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        Criteria cDeptos = session.createCriteria(Tbldepartamento.class);
        Iterator iter = cDeptos.list().iterator();
        while (iter.hasNext()) {
            Tbldepartamento d = (Tbldepartamento) iter.next();
            listaDeptos.add(new SelectItem(d.getIdDepartamento(), d.getNomDepartamento(), ""));
        }
        session.close();
        return listaDeptos;
    }

    /**
     * Carga la lista de municipios que seran mostrados en un cuadro de lista desplegable
     * @param idDepto
     * @return  
     */
    public ArrayList<SelectItem> cargarListaMunicipios(Short idDepto) {
        ArrayList<SelectItem> listaMunicipios = new ArrayList<SelectItem>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        if (idDepto != null) {
            Tbldepartamento depto = (Tbldepartamento) session.load(Tbldepartamento.class, idDepto);
            Iterator iter = depto.getTblmunicipios().iterator();
            while (iter.hasNext()) {
                Tblmunicipio m = (Tblmunicipio) iter.next();
                listaMunicipios.add(new SelectItem(m.getIdMunicipio(), m.getNomMunicipio(), ""));
            }
        }
        session.close();
        return listaMunicipios;
    }
}
