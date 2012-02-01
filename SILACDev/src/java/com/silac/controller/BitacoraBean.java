/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblbitacora;
import com.silac.model.Tblusuario;
import com.silac.util.HibernateUtil;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@ManagedBean(name = "bitacoraBean")
@RequestScoped
public class BitacoraBean {

    /** Creates a new instance of BitacoraBean */
    public BitacoraBean() {
    }

    /**
     * Actualiza la bitacora
     * @param id Identificador del usuario logueado en el sistema
     * @param op Descripción textual de la operación realizada por el usuario
     */
    public void actualizar(Short id, String op) throws IllegalStateException, RollbackException, HeuristicMixedException, SystemException, HeuristicRollbackException {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = null;
        try {
            tx = (Transaction) session.beginTransaction();
            Tblbitacora b = new Tblbitacora();
            b.setFecha(new Date());
            b.setHora(new Date());
            b.setTipoOperacion(op);
            b.setTblusuario((Tblusuario) session.load(Tblusuario.class, id));
            session.save(b);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.getCause().getMessage()));
        }
    }//Fin atualizar
}
