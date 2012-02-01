/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author civic
 */
@FacesValidator("ListaPuestoValidator")
public class ListaPuestoValidator implements Validator {

    public ListaPuestoValidator() {
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String idPuesto = (String) component.getAttributes().get("idPuesto");
        if (idPuesto == null) {
            idPuesto = "-1";
        }
        if (idPuesto.equals("-1") || idPuesto == null) {
            FacesMessage msg = new FacesMessage("Error:", "Debe seleccionar un puesto");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }
}