/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.converters;

import com.silac.model.Tblseccion;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.DualListModel;
import org.primefaces.component.picklist.PickList;


/**
 *
 * @author kerberoz
 */
@FacesConverter("seccionesPickListConverter")
public class SeccionesPickListConverter implements Converter {

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
                string = String.valueOf(((Tblseccion) object).getIdSeccionLaboratorio());
            } catch (ClassCastException cce) {
                throw new ConverterException();
            }
        }
        return string;
    }

    @SuppressWarnings("unchecked")
    private Tblseccion getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Tblseccion> dualList;
        try {
            dualList = (DualListModel<Tblseccion>) ((PickList) component).getValue();
            Tblseccion sec = getObjectFromList(dualList.getSource(), Short.valueOf(value));
            if (sec == null) {
                sec = getObjectFromList(dualList.getTarget(), Short.valueOf(value));
            }

            return sec;
        } catch (ClassCastException cce) {
            throw new ConverterException();
        } catch (NumberFormatException nfe) {
            throw new ConverterException();
        }
    }

    private Tblseccion getObjectFromList(final List<?> list, final Short identifier) {
        for (final Object object : list) {
            final Tblseccion sec = (Tblseccion) object;
            if (sec.getIdSeccionLaboratorio().equals(identifier)) {
                return sec;
            }
        }
        return null;
    }
}
