/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import com.silac.util.FacesUtil;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.silac.controller.ExamenesBean;

/**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@FacesValidator("itemExamenValidator")
public class ItemExamenValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        ExamenesBean e = (ExamenesBean) FacesUtil.getBean("examenesBean");
        if (e.getListaArticulosAsignados().isEmpty() || e.getListaParametrosAsignados().isEmpty()) {
            FacesMessage msg = new FacesMessage("Error:", "Debe agregar, al menos, un reactivo o insumo y un parametro");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
