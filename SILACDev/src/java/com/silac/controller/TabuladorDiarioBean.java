/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author siman
 */
@ManagedBean(name="tabuladorDiarioBean")
@ViewScoped
public class TabuladorDiarioBean implements Serializable{
    private Short selectedModalidad;
    private Short selectedSeccion;
    private Date selectedFecha;
    private ArrayList listaTabulador = new ArrayList();
    private Date hoy = new Date();
    private String selectedTipo;
    private String url;

    /** Creates a new instance of TabuladorDiarioBean */
    public TabuladorDiarioBean() {
        this.init();
    }
    
    private void init(){
        this.selectedModalidad = null;
        this.selectedSeccion = null;
        this.selectedFecha = null;
        this.listaTabulador = new ArrayList();
        this.selectedTipo = null;
        
    }
    
    public void populateTabuladorDiario(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql ="SELECT eo.numeroControl, "  
+"pe.primerNombre, pe.segundoNombre, pe.primerApellido, "
+"pe.segundoApellido, YEAR(CURDATE())-YEAR(pe.`fechaNacimiento`) + IF(DATE_FORMAT(CURDATE(),'%m-%d') > DATE_FORMAT(pe.`fechaNacimiento`,'%m-%d'), 0, -1), pe.sexo, " 
+"p.numExpediente, eo.fechaRegistro, "
+"ts.nombreTipoServicio, "
+"e.nombreExamen, e.codigoExamen, "
+"tr.nombreResultado, tr.tipoResultado, "
+"per.primerNombre, m.nombreModalidad, sec.nombreSeccion "
+"FROM tblpaciente p "
+"INNER JOIN tblpersona pe ON pe.idPersona=p.idPersona "
+"INNER JOIN tblorden o ON o.idPaciente= p.idPaciente "
+"INNER JOIN tblexamen_orden eo ON eo.idOrden= o.idOrden "
+"INNER JOIN tblservicio s ON s.idServicio = o.idServicio "
+"INNER JOIN tbltiposervicio ts ON ts.idTipoServicio = s.idTipoServicio "
+"INNER JOIN tblexamen e ON e.idExamen = eo.idExamen "
+"INNER JOIN tblresultado r ON r.idExamenOrden = eo.idExamenOrden "
+"INNER JOIN tbltiporesultado tr ON tr.idTipoResultado = r.idTipoResultadoExamen "
+"INNER JOIN tblempleado em ON em.codigoMarcacion = r.codigoMarcacion "
+"INNER JOIN tblpersona per ON per.idPersona = em.idPersona "
+"INNER JOIN tblmodalidad m ON m.idModalidad = ts.idModalidad "
+"INNER JOIN tblseccion sec ON sec.idSeccionLaboratorio= e.idSeccionLaboratorio "
+"WHERE m.idModalidad = :modalidad AND sec.idSeccionLaboratorio = :seccion AND DATE(eo.fechaRegistro) = '2011-12-14'";
        listaTabulador=new ArrayList();
        Query query = session.createSQLQuery(sql);
        query.setParameter("modalidad", selectedModalidad);
        query.setParameter("seccion", selectedSeccion);
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
                //String sFecha = sdf.format(selectedFecha);
        //query.setParameter("fecha", sFecha);
        
        listaTabulador = (ArrayList) query.list();
        System.out.println("MODALIDAD"+selectedModalidad);
        System.out.println("SECCION"+selectedSeccion);
        //System.out.println("FECHA"+sFecha);
        System.out.println(hoy);
        System.out.println(listaTabulador.size());
        session.close();
        
    }
    
    public void imprimir() {
        String contextPath = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        this.url = contextPath + "/faces/createReportServlet?fileName=";
        String fileName = "informeDiarioActividades";
        String tipo = this.selectedTipo;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
                String sFecha = sdf.format(this.selectedFecha);
        
        String params = "&tipo=" + tipo + "&idModalidad=" + selectedModalidad + "&idSeccion=" + selectedSeccion + "&fecha=" + sFecha;
        this.url = this.url + fileName + params;
    }

    public Date getSelectedFecha() {
        return selectedFecha;
    }

    public void setSelectedFecha(Date selectedFecha) {
        this.selectedFecha = selectedFecha;
    }

    public Short getSelectedModalidad() {
        return selectedModalidad;
    }

    public void setSelectedModalidad(Short selectedModalidad) {
        this.selectedModalidad = selectedModalidad;
    }

    public Short getSelectedSeccion() {
        return selectedSeccion;
    }

    public void setSelectedSeccion(Short selectedSeccion) {
        this.selectedSeccion = selectedSeccion;
    }

    public ArrayList getListaTabulador() {
        return listaTabulador;
    }

    public void setListaTabulador(ArrayList listaTabulador) {
        this.listaTabulador = listaTabulador;
    }

    public Date getHoy() {
        return hoy;
    }

    public void setHoy(Date hoy) {
        this.hoy = hoy;
    }

    public String getSelectedTipo() {
        return selectedTipo;
    }

    public void setSelectedTipo(String selectedTipo) {
        this.selectedTipo = selectedTipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
