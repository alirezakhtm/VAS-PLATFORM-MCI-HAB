/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.app.ws;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.OTPLogObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alireza
 */
@WebServlet(name = "ConfirmOTP", urlPatterns = {"/confirmotp"})
public class ConfirmOTP extends HttpServlet {

    private static final String TOKEN = "FIDAR123MLmkOsKKMc786231QweXjzPPLLARKHTM";
    private DatabaseHandler db = new DatabaseHandler();
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
        String strAns = "";
        try{
            String strToken = request.getParameter("token");
            String strOtpId = request.getParameter("otpid");
            if(strToken.equals(TOKEN)){
                // get result from tbl_otp_log by otpid
                db.open();
                OTPLogObject otplo = db.getOTPLog(strOtpId);
                db.close();
                strAns = (new GsonBuilder().create()).toJson(otplo, OTPLogObject.class);
            }else{
                strAns = "{\"rsp\":\"token is not valid\"}";
            }
        }catch(Exception e){
            strAns = "{\"rsp\":\"ERROR\"}";
        }
        // print for user
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println(strAns);
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
