/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import com.silac.util.FacesUtil;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import com.silac.controller.OrdenBean;

    /**
 * @author José Santos Mejía Angel
 * @author Mauricio Eduardo Marín Cruz
 * @author Fernando Emerson Ortiz Baron
 */
@FacesValidator("itemExamenSolicitadoValidator")
public class ItemExamenSolicitadoValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        OrdenBean o = (OrdenBean) FacesUtil.getBean("ordenBean");
        if (o.getListaExamenesSolicitados().isEmpty()) {
            FacesMessage msg = new FacesMessage("Error:", "Debe agregar al menos un examen a la lista");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
