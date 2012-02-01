/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.faces.validator.FacesValidator;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@FacesValidator("existenciaValidator")
public class ExistenciaValidator implements Validator {

    public ExistenciaValidator() {
    }
    
    //Metodo para validar si la cantidad requerida es mayor que las existencias disponibles    
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String existencia = (String) component.getAttributes().get("existencias");
        //variables de existencia y de cantidad requerida
        Double e = Double.parseDouble(existencia);
        Double s = Double.parseDouble(value.toString());
        //verifica si la cantidad solicitada es mayor que las existencias
        if (s > e) {
            FacesMessage msg = new FacesMessage("Error:", "La cantidad requerida excede las existencias");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);

        }
    }
    
}
