/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblobjeto;
import com.silac.model.Tblprivilegio;
import com.silac.model.Tblrol;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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
@ManagedBean(name = "rolesBean")
@ViewScoped
public class RolesBean implements Serializable {

    private Tblrol rol = new Tblrol();
    private List<Tblrol> roles;
    private Tblrol selectedRol = null;
    private TreeNode root;
    private ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
    private ArrayList<Permiso> permisos = new ArrayList<Permiso>();
    private List<Tblobjeto> menus;
    private List<Tblobjeto> subMenus;
    private List<Tblobjeto> acciones;
    private ArrayList<TreeNode> selectedNodes = new ArrayList<TreeNode>();
    private Boolean renderInfo;
    private Boolean renderDtObj;

    /** Creates a new instance of RolesBean */
    public RolesBean() {
        this.init();
    }

    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.rol = new Tblrol();
        this.roles = null;
        this.selectedRol = null;
        this.root = null;
        this.nodes = new ArrayList<TreeNode>();
        this.permisos = new ArrayList<Permiso>();
        this.menus = null;
        this.subMenus = null;
        this.acciones = null;
        this.selectedNodes = new ArrayList<TreeNode>();
        renderDtObj = false;
        renderInfo = true;
        populateDtRol();
    }

    /**Crea un nuevo rol*/
    public String crear() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            session.save(rol);
            session.flush();
            tx.commit();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "El rol se registro satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();
            roles = null;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
            populateDtRol();
            populateTreeTable();
        }
        return null;
    }

    /** Elimina el registro seleccionado por el usuario.*/
    public void eliminar() throws RollbackException, SystemException, HeuristicRollbackException, HeuristicMixedException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        String id = FacesUtil.getRequestParameter("idRol");
        Tblrol r = (Tblrol) session.load(Tblrol.class, Short.parseShort(id));
        try {
            tx = (Transaction) session.beginTransaction();
            session.delete(r);

            session.flush();
            tx.commit();
            renderInfo = true;
            renderDtObj = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Informacion:", "Registro eliminado satisfactoriamente"));
        } catch (HibernateException e) {
            tx.rollback();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Un error ha ocurrido:", e.getCause().getMessage()));
        } finally {
            session.close();
            populateDtRol();
            populateTreeTable();
        }
    }

    /**Registra los permisos asignados al rol seleccionado*/
    public void asignarPermiso() {
        if (selectedRol != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = null;
            try {
                tx = (Transaction) session.beginTransaction();

                Iterator iter = root.getChildren().iterator();

                while (iter.hasNext()) {
                    Tblprivilegio newPriv = new Tblprivilegio();
                    TreeNode mnuNode = (TreeNode) iter.next();
                    Permiso p = (Permiso) mnuNode.getData();

                    Criteria cPr = session.createCriteria(Tblprivilegio.class);
                    cPr.add(Restrictions.eq("tblobjeto", p.getObjeto()));
                    cPr.add(Restrictions.eq("tblrol", selectedRol));
                    Tblprivilegio priv = (Tblprivilegio) cPr.uniqueResult();

                    if (priv == null) {
                        newPriv.setTblobjeto(p.getObjeto());
                        newPriv.setPermiso(p.getPermiso());
                        newPriv.setTblrol(selectedRol);
                        session.save(newPriv);
                    } else {
                        newPriv = (Tblprivilegio) session.load(Tblprivilegio.class, priv.getIdPrivilegio());
                        newPriv.setPermiso(p.getPermiso());
                        session.update(newPriv);
                    }

                    Iterator itSmnu = mnuNode.getChildren().iterator();

                    while (itSmnu.hasNext()) {
                        newPriv = new Tblprivilegio();
                        TreeNode smnuNode = (TreeNode) itSmnu.next();
                        p = (Permiso) smnuNode.getData();

                        cPr = session.createCriteria(Tblprivilegio.class);
                        cPr.add(Restrictions.eq("tblobjeto", p.getObjeto()));
                        cPr.add(Restrictions.eq("tblrol", selectedRol));
                        priv = (Tblprivilegio) cPr.uniqueResult();

                        if (priv == null) {
                            newPriv.setTblobjeto(p.getObjeto());
                            newPriv.setPermiso(p.getPermiso());
                            newPriv.setTblrol(selectedRol);
                            session.save(newPriv);
                        } else {
                            newPriv = (Tblprivilegio) session.load(Tblprivilegio.class, priv.getIdPrivilegio());
                            newPriv.setPermiso(p.getPermiso());
                            session.update(newPriv);
                        }
                        Iterator itAc = smnuNode.getChildren().iterator();
                        while (itAc.hasNext()) {
                            newPriv = new Tblprivilegio();
                            p = (Permiso) ((TreeNode) itAc.next()).getData();

                            cPr = session.createCriteria(Tblprivilegio.class);
                            cPr.add(Restrictions.eq("tblobjeto", p.getObjeto()));
                            cPr.add(Restrictions.eq("tblrol", selectedRol));
                            priv = (Tblprivilegio) cPr.uniqueResult();

                            if (priv == null) {
                                newPriv.setTblobjeto(p.getObjeto());
                                newPriv.setPermiso(p.getPermiso());
                                newPriv.setTblrol(selectedRol);
                                session.save(newPriv);
                            } else {
                                newPriv = (Tblprivilegio) session.load(Tblprivilegio.class, priv.getIdPrivilegio());
                                newPriv.setPermiso(p.getPermiso());
                                session.update(newPriv);
                            }
                        }
                    }
                }
                session.flush();
                tx.commit();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Informacion:", "Los permisos se asignaron satisfactoriamente"));
            } catch (HibernateException e) {
                tx.rollback();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getMessage()));
            } finally {
                session.close();
                this.populateTreeTable();
                UsuarioBean u = (UsuarioBean) FacesUtil.getBean("usuarioBean");
                u.populateSubMenu();
            }
        }
    }

    /**Muestra los permisos asignador al rol seleccioando*/
    public void onRowSelect() {
        renderInfo = false;
        renderDtObj = true;
        populateTreeTable();
    }

    /**Genera la lista de roles*/
    public void populateDtRol() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT r from Tblrol r ORDER BY r.idRol";
        roles = null;
        Query query = session.createQuery(sql);
        roles = (ArrayList) session.createQuery(sql).list();
        session.close();
        populateTreeTable();
    }

    /**Genera la lista de objetos asociados al rol*/
    public void populateTreeTable() {
        root = new DefaultTreeNode("root", null);
        Boolean permiso = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        //try {
        String sqlMenu = "SELECT o from Tblobjeto o WHERE o.tipoObjeto LIKE 'menu' ORDER BY o.idObjeto";
        menus = session.createQuery(sqlMenu).list();

        for (Tblobjeto mnu : menus) {
            Criteria cpMnu = session.createCriteria(Tblprivilegio.class);
            cpMnu.add(Restrictions.eq("tblobjeto", mnu));
            cpMnu.add(Restrictions.eq("tblrol", selectedRol));
            Tblprivilegio pMnu = (Tblprivilegio) cpMnu.uniqueResult();

            permiso = false;
            if (pMnu != null) {
                permiso = pMnu.isPermiso();
            }
            TreeNode nodeMenu = new DefaultTreeNode(new Permiso(mnu, permiso), root);
            nodes.add(nodeMenu);

            Criteria cMenu = session.createCriteria(Tblobjeto.class);
            cMenu.add(Restrictions.eq("tblobjeto", mnu));
            subMenus = (List<Tblobjeto>) cMenu.list();

            for (Tblobjeto smnu : subMenus) {
                Criteria cpSub = session.createCriteria(Tblprivilegio.class);
                cpSub.add(Restrictions.eq("tblobjeto", smnu));
                cpSub.add(Restrictions.eq("tblrol", selectedRol));
                Tblprivilegio pSub = (Tblprivilegio) cpSub.uniqueResult();

                permiso = false;
                if (pSub != null) {
                    permiso = pSub.isPermiso();
                }
                TreeNode nodeSubMenu = new DefaultTreeNode(new Permiso(smnu, permiso), nodeMenu);
                nodes.add(nodeSubMenu);

                if (selectedRol != null) {
                  
                }

                Criteria cSubMenu = session.createCriteria(Tblobjeto.class);
                cSubMenu.add(Restrictions.eq("tblobjeto", smnu));
                acciones = (List<Tblobjeto>) cSubMenu.list();

                for (Tblobjeto ac : acciones) {
                    Criteria cpAc = session.createCriteria(Tblprivilegio.class);
                    cpAc.add(Restrictions.eq("tblobjeto", ac));
                    cpAc.add(Restrictions.eq("tblrol", selectedRol));
                    Tblprivilegio pAc = (Tblprivilegio) cpSub.uniqueResult();

                    permiso = false;
                    if (pAc != null) {
                        permiso = pAc.isPermiso();
                    }
                    nodes.add(new DefaultTreeNode(new Permiso(ac, permiso), nodeSubMenu));
                }
            }
        }
        // } catch (HibernateException e) {
        //logger.throwing(getClass().getName(), "ObjetosBean", e);
        // } finally {
        session.close();
        //}
    }

    /**
     * Getters & Setters
     */
    public Tblrol getRol() {
        return rol;
    }

    public void setRol(Tblrol rol) {
        this.rol = rol;
    }

    public List<Tblrol> getRoles() {
        return roles;
    }

    public void setRoles(List<Tblrol> roles) {
        this.roles = roles;
    }

    public Tblrol getSelectedRol() {
        return selectedRol;
    }

    public void setSelectedRol(Tblrol selectedRol) {
        this.selectedRol = selectedRol;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public ArrayList<TreeNode> getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(ArrayList<TreeNode> selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public Boolean getRenderDtObj() {
        return renderDtObj;
    }

    public void setRenderDtObj(Boolean renderDtObj) {
        this.renderDtObj = renderDtObj;
    }

    public Boolean getRenderInfo() {
        return renderInfo;
    }

    public void setRenderInfo(Boolean renderInfo) {
        this.renderInfo = renderInfo;
    }

    public class Permiso implements Serializable {

        private Tblobjeto objeto;
        private Boolean permiso;

        public Permiso(Tblobjeto obj, Boolean p) {
            this.objeto = obj;
            this.permiso = p;
        }

        public Permiso() {
        }

        public Tblobjeto getObjeto() {
            return objeto;
        }

        public void setObjeto(Tblobjeto objeto) {
            this.objeto = objeto;
        }

        public Boolean getPermiso() {
            return permiso;
        }

        public void setPermiso(Boolean permiso) {
            this.permiso = permiso;
        }
    }
}
