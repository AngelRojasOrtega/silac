/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblempleado;
import com.silac.model.Tblexamen;
import com.silac.model.TblexamenOrden;
import com.silac.model.Tblobjeto;
import com.silac.model.Tblorden;
import com.silac.model.Tblpaciente;
import com.silac.model.Tblpagina;
import com.silac.model.Tblpersona;
import com.silac.model.Tblplanificaciontrabajo;
import com.silac.model.Tblprivilegio;
import com.silac.model.Tblrol;
import com.silac.model.Tblseccion;
import com.silac.model.Tblusuario;
import com.silac.util.FacesUtil;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import com.silac.util.HibernateUtil;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import com.silac.controller.OrdenBean.OrdenPendiente;
import com.silac.model.Tblestablecimiento;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Barón
 */
@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    private ArrayList<Objeto> listaMenu = new ArrayList<Objeto>(); //Lista de opciones del menu principal.
    private Boolean loggedIn = false; //Indica si el usuario esta logueado en el sistema o no.
    private Short id; //Almacena el id correspondiente al usuario logueado en el sistema. 
    private Short idEmpleado;
    private MenuModel modelMenuBar;
    private MenuModel modelLateralMenu;
    private MenuItem selectedMenu;
    private String login;
    private String pass;
    private String theme;
    private Integer idAnioLaboral;
    private OrdenBean.OrdenPendiente selectedOrdenPendiente;

    /**Crea una nueva instancia de UsuarioBean*/
    public UsuarioBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.listaMenu = new ArrayList<Objeto>(); //Lista de opciones del menu principal.
        this.loggedIn = false; //Indica si el usuario esta logueado en el sistema o no.
        this.id = null; //Almacena el id correspondiente al usuario logueado en el sistema. 
        this.modelMenuBar = null;
        this.modelLateralMenu = null;
        this.selectedMenu = null;
        this.login = null;
        this.pass = null;
        this.theme = null;
        this.idAnioLaboral = null;
        this.selectedOrdenPendiente = null;
        theme = "redmond";
        String sql = "SELECT e FROM Tblestablecimiento e WHERE e.idEstablecimiento = 1";
        Session session = HibernateUtil.getSessionFactory().openSession();
        Tblestablecimiento establecimiento = new Tblestablecimiento();
        this.theme = "redmond";
        if (session.createQuery(sql).uniqueResult() != null) {
            establecimiento = (Tblestablecimiento) session.createQuery(sql).uniqueResult();
            this.theme = establecimiento.getTema();
        }
        session.close();
    }

    /**Valida el ingreso del usuario al sistema*/
    public void validarUsuario() throws NamingException, SQLException, HeuristicMixedException, HeuristicRollbackException, IllegalStateException, SystemException, RollbackException {
        Session session = HibernateUtil.getSessionFactory().openSession(); //Inicia una session en Hibernate
        String sqlUser = "SELECT idUsuario FROM tblusuario WHERE login ='" + login + "' and pass= '" + pass + "' ";

        try {
            SQLQuery qUser = session.createSQLQuery(sqlUser);
            if (!qUser.list().isEmpty()) {
                this.id = (Short) session.createSQLQuery(sqlUser).uniqueResult();

                //Actualiza la bitacora
                BitacoraBean b = (BitacoraBean) FacesUtil.getBean("bitacoraBean");
                b.actualizar(id, "Inicio de sessión");

                //Obtiene los permisos asignados al usuario
                String hql = "SELECT DISTINCT u, r, o, p, pag FROM Tblusuario u "
                        + "JOIN u.tblrols r "
                        + "JOIN r.tblprivilegios p "
                        + "JOIN p.tblobjeto o "
                        + "JOIN o.tblpaginas pag "
                        + "WHERE p.permiso = true AND u.idUsuario = " + this.id;
                Query query = session.createQuery(hql);
                List permisos = query.list();
                Iterator iter = permisos.iterator();
                while (iter.hasNext()) {
                    Object[] o = (Object[]) iter.next();
                    Tblusuario u = (Tblusuario) o[0];
                    Tblrol r = (Tblrol) o[1];
                    Tblobjeto obj = (Tblobjeto) o[2];
                    Tblprivilegio p = (Tblprivilegio) o[3];
                    Tblpagina pag = (Tblpagina) o[4];

                    Tblobjeto parent = obj.getTblobjeto();

                    Short idObjeto = 0;
                    if (parent != null) {
                        idObjeto = parent.getIdObjeto();
                    }
                    listaMenu.add(new Objeto(pag.getUrl(), obj.getNombreObjeto(), obj.getTipoObjeto(), p.isPermiso(), obj.getIdObjeto(), idObjeto, obj.getGrupo()));
                }

                //Redirecciona hacia la pagina principal del sistema
                String url = ("faces/index.xhtml");
                FacesContext fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();
                try {
                    pass = "";
                    populateMainMenu();
                    this.loggedIn = true;
                    Tblusuario u = (Tblusuario) session.load(Tblusuario.class, this.id);
                    this.idEmpleado = u.getTblempleado().getCodigoMarcacion();
                    ec.redirect(url);
                } catch (IOException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", "Usuario o contraseña incorrectos"));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", "Usuario o contraseña incorrectos"));
            }
        } catch (HibernateException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
        }
    }//Fin validarUsuario

    /**Cierra la sesion en el sistema*/
    public void cerrarSesion() throws IllegalStateException, HeuristicMixedException, SystemException, HeuristicRollbackException, RollbackException {
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

        try {
            //invalida la sesión, para eliminar las variables de sesion
            ((HttpSession) ctx.getSession(false)).invalidate();

            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                BitacoraBean b = (BitacoraBean) FacesUtil.getBean("bitacoraBean");
                b.actualizar(id, "Cierre de sesión");
                this.loggedIn = false;
            } catch (HibernateException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
            } finally {
                session.close();
            }
            // Redirección a la vista de login,
            ctx.redirect(ctxPath + "/");
        } catch (IOException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        }
    }// Fin cerrarSesion

    /**Genera el menu que se mostrara al usuario logueado*/
    public void populateMainMenu() {
        Application app = FacesContext.getCurrentInstance().getApplication();
        Boolean exist = false;
        Iterator iter = listaMenu.iterator();
        modelMenuBar = new DefaultMenuModel();

        while (iter.hasNext()) {
            Objeto obj = (Objeto) iter.next();
            if (obj.getTipo().equals("menu")) {
                Iterator itModel = modelMenuBar.getMenuItems().iterator();
                exist = false;
                while (itModel.hasNext()) {
                    MenuItem tempItem = (MenuItem) itModel.next();
                    if (obj.getNombre().equals(tempItem.getValue())) {
                        exist = true;
                        break;
                    }
                }
                if (!exist) {
                    MenuItem item = new MenuItem();
                    item.setValue(obj.getNombre());
                    item.setUpdate(obj.getUrl());
                    item.setId("menu" + obj.getId().toString());

                    MethodExpression methodExpr = app.getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{usuarioBean.onClick}", null, new Class[]{javax.faces.event.ActionEvent.class});

                    item.addActionListener(new MethodExpressionActionListener(methodExpr));
                    item.setProcess("@this");
                    modelMenuBar.addMenuItem(item);
                }
            }
        }
        MenuItem ayudaItem = new MenuItem();
        ayudaItem.setUrl("#");
        ayudaItem.setValue("Ayuda");
        ayudaItem.setId("mnuAyuda");
        ayudaItem.setIcon("ui-icon-help");
        modelMenuBar.addMenuItem(ayudaItem);

        MenuItem inicioItem = new MenuItem();
        inicioItem.setUrl("/faces/index.xhtml");
        inicioItem.setValue("Inicio");
        inicioItem.setId("mnuInicio");
        inicioItem.setIcon("ui-icon-home");
        modelMenuBar.addMenuItem(inicioItem);

        MenuItem salirItem = new MenuItem();
        //Action
        MethodExpression methodExpr = app.getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{usuarioBean.cerrarSesion}", String.class, new Class[]{});
        //Listener
        salirItem.setActionExpression(methodExpr);
        salirItem.setValue("Salir");
        salirItem.setId("mnuSalir");
        salirItem.setProcess("@this");
        salirItem.setIcon("ui-icon-power");
        modelMenuBar.addMenuItem(salirItem);
    }//Fin populateMainMenu

    public String stringAnioLaboral() {
        String cadena = "Sin asignar";
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMMM, 'de' yyyy");

        return sdf.format(d);
    }

    /**Genera el submenu de acuerdo a la opción seleccionada por el usuario en el menu principal.*/
    public void populateSubMenu() {
        modelLateralMenu = new DefaultMenuModel();
        Application app = FacesContext.getCurrentInstance().getApplication();
        Iterator iter = listaMenu.listIterator();
        Iterator itSubMenu;
        Boolean existTareas = false;
        Boolean existConsultas = false;
        Boolean existReportes = false;
        String idMenu;
        Short id;
        if (selectedMenu != null) {
            idMenu = selectedMenu.getId().substring(4);
            id = Short.parseShort(idMenu);
        } else {
            id = 0;
        }
        Submenu subTareas = new Submenu();
        subTareas.setId("subTareas");
        subTareas.setLabel("Tareas");

        Submenu subConsultas = new Submenu();
        subConsultas.setId("subConsultas");
        subConsultas.setLabel("Consultas");

        Submenu subReportes = new Submenu();
        subReportes.setId("subReportes");
        subReportes.setLabel("Reportes");

        String tipo;
        String parent;
        Integer i = 0;
        MethodExpression methodExpr = app.getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{usuarioBean.onClick}", null, new Class[]{javax.faces.event.ActionEvent.class});
        while (iter.hasNext()) {
            Objeto obj = (Objeto) iter.next();
            tipo = obj.getTipo().trim();
            parent = obj.getParentId().toString();
            if (tipo.equals("submenu") && parent.equals(id.toString()) && obj.getGrupo().equals("tarea")) {
                itSubMenu = subTareas.getChildren().iterator();
                existTareas = false;
                while (itSubMenu.hasNext()) {
                    MenuItem tempItem = (MenuItem) itSubMenu.next();
                    if (obj.getNombre().equals(tempItem.getValue())) {
                        existTareas = true;
                        break;
                    }
                }
                if (!existTareas) {
                    MenuItem item = new MenuItem();
                    item.setValue(obj.getNombre());
                    i = i + 1;
                    item.setId("subMenu" + obj.getParentId().toString() + obj.getId());
                    item.setUpdate(obj.getUrl());
                    item.addActionListener(new MethodExpressionActionListener(methodExpr));
                    item.setProcess("@this");
                    subTareas.getChildren().add(item);
                }
            }
            if (tipo.equals("submenu") && parent.equals(id.toString()) && obj.getGrupo().equals("consulta")) {
                itSubMenu = subConsultas.getChildren().iterator();
                existConsultas = false;
                while (itSubMenu.hasNext()) {
                    MenuItem tempItem = (MenuItem) itSubMenu.next();
                    if (obj.getNombre().equals(tempItem.getValue())) {
                        existConsultas = true;
                        break;
                    }
                }
                if (!existConsultas) {
                    MenuItem item = new MenuItem();
                    item.setValue(obj.getNombre());
                    i = i + 1;
                    item.setId("subMenu" + obj.getParentId().toString() + obj.getId());
                    item.setUpdate(obj.getUrl());
                    item.addActionListener(new MethodExpressionActionListener(methodExpr));
                    item.setProcess("@this");
                    subConsultas.getChildren().add(item);
                }
            }
            if (tipo.equals("submenu") && parent.equals(id.toString()) && obj.getGrupo().equals("reporte")) {
                itSubMenu = subReportes.getChildren().iterator();
                existReportes = false;
                while (itSubMenu.hasNext()) {
                    MenuItem tempItem = (MenuItem) itSubMenu.next();
                    if (obj.getNombre().equals(tempItem.getValue())) {
                        existReportes = true;
                        break;
                    }
                }
                if (!existReportes) {
                    MenuItem item = new MenuItem();
                    item.setValue(obj.getNombre());
                    i = i + 1;
                    item.setId("subMenu" + obj.getParentId().toString() + obj.getId());
                    item.setUpdate(obj.getUrl());
                    item.addActionListener(new MethodExpressionActionListener(methodExpr));
                    item.setProcess("@this");
                    subReportes.getChildren().add(item);
                }
            }
        }
        if (subTareas.getChildCount() > 0) {
            modelLateralMenu.addSubmenu(subTareas);
        }
        if (subConsultas.getChildCount() > 0) {
            modelLateralMenu.addSubmenu(subConsultas);
        }
        if (subReportes.getChildCount() > 0) {
            modelLateralMenu.addSubmenu(subReportes);
        }
    }// Fin populateSubMenu

    public void onClick(ActionEvent actionEvent) {
        MenuItem item = (MenuItem) actionEvent.getSource();
        String tipo = item.getId();
        if (tipo.contains("menu")) {
            selectedMenu = item;
            populateSubMenu();
        }
        try {
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ctx.redirect(ctxPath + "/faces/" + item.getUpdate());
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin onClick

    /**
     * Getters and Setters
     */
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public ArrayList<Objeto> getListaMenu() {
        return listaMenu;
    }

    public void setListaMenu(ArrayList<Objeto> listaMenu) {
        this.listaMenu = listaMenu;
    }

    public MenuModel getModelMenuBar() {
        return modelMenuBar;
    }

    public MenuModel getModelLateralMenu() {
        return modelLateralMenu;
    }

    public MenuItem getSelectedMenu() {
        return selectedMenu;
    }

    public void setSelectedMenu(MenuItem selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Integer getIdAnioLaboral() {
        return idAnioLaboral;
    }

    public void setIdAnioLaboral(Integer idAnioLaboral) {
        this.idAnioLaboral = idAnioLaboral;
    }

    public Short getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Short idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public OrdenPendiente getSelectedOrdenPendiente() {
        return selectedOrdenPendiente;
    }

    public void setSelectedOrdenPendiente(OrdenPendiente selectedOrdenPendiente) {
        this.selectedOrdenPendiente = selectedOrdenPendiente;
    }

    public class Objeto implements Serializable {

        private String url;
        private String nombre;
        private String tipo;
        private Boolean permiso;
        private Short id;
        private Short parentId;
        private String grupo;

        public Objeto() {
        }

        public Objeto(String url, String nombre, String tipo, Boolean permiso, Short id, Short parentId, String g) {
            this.url = url;
            this.nombre = nombre;
            this.tipo = tipo;
            this.permiso = permiso;
            this.id = id;
            this.parentId = parentId;
            this.grupo = g;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Boolean getPermiso() {
            return permiso;
        }

        public void setPermiso(Boolean permiso) {
            this.permiso = permiso;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Short getId() {
            return id;
        }

        public void setId(Short id) {
            this.id = id;
        }

        public Short getParentId() {
            return parentId;
        }

        public void setParentId(Short parentId) {
            this.parentId = parentId;
        }

        public String getGrupo() {
            return grupo;
        }

        public void setGrupo(String grupo) {
            this.grupo = grupo;
        }
    }
}
