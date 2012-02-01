/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author kerberoz
 */
@ManagedBean(name = "autoCompleteBean")
@ViewScoped
public class AutoCompleteBean implements Serializable {

    /** Creates a new instance of AutoCompleteBean */
    public AutoCompleteBean() {
    }

    public List<String> complete(String query) {
        List<String> results = new ArrayList<String>();
        String field =  (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("field");
        String table = (String) UIComponent.getCurrentComponent(FacesContext.getCurrentInstance()).getAttributes().get("table");
        Session session = HibernateUtil.getSessionFactory().openSession();
        String sql = "SELECT t."+ field +" FROM "+ table +" t "
                + "WHERE t."+ field +" LIKE :valor "
                + "GROUP BY t."+field;
        Query hQuery = session.createSQLQuery(sql);
        hQuery.setParameter("valor", "%" + query + "%");
        results = hQuery.list();
        session.close();
        return results;
    }
}
