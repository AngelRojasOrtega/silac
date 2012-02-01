/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import java.util.Calendar;
import java.util.Date;
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
@FacesValidator("fechaValidator")
public class FechaValidator implements Validator {

    /*
     * Metodo para validar si un intervalo de fechas es valido. Es decir el limite superior del intervalo
     * es mayor que el limite inferior.
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (component.getAttributes().size() > 0) {
            Date fechaInicio = (Date) component.getAttributes().get("fechaInicio");
            Date fechaFinal = (Date) value;

            Calendar cInicio = Calendar.getInstance();
            cInicio.setTime(fechaInicio);

            Calendar cFinal = Calendar.getInstance();
            cFinal.setTime(fechaFinal);

            if (cFinal.compareTo(cInicio) < 0) {
                FacesMessage msg = new FacesMessage("Error:", "La fecha final debe ser mayor o igual a la de inicio");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
