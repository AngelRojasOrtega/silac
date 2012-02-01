/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.controller;

import com.silac.model.Tblusuario;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FlowEvent;
import java.sql.*;
import javax.swing.JOptionPane;
import java.math.*;

/**
 *
 * @author mauricioherrera
 */
@ManagedBean(name = "preferenciaBean")
@ViewScoped
public class preferenciaBean implements Serializable {

    private Tblusuario user = new Tblusuario();
    private boolean skip;
    private static final Logger logger = Logger.getLogger(preferenciaBean.class.getName());
    private String nameuser = "";
    private String password = "";
    private static final String connectionURL = "jdbc:mysql://localhost:3306";
    private Integer progress;

    public preferenciaBean() {
        this.init();
    }

    private void init() {
    }

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("*** Driver loaded");
        } catch (Exception e) {
            System.out.println("*** Error : " + e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
        }

    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionURL, nameuser, password);
    }

    public void resetDatabase() throws SQLException {
        String s = new String();
        String sqlQuery = "";
        StringBuilder sb = new StringBuilder();
        String fileName;

        try {

            fileName = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/dbsilac.sql");

            FileReader fr = new FileReader(new File(fileName));
            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character

            BufferedReader br = new BufferedReader(fr);
            Connection c = getConnection();
            Statement st = c.createStatement();

            sb.append("");
            while ((s = br.readLine()) != null) {
                //Skip comments and empty lines

                if (s.length() > 0 && s.charAt(0) == '-' || s.length() == 0) {
                    continue;
                }
                sqlQuery = sqlQuery + " " + s;
                //If one command complete
                if (sqlQuery.charAt(sqlQuery.length() - 1) == ';') {
                    sb.append("<br><br>" + sqlQuery);
                    sqlQuery = sqlQuery.replace(';', ' '); //Remove the ; since jdbc complains
                    try {
                        st.execute(sqlQuery);
                    } catch (SQLException ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", ex.getMessage()));
                    } catch (Exception ex) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", ex.getMessage()));
                    }
                    sqlQuery = "";
                }
            }
            br.close();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Base de Datos creada satisfactoriamente", sb.toString()));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un error ha ocurrido:", e.toString() + "<br>" + sb.toString()));
        }
    }

    //bloque para la barra de progreso 
    public Integer getProgress() {
        if (progress == null) {
            progress = 0;
        } else {
            progress = progress + (int) (Math.random() * 35);

            if (progress > 100) {
                progress = 100;
            }
        }

        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));
    }

    public void cancel() {
        progress = null;
    }

    //fin del bloque de progreso
    public Tblusuario getUser() {
        return user;
    }

    public void setUser(Tblusuario user) {
        this.user = user;
    }

    public void save(ActionEvent actionEvent) {
        //Persist user  

        FacesMessage msg = new FacesMessage("Successful", "Welcome :" + user.getLogin());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public boolean isSkip() {
        return skip;
    }

    public void setSkip(boolean skip) {
        this.skip = skip;
    }

    public String getNameuser() {
        return nameuser;
    }

    public void setNameuser(String nameuser) {
        this.nameuser = nameuser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String onFlowProcess(FlowEvent event) {
        logger.log(Level.INFO, "Current wizard step:{0}", event.getOldStep());
        logger.log(Level.INFO, "Next step:{0}", event.getNewStep());

        if (skip) {
            skip = false;   //reset in case user goes back  
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }
    /** Creates a new instance of preferenciaBean */
}
