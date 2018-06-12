/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.report;

import com.khtm.database.DBHandler;
import com.khtm.object.NotificationObj;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alirzea
 */
@WebServlet(name = "CustomerCharge", urlPatterns = {"/CustomerCharge"})
public class CustomerCharge extends HttpServlet {

    public static int number = 0;
    private static List<NotificationObj> tblNotification;
    private DBHandler db = new DBHandler();
    public static String dateRegister, dateCharge;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("customercharge.jsp");
        dateRegister = request.getParameter("dateRegister");
        dateCharge = request.getParameter("dateCharge");
        try{
            String action = request.getParameter("action");
            if(action.equals("download")){
                db.open();
                tblNotification = db.getUserChargedInDate(dateRegister, dateCharge);
                db.close();
                String folder = UUID.randomUUID() + "";
                new File(folder).mkdir();
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(
                                new File(folder +"/report.csv"))));
                String dataFile = "text,keyword,from,date\n";
                for(NotificationObj obj : tblNotification){
                    dataFile += obj.getText() + "," + obj.getKeyword() + "," + obj.getFrom() + "," + obj.getReceiveDate() + "\n";
                }
                bw.write(dataFile);
                bw.flush();
                bw.close();
                response.setContentType("application/download");
                response.setHeader("Content-Disposition",
                     "attachment;filename=" + "report.csv");
                OutputStream out = response.getOutputStream();
                FileInputStream fileStream = new FileInputStream(new File(folder +"/report.csv"));
                byte[] buffer = new byte[1024];
                int length = 0;
                while((length = fileStream.read(buffer)) > 0){
                    out.write(buffer, 0, length);
                }
                fileStream.close();
                number = tblNotification.size();
            }else{
                db.open();
                number = db.getUserChargedInDateNumber(dateRegister, dateCharge);
                db.close();
                requestDispatcher.forward(request, response);
            }
        }catch(Exception e){
            db.open();
            number = db.getUserChargedInDateNumber(dateRegister, dateCharge);
            db.close();
            requestDispatcher.forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
