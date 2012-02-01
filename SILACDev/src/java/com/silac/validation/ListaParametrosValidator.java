/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import java.util.ArrayList;
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
@FacesValidator("listaParametrosValidator")
public class ListaParametrosValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ArrayList<Object[]> lista = (ArrayList<Object[]>) component.getAttributes().get("lista");
        if (lista.isEmpty()) {
            FacesMessage msg = new FacesMessage("Error:", "El examen no contiene parametros");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
    
}
