/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.listener;

import com.silac.controller.UsuarioBean;
import com.silac.util.FacesUtil;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.ServletContext;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
public class LoggedInCheck implements PhaseListener {

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    @Override
    public void beforePhase(PhaseEvent event) {
    }

    /**
     * Verifica si el usuario ha iniciado sesion en el sistema, si este es el caso lo envia 
     * a una pagina donde se le informa que ya inicio sesion.
     * Si el usuario trata de ingresar a traves del URL a cualquier parte del sistema y no ha iniciado 
     * sesion, es redireccionado a la pagina de inicio de sesion.
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        FacesContext fc = event.getFacesContext();
        ExternalContext ctx = FacesContext.getCurrentInstance().getExternalContext();
        String ctxPath = ((ServletContext) ctx.getContext()).getContextPath();

        // Check to see if they are on the login page.
        boolean loginPage = fc.getViewRoot().getViewId().lastIndexOf("login") > -1 ? true : false;
        boolean isLoggedIn = fc.getViewRoot().getViewId().lastIndexOf("isLoggedIn") > -1 ? true : false;
        boolean preferencias = fc.getViewRoot().getViewId().lastIndexOf("Preferencias") > -1 ? true : false;
        if (!preferencias) {
            if (!loginPage && !loggedIn()) {
                try {
                    // Redirección a la vista login
                    ctx.redirect(ctxPath + "/");
                } catch (IOException e) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Un error ha ocurrido:", e.getMessage()));
                }
            } else if (loggedIn() && loginPage) {
                try {
                    // Redirección a la vista login
                    ctx.redirect(ctxPath + "/faces/isLoggedIn.xhtml");
                } catch (IOException e) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Un error ha ocurrido:", e.getMessage()));
                }
            }
        }
    }

    /** Verifica si el usuario ha iniciado sesion*/
    private boolean loggedIn() {
        boolean exist = false;
        UsuarioBean u = (UsuarioBean) FacesUtil.getBean("usuarioBean");
        if (u.getLoggedIn() != null) {
            exist = u.getLoggedIn().booleanValue();
        }
        return exist;
    }
}
