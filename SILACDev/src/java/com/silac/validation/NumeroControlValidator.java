/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import com.silac.model.Tblseccion;
import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.util.Calendar;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.hibernate.Query;
import org.hibernate.Session;
import com.silac.controller.OrdenBean;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@FacesValidator("numeroControlValidator")
public class NumeroControlValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Short idSeccion = (Short) component.getAttributes().get("idSeccion");
        System.out.println(idSeccion);
        //FacesMessage msg = null;

        if (idSeccion != null) {
            OrdenBean o = (OrdenBean) FacesUtil.getBean("ordenBean");
            Session session = HibernateUtil.getSessionFactory().openSession();
            Tblseccion sec = (Tblseccion) session.load(Tblseccion.class, idSeccion);
            String periodo = sec.getReinicioNumeroControl();
            Map<String, Calendar> mPeriodo = o.getPeriodoReinicio(periodo);
            String sql = "SELECT eo.numeroControl "
                    + "FROM Tblseccion sec "
                    + "JOIN sec.tblexamens ex "
                    + "JOIN ex.tblexamenOrdens eo "
                    + "JOIN eo.tblorden o "
                    + "WHERE sec.idSeccionLaboratorio = :idSeccion "
                    + "AND eo.numeroControl = :numeroControl "
                    + "AND (o.fechaRecepcion BETWEEN :inicio AND :fin)";
            Query query = session.createQuery(sql);
            query.setParameter("idSeccion", idSeccion);
            query.setParameter("numeroControl", value);
            query.setParameter("inicio", mPeriodo.get("inicio").getTime());
            query.setParameter("fin", mPeriodo.get("final").getTime());
            if (!query.list().isEmpty()) {
                throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "El numero de control ya ha sido asignado"));
                //msg = new FacesMessage("Error:", "El numero ya ha sido asignado");
            }
            session.close();
        } else {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Seleccione una sección"));
            //msg = FacesMessage("Error:", "Seleccione una sección");
        }
        //msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        //throw new ValidatorException(msg);
    }
}
