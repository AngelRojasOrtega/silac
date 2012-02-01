/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.converters;

import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.DualListModel;
import org.primefaces.component.picklist.PickList;
import com.silac.controller.AsistenciaCapBean;


/**
 *
 * @author kerberoz
 */
@FacesConverter("empleadosPickListConverter")
public class EmpleadosPickListConverter implements Converter {

    //private static final Logger LOG = (Logger) LoggerFactory.getLogger(EmpleadosPickListConverter.class);

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component, value);
    }

    public String getAsString(FacesContext context, UIComponent component, Object object) {
        String string;
        if (object == null) {
            string = "";
        } else {
            try {
                string = String.valueOf(((AsistenciaCapBean.Empleado) object).getCodigo());
            } catch (ClassCastException cce) {
                throw new ConverterException();
            }
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    private AsistenciaCapBean.Empleado getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<AsistenciaCapBean.Empleado> dualList;
        try {
            dualList = (DualListModel<AsistenciaCapBean.Empleado>) ((PickList) component).getValue();
            AsistenciaCapBean.Empleado empleado = getObjectFromList(dualList.getSource(), Short.valueOf(value));
            if (empleado == null) {
                empleado = getObjectFromList(dualList.getTarget(), Short.valueOf(value));
            }

            return empleado;
        } catch (ClassCastException cce) {
            throw new ConverterException();
        } catch (NumberFormatException nfe) {
            throw new ConverterException();
        }
    }

    private AsistenciaCapBean.Empleado getObjectFromList(final List<?> list, final Short identifier) {
        for (final Object object : list) {
            final AsistenciaCapBean.Empleado empleado = (AsistenciaCapBean.Empleado) object;
            if (empleado.getCodigo().equals(identifier)) {
                return empleado;
            }
        }
        return null;
    }
}