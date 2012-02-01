/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.util.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.MethodExpression;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.MethodExpressionActionListener;
import javax.servlet.ServletContext;
import org.primefaces.component.submenu.Submenu;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "menuBean")
@RequestScoped
public class MenuBean implements Serializable {

    private MenuModel modelMenuBar;
    private MenuModel modelLateralMenu;
    private MenuItem selectedMenu;

    public MenuBean() {
        this.init();
    }
    /**Inicializa los valores de las variables y propiedades**/
    private void init() {
        this.modelMenuBar = new DefaultMenuModel();
        this.modelLateralMenu = new DefaultMenuModel();
        this.selectedMenu = null;
        populateMainMenu();
        populateSubMenu();
    }

    /**
     *Genera el menu principal mostrando las opciones asignadas segun el rol del usuario.
     */
    public void populateMainMenu() {
        modelMenuBar = new DefaultMenuModel();
        Application app = FacesContext.getCurrentInstance().getApplication();
        UsuarioBean usuario = (UsuarioBean) FacesUtil.getBean("usuarioBean");
        Iterator iter = usuario.getListaMenu().iterator();
        Boolean exist = false;
        while (iter.hasNext()) {
            UsuarioBean.Objeto obj = (UsuarioBean.Objeto) iter.next();
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
                    item.setId("objeto" + obj.getId().toString());

                    MethodExpression methodExpr = app.getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{menuBean.onItemClick}", null, new Class[]{javax.faces.event.ActionEvent.class});

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
        modelMenuBar.addMenuItem(ayudaItem);

        MenuItem inicioItem = new MenuItem();
        inicioItem.setUrl("/faces/index.xhtml");
        inicioItem.setValue("Inicio");
        inicioItem.setId("mnuInicio");
        modelMenuBar.addMenuItem(inicioItem);

        MenuItem salirItem = new MenuItem();
        //Action
        MethodExpression methodExpr = app.getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{usuarioBean.cerrarSesion}", String.class, new Class[]{});

        //Listener
        salirItem.setActionExpression(methodExpr);
        salirItem.setValue("Salir");
        salirItem.setId("mnuSalir");
        salirItem.setProcess("@this");
        modelMenuBar.addMenuItem(salirItem);
    }

    /**
     *Generar el submenu, mostrando las opciones asignadas segun el rol de usuario.
     */
    public void populateSubMenu() {
        modelLateralMenu = new DefaultMenuModel();
        Application app = FacesContext.getCurrentInstance().getApplication();
        UsuarioBean usuario = (UsuarioBean) FacesUtil.getBean("usuarioBean");
        Iterator iter = usuario.getListaMenu().listIterator();
        Boolean exist = false;
        String idMenu;
        Short id;
        if (selectedMenu != null) {
            idMenu = selectedMenu.getId().substring(6);
            id = Short.parseShort(idMenu);
        } else {
            id = 0;
        }
        Submenu submenu = new Submenu();
        submenu.setId("submenu1");
        submenu.setLabel("Dynamic Submenu 1");
        String tipo;
        String parent;
        Integer i = 0;
        MethodExpression methodExpr = app.getExpressionFactory().createMethodExpression(FacesContext.getCurrentInstance().getELContext(), "#{menuBean.onItemClick}", null, new Class[]{javax.faces.event.ActionEvent.class});
        while (iter.hasNext()) {
            UsuarioBean.Objeto obj = (UsuarioBean.Objeto) iter.next();

            tipo = obj.getTipo().toString();
            parent = obj.getParentId().toString();
        }
        modelLateralMenu.addSubmenu(submenu);
    }

    /**
     *Identifica la opcion seleccionada por el usuario en el menu principal y despliega el submenu correspondiete
     *Si el usuario selecciona una opcion del submenu lo redirecciona a la pagina correspondiente.
     */
    public void onItemClick(ActionEvent actionEvent) {
        selectedMenu = (MenuItem) actionEvent.getSource();
        populateSubMenu();
        String url = "faces/" + selectedMenu.getUpdate();
        try {
            ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
            String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();
            ctx.redirect(ctxPath + "/faces/" + selectedMenu.getUpdate());
            //ec.redirect(url);
        } catch (IOException ex) {
            Logger.getLogger(MenuBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Getters & Setters
     */
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
}
