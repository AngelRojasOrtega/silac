/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silac.servlet;

import com.silac.util.FacesUtil;
import com.silac.util.HibernateUtil;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.hibernate.Session;
import com.silac.controller.ExamenesBean;

/**
 *
 * @author kerberoz
 */
public class createReportServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Declaracion e inicializacion de variables
        int bit;
        File f = null;
        InputStream in = null;
        ServletOutputStream out = null;
        JRExporter exporter = null;
        String fName = null;
        File reportFile = null;
        String field = null;
        String value = null;
        Map<String, String> params = new HashMap<String, String>();

        //Obtiene el nombre y tipo de archivo, que fueron enviados a traves del URL
        String fileName = (String) request.getParameter("fileName");
        String tipo = (String) request.getParameter("tipo");
        String title = (String) request.getParameter("title");

        //Obtiene el archivo .jasper solicitado por el usuario.
        reportFile = new File(request.getRealPath("/reportes/" + fileName + ".jasper"));
        System.out.println("Report File: " + request.getRealPath("/reportes/" + fileName + ".jasper"));

        try {
            Set setParams = request.getParameterMap().entrySet();
            Iterator iterParams = setParams.iterator();
            List tempList = new ArrayList();
            while (iterParams.hasNext()) {
                Entry eParam = (Entry) iterParams.next();
                if (!"fileName".equals(eParam.getKey()) && !"tipo".equals(eParam.getKey())) {
                    Collections.addAll(tempList, request.getParameterValues(eParam.getKey().toString()));
                    params.put(eParam.getKey().toString(), request.getParameter(eParam.getKey().toString()));
                }
            }

            //Obtiene la conexion a la base de datos y compila el reporte
            Session session = HibernateUtil.getSessionFactory().openSession();
            JasperPrint jasperPrint = JasperFillManager.fillReport(reportFile.getPath(), params, session.connection());
            session.close();
            response.setContentType(tipo); //Asigna el content-type al archivo que sera generado
            //Crea un archivo basado en el reporte dependiendo del tipo seleccionado por el usuario.
            if ("application/pdf".equals(tipo)) {
                response.setHeader("Content-Disposition", "attachment;filename=\'" + jasperPrint.getName() + "\'");
                fName = fileName + ".pdf";
                exporter = new JRPdfExporter();
                exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, fName);
                //exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
            } else if ("application/vnd.ms-excel".equals(tipo)) {
                String vxls = "/reportes/detObjMet.xls";
                fName = request.getRealPath(vxls);
                response.setHeader("Content-Disposition", "attachment;filename=\'" + jasperPrint.getName() + "\'");
                exporter = new JRXlsExporter();
                exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, fName);
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            } else if ("text/html".equals(tipo)) {
                response.setHeader("Content-Disposition", "inline; " + jasperPrint.getName());
                exporter = new JRHtmlExporter();
                fName = fileName + ".html";
                exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT, jasperPrint);
                //exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, response.getWriter());
                exporter.setParameter(JRHtmlExporterParameter.OUTPUT_FILE_NAME, fName);
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
                exporter.setParameter(JRHtmlExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
                exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "ISO-8859-1");
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, request.getContextPath() + "/servlets/image?image=");
            }
            if (exporter != null) {
                exporter.exportReport();
            }
            //Escribe el archivo para que sea visualizado por el usuario.
            f = new File(fName);
            System.out.println("File: " + fName);
            in = new FileInputStream(fName);
            out = response.getOutputStream();
            bit = 256;
            while ((bit) >= 0) {
                bit = in.read();
                out.write(bit);
            }
        } catch (Exception ex) {
            Logger.getLogger(ExamenesBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.toString());
        } finally {
            out.flush();
            out.close();
            in.close();
        }
        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
