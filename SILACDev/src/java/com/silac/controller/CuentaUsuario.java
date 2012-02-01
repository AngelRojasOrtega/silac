/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblempleado;
import com.silac.model.Tblpersona;
import com.silac.model.Tblrol;
import com.silac.model.Tblusuario;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DualListModel;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "cuentaUsuarioBean")
@ViewScoped
public class CuentaUsuario implements Serializable {

    private DualListModel<String> listaRoles;
    private Tblusuario usuario = new Tblusuario();
    private String selectedEmpleado;
    private String verificar;
    private ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();

    /** Creates a new instance of CuentaUsuario */
    public CuentaUsuario() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.listaRoles = new DualListModel<String>();
        this.usuario = new Tblusuario();
        this.selectedEmpleado = null;
        this.verificar = null;
        this.listaUsuarios = new ArrayList<Usuario>();
        cargarRoles();
        cargarUsuarios();
    }

    public String crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();

            //Alamacena los datos del usuario
            Tblempleado e = (Tblempleado) session.load(Tblempleado.class, Short.parseShort(selectedEmpleado));
            usuario.setTblempleado(e);
            usuario.setEstado("Activo");

            //Obtiene los roles asignados al usuario y se los asigna
            List rolesTarget = listaRoles.getTarget();

            Iterator iter = rolesTarget.iterator();
            if (iter.hasNext()) {
                String n;
                Tblrol r;
                Set<Tblrol> rols = new HashSet<Tblrol>();
                while (iter.hasNext()) {
                    n = (String) iter.next();
                    Criteria cRol = session.createCriteria(Tblrol.class);
                    cRol.add(Restrictions.eq("nombreRol", n));
                    r = (Tblrol) cRol.uniqueResult();
                    rols.add(r);
                }
                usuario.setTblrols(rols);
            }

            //Guarda los datos del nuevo usuario
            session.save(usuario);
            tx.commit();
            this.init();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "La cuenta se creo satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            //secs = null;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
        }
        return null;
    }
    
    public void nuevo(){
        this.init();
    }
    
    public void cargarRoles() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        //Obtiene la lista de roles disponibles
        List<String> rolesSource = new ArrayList<String>();
        List<String> rolesTarget = new ArrayList<String>();

        String sql = "SELECT a FROM Tblrol a ORDER BY a.nombreRol";
        List<Tblrol> roles = (ArrayList) session.createQuery(sql).list();

        for (Tblrol r : roles) {
            rolesSource.add(r.getNombreRol());
        }
        listaRoles = new DualListModel<String>(rolesSource, rolesTarget);
        session.close();
    }

    public void cargarUsuarios() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT DISTINCT p, u FROM Tblusuario u "
                + "JOIN u.tblempleado e "
                + "JOIN e.tblpersona p ";
        Query query = session.createQuery(sql);
        List usuarios = query.list();
        Iterator iter = usuarios.iterator();
        while (iter.hasNext()) {
            Object[] o = (Object[]) iter.next();
            Tblpersona p = (Tblpersona) o[0];
            Tblusuario u = (Tblusuario) o[1];
            Set<Tblrol> r = (Set<Tblrol>) u.getTblrols();

            ArrayList<String> rol = new ArrayList<String>();

            Iterator itRol = r.iterator();
            while (itRol.hasNext()) {
                Tblrol tempRol = (Tblrol) itRol.next();
                rol.add(tempRol.getNombreRol());
            }
            listaUsuarios.add(new Usuario(p, u, rol));
        }

        session.close();
    }

    /**
     * Getters & Setters
     */
    public DualListModel<String> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(DualListModel<String> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public Tblusuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Tblusuario usuario) {
        this.usuario = usuario;
    }

    public String getSelectedEmpleado() {
        return selectedEmpleado;
    }

    public void setSelectedEmpleado(String selectedEmpleado) {
        this.selectedEmpleado = selectedEmpleado;
    }

    public String getVerificar() {
        return verificar;
    }

    public void setVerificar(String verificar) {
        this.verificar = verificar;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public class Usuario implements Serializable {

        private String nombre;
        private Tblpersona persona;
        private Tblusuario usuario;
        private ArrayList<String> rol;

        public Usuario() {
        }

        public Usuario(Tblpersona persona, Tblusuario usuario, ArrayList<String> rol) {
            this.persona = persona;
            this.usuario = usuario;
            this.rol = rol;
        }

        public Tblpersona getPersona() {
            return persona;
        }

        public void setPersona(Tblpersona persona) {
            this.persona = persona;
        }

        public ArrayList<String> getRol() {
            return rol;
        }

        public void setRol(ArrayList<String> rol) {
            this.rol = rol;
        }

        public Tblusuario getUsuario() {
            return usuario;
        }

        public void setUsuario(Tblusuario usuario) {
            this.usuario = usuario;
        }

        public String getNombre() {
            String cadena ="";
            cadena = FacesUtil.emptyString(this.persona.getPrimerNombre())+" "
                    + FacesUtil.emptyString(this.persona.getSegundoNombre())+" "
                    + FacesUtil.emptyString(this.persona.getPrimerApellido())+" "
                    + FacesUtil.emptyString(this.persona.getSegundoApellido());
            nombre = cadena;
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }        
    }
}
