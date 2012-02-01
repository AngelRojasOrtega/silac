/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblpersona;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.event.SelectEvent;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "personasBean")
@ViewScoped
public class PersonaBean implements Serializable {

    private Tblpersona persona;
    private String buscarNombre;
    private String buscarDocId;
    private String buscarExpediente;

    /** Creates a new instance of PersonaBean */
    public PersonaBean() {
        this.init();
    }

    private void init() {
        this.persona = new Tblpersona();
    }

    /*
     * Carga la lista de opciones pare optSexo
     */
    public ArrayList<SelectItem> cargarListaSexo() {
        ArrayList<SelectItem> listaSexo = new ArrayList<SelectItem>();
        listaSexo.add(new SelectItem("F", "Femenino", ""));
        listaSexo.add(new SelectItem("M", "Masculino", ""));
        return listaSexo;
    }

    public ArrayList<Object[]> buscar() {
        ArrayList<Object[]> listaPersonas = new ArrayList<Object[]>();
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT temp.nombre, temp.docId, temp.numExpediente, temp.idPersona, temp.exclusionPermanente, temp.exclusionDiferida "
                + "FROM ((SELECT CONCAT(IFNULL(p.primerNombre,''),' ',IFNULL(p.segundoNombre,''),' ',IFNULL(p.primerApellido,''),' ',IFNULL(p.segundoApellido,'')) "
                + "AS nombre, IFNULL(p.docId,'N/A') AS docId, IFNULL(pa.numExpediente,'N/A') as numExpediente, p.idPersona as idPersona,  "
                + "(SELECT d.exclusionPermanente FROM tbldonante d "
                + "WHERE d.idPersona = p.idPersona) as exclusionPermanente, (SELECT d.exclusionDiferida FROM tbldonante d "
                + "WHERE d.idPersona = p.idPersona) as exclusionDiferida FROM tblpersona p, tblpaciente pa "
                + "WHERE pa.idPersona = p.idPersona) "
                + "UNION "
                + "(SELECT CONCAT(IFNULL(p.primerNombre,''),' ',IFNULL(p.segundoNombre,''),' ',IFNULL(p.primerApellido,''),' ',IFNULL(p.segundoApellido,'')) "
                + "AS nombre, IFNULL(p.docId,'N/A') AS docId, 'N/A' AS numExpediente, p.idPersona as idPersona, "
                + "(SELECT d.exclusionPermanente FROM tbldonante d "
                + "WHERE d.idPersona = p.idPersona) as exclusionPermanente, (SELECT d.exclusionDiferida FROM tbldonante d "
                + "WHERE d.idPersona = p.idPersona) as exclusionDiferida FROM tblpersona p)) temp "
                + "WHERE temp.docId LIKE :docId AND temp.nombre LIKE :nombre AND temp.numExpediente LIKE :numExpediente AND temp.idPersona != 1 "
                + "GROUP BY temp.idPersona "
                + "LIMIT 200";
        Query query = session.createSQLQuery(sql);
        query.setParameter("nombre", '%' + this.buscarNombre + '%');
        query.setParameter("docId", '%' + this.buscarDocId + '%');
        query.setParameter("numExpediente", '%' + this.buscarExpediente + '%');
        listaPersonas = (ArrayList<Object[]>) query.list();
        session.close();
        return listaPersonas;
    }

    public Tblpersona getPersona() {
        return persona;
    }

    public Tblpersona cargarPersona(Long id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tblpersona p;
        Criteria cPersona = session.createCriteria(Tblpersona.class);
        cPersona.add(Restrictions.eq("idPersona", id));
        p = (Tblpersona) cPersona.uniqueResult();
        session.close();
        return p;
    }
    /*
     * Getters & Setters
     */
    public void setPersona(Tblpersona persona) {
        this.persona = persona;
    }

    public String getBuscarDocId() {
        return buscarDocId;
    }

    public void setBuscarDocId(String buscarDocId) {
        this.buscarDocId = buscarDocId;
    }

    public String getBuscarNombre() {
        return buscarNombre;
    }

    public void setBuscarNombre(String buscarNombre) {
        this.buscarNombre = buscarNombre;
    }

    public String getBuscarExpediente() {
        return buscarExpediente;
    }

    public void setBuscarExpediente(String buscarExpediente) {
        this.buscarExpediente = buscarExpediente;
    } 
}
