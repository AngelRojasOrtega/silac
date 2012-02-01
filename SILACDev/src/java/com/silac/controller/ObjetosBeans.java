/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblobjeto;
import com.silac.model.Tblpagina;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.RollbackException;
import javax.servlet.ServletContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.SystemException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "objetosBean")
@ViewScoped
public class ObjetosBeans implements Serializable {

    private ArrayList listaObjetos = new ArrayList();
    private TreeNode root;
    private Tblobjeto objeto = new Tblobjeto();
    private Tblpagina pagina = new Tblpagina();
    private Tblobjeto selectedObj;
    private List<Tblobjeto> menus;
    private List<Tblobjeto> subMenus;
    private List<Tblobjeto> acciones;
    private ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
    private String selectedObjeto;
    private String selectedTipo;
    private Boolean renderPaginaForm;
    private Boolean renderGrupoForm;
    private String selectedGrupo;
    private static final Logger logger = Logger.getLogger("ObjetosBean");

    /** Creates a new instance of ObjetosBeans */
    public ObjetosBeans() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.listaObjetos = new ArrayList();
        this.root = null;
        this.objeto = new Tblobjeto();
        this.pagina = new Tblpagina();
        this.selectedObj = null;
        this.menus = null;
        this.subMenus = null;
        this.acciones = null;
        this.nodes = new ArrayList<TreeNode>();
        this.selectedObjeto = null;
        this.selectedTipo = null;
        this.renderPaginaForm = null;
        this.renderGrupoForm = null;
        this.selectedGrupo = null;
        this.populateTreeTable();
    }

    /** Crea un nuevo objeto*/
    public void resetForm() {
        selectedTipo = null;
    }

    public String crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            if (selectedTipo.equals("submenu") || selectedTipo.equals("accion")) {
                Tblobjeto objtemp = (Tblobjeto) session.load(Tblobjeto.class, Short.parseShort(selectedObjeto));
                objeto.setTblobjeto(objtemp);
            }
            objeto.setTipoObjeto(selectedTipo);
            objeto.setGrupo(this.selectedGrupo);
            session.save(objeto);
            if (!selectedTipo.equals("accion")) {
                pagina.setTitulo(objeto.getNombreObjeto());
                pagina.setTblobjeto(objeto);
                session.save(pagina);
            }
            tx.commit();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "El objeto se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
        } finally {
            session.close();
            populateTreeTable();
            UsuarioBean u = (UsuarioBean) FacesUtil.getBean("usuarioBean");
            u.populateSubMenu();
        }
        return null;
    }

    public void nuevo() {
        this.init();
    }

    /**Elimina el registro seleccionado por el usuario.*/
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idObj");
        Tblobjeto o = (Tblobjeto) session.load(Tblobjeto.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(o);
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
            populateTreeTable();
            UsuarioBean u = (UsuarioBean) FacesUtil.getBean("usuarioBean");
            u.populateSubMenu();
        }
    }

    /**Genera el arbol de objetos para visualizar la jerarquia de los mismos*/
    public void populateTreeTable() {
        root = new DefaultTreeNode("root", null);
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String sqlMenu = "SELECT o from Tblobjeto o WHERE o.tipoObjeto LIKE 'menu' ORDER BY o.idObjeto";
            menus = session.createQuery(sqlMenu).list();

            for (Tblobjeto mnu : menus) {
                TreeNode nodeMenu = new DefaultTreeNode(mnu, root);
                nodes.add(nodeMenu);

                Criteria cMenu = session.createCriteria(Tblobjeto.class);
                cMenu.add(Restrictions.eq("tblobjeto", mnu));
                subMenus = (List<Tblobjeto>) cMenu.list();

                for (Tblobjeto smnu : subMenus) {
                    TreeNode nodeSubMenu = new DefaultTreeNode(smnu, nodeMenu);
                    nodes.add(nodeSubMenu);

                    Criteria cSubMenu = session.createCriteria(Tblobjeto.class);
                    cSubMenu.add(Restrictions.eq("tblobjeto", smnu));
                    acciones = (List<Tblobjeto>) cSubMenu.list();

                    for (Tblobjeto ac : acciones) {
                        nodes.add(new DefaultTreeNode(ac, nodeSubMenu));
                    }
                }
            }
        } catch (HibernateException e) {
            logger.throwing(getClass().getName(), "ObjetosBean", e);
        } finally {
            session.close();
        }
    }

    /*
     * Establece si se muestra o no parte del formulario de captura de datos
     * especificamente la lista para seleccion de grupos y el fieldset "Datos Pagina Web"
     */
    public void loadForm() {
        if (!selectedTipo.equals("accion")) {
            this.renderPaginaForm = true;
        } else {
            this.renderPaginaForm = false;
        }
        if (selectedTipo.equals(null) || selectedTipo.equals("menu")) {
            this.renderGrupoForm = false;
        } else {
            this.renderGrupoForm = true;
        }
        populateListaObjetos();
    }

    /**Genera la lista de objetos para la lista desplegable cboGrupo*/
    public void populateListaObjetos() {
        listaObjetos.clear();
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String tipo = null;
            if (selectedTipo.equals("submenu")) {
                tipo = "menu";
            } else if (selectedTipo.equals("accion")) {
                tipo = "submenu";
            }

            Criteria cObjetos = session.createCriteria(Tblobjeto.class);
            cObjetos.add(Restrictions.eq("tipoObjeto", tipo));
            Iterator iteObjetos = cObjetos.list().iterator();
            while (iteObjetos.hasNext()) {
                Tblobjeto o = (Tblobjeto) iteObjetos.next();
                listaObjetos.add(new SelectItem(new Short(o.getIdObjeto()).toString(), o.getNombreObjeto(), ""));
            }
        } catch (HibernateException e) {
            //logger.throwing(getClass().getName(), "populateListaObjetos", e);
        } finally {
            session.close();
        }
    }

    /**
     * Getters & Setters
     */
    public ArrayList getListaObjetos() {
        return listaObjetos;
    }

    public void setListaObjetos(ArrayList listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public Tblobjeto getObjeto() {
        return objeto;
    }

    public void setObjeto(Tblobjeto objeto) {
        this.objeto = objeto;
    }

    public Tblpagina getPagina() {
        return pagina;
    }

    public void setPagina(Tblpagina pagina) {
        this.pagina = pagina;
    }

    public Tblobjeto getSelectedObj() {
        return selectedObj;
    }

    public void setSelectedObj(Tblobjeto selectedObj) {
        this.selectedObj = selectedObj;
    }

    public String getSelectedObjeto() {
        return selectedObjeto;
    }

    public void setSelectedObjeto(String selectedObjeto) {
        this.selectedObjeto = selectedObjeto;
    }

    public String getSelectedTipo() {
        return selectedTipo;
    }

    public void setSelectedTipo(String selectedTipo) {
        this.selectedTipo = selectedTipo;
    }

    public Boolean getRenderPaginaForm() {
        return renderPaginaForm;
    }

    public void setRenderPaginaForm(Boolean renderForm) {
        this.renderPaginaForm = renderForm;
    }

    public Boolean getRenderGrupoForm() {
        return renderGrupoForm;
    }

    public void setRenderGrupoForm(Boolean renderGrupoForm) {
        this.renderGrupoForm = renderGrupoForm;
    }

    public String getSelectedGrupo() {
        return selectedGrupo;
    }

    public void setSelectedGrupo(String selectedGrupo) {
        this.selectedGrupo = selectedGrupo;
    }
}
