/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.util;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 *
 * @author civic
 */
public class FacesUtil {

    public static String getRequestParameter(String name) {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }

    public static Object getBean(String expr) {
        FacesContext context = FacesContext.getCurrentInstance();
        Application app = context.getApplication();
        ValueBinding binding = app.createValueBinding("#{" + expr + "}");
        Object value = binding.getValue(context);
        return value;
    }

    public static String[] getMonths() {
        String[] months = new DateFormatSymbols(new Locale("es", "ES")).getMonths();
        return months;
    }

    public static String getMonth(int m) {
        return new DateFormatSymbols(new Locale("es", "ES")).getMonths()[m];
    }

    public static int getMaxDiasMes(int a, int m) {
        Calendar cal = Calendar.getInstance();
        cal.set(a, m, 1);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String getDia(int y, int m, int d) {
        Calendar newCal = new GregorianCalendar();
        newCal.set(y, m, d, 0, 0, 0);
        newCal.setTime(newCal.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE", new Locale("es", "ES"));
        String asWeek = dateFormat.format(newCal.getTime());
        return asWeek;
    }

    public static String getStringDate(Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
        String sDate = sdf.format(d);
        return sDate;
    }

    public static String emptyString(String s) {
        if (s == null) {
            s = "";
        }
        return s;
    }
    
    public static String booleanString(boolean b){
        String cadena ="";
        if(b){
            cadena = "Sí";
        }else{
            cadena = "No";
        }
        return cadena;
    }
}
