/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.validation;

import com.silac.controller.EvaluacionDonanteBean;
import com.silac.util.FacesUtil;
import java.util.ArrayList;
import java.util.Iterator;
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
@FacesValidator("evaluacionValidator")
public class EvaluacionValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        //ArrayList<EvaluacionDonanteBean.Evaluacion> lista = (ArrayList<EvaluacionDonanteBean.Evaluacion>) component.getAttributes().get("lista");
        EvaluacionDonanteBean eval = (EvaluacionDonanteBean) FacesUtil.getBean("evaluacionDonanteBean");
        boolean rechazar = eval.isRechazar();
        boolean preguntaRespuesta = false;
        boolean criterioRespuesta = false;
        if (!rechazar) {
            Iterator iterCuestionario = eval.getListaCuestionario().iterator();
            bucleIterCuestionario:
            while (iterCuestionario.hasNext()) {
                EvaluacionDonanteBean.Evaluacion tempEval = (EvaluacionDonanteBean.Evaluacion) iterCuestionario.next();
                if (tempEval.getRespuestaPregunta().getRespuesta() == null) {
                    preguntaRespuesta = true;
                    break bucleIterCuestionario;
                } else {
                    if (tempEval.getRespuestaPregunta().getRespuesta() == tempEval.getPregunta().isRechazarDonante()) {
                        criterioRespuesta = true;
                        Iterator iterCriterios = tempEval.getListaCriterios().iterator();
                        bucleIterCriterio:
                        while (iterCriterios.hasNext()) {
                            EvaluacionDonanteBean.Criterio tempCriterio = (EvaluacionDonanteBean.Criterio) iterCriterios.next();
                            System.out.println("Criterio " + tempCriterio.getCriterio().getTextoCriterio());
                            if (tempCriterio.getRespuestaCriterio().getRespuesta() != null && tempCriterio.getRespuestaCriterio().getRespuesta().booleanValue() != false) {
                                System.out.println("Respondio criterio");
                                criterioRespuesta = false;
                            }
                        }
                        if (criterioRespuesta) {
                            break bucleIterCuestionario;
                        }
                    }
                }
            }
        }

        if (preguntaRespuesta || criterioRespuesta == true) {
            FacesMessage msg = new FacesMessage("Error:", "No ha terminado de contestar el cuestionario");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }
    }
}
