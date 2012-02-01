/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

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
@FacesValidator("passwordValidator")
public class PassworValidator implements Validator {

    public PassworValidator() {
    }
    
    /**
     * Metodo para verificar si la contraseña ingresada por el usuario coincide con el campo 
     * de "Verificar contraseña".
     */
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        String pass = (String) component.getAttributes().get("pass");
        if (pass != null) {
            if (!pass.equals(value.toString())) {
                FacesMessage msg = new FacesMessage("Error:", "Las contraseñas no coinciden");
                msg.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(msg);
            }
        }
    }
}
