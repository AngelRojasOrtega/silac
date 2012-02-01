/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@FacesValidator("horaValidator")
public class HoraValidator implements Validator {

    /*
     * Metodo para validar si un intervalo de horas es valido. Es decir el limite superior del intervalo
     * es mayor que el limite inferior.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (component.getAttributes().size() > 0) {
            int horaInicio = Integer.parseInt(component.getAttributes().get("horaInicio").toString());
            int minsInicio = Integer.parseInt(component.getAttributes().get("minsInicio").toString());
            int am_pmInicio = Integer.parseInt(component.getAttributes().get("am_pmInicio").toString());
            int horaFinal = Integer.parseInt(component.getAttributes().get("horaFin").toString());
            int minsFinal = Integer.parseInt(component.getAttributes().get("minsFin").toString());
            int am_pmFinal = Integer.parseInt(value.toString());

            Calendar cInicio = Calendar.getInstance();
            Calendar cFinal = Calendar.getInstance();

            cInicio.set(Calendar.HOUR, horaInicio);
            cInicio.set(Calendar.MINUTE, minsInicio);
            cInicio.set(Calendar.SECOND, 0);
            cInicio.set(Calendar.AM_PM, am_pmInicio);

            cFinal.set(Calendar.HOUR, horaFinal);
            cFinal.set(Calendar.MINUTE, minsFinal);
            cFinal.set(Calendar.SECOND, 0);
            cFinal.set(Calendar.AM_PM, am_pmFinal);

            if (cFinal.compareTo(cInicio) <= 0) {
                FacesMessage msg = new FacesMessage("Error:", "La hora de finalización debe ser mayor a la hora de inicio");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
