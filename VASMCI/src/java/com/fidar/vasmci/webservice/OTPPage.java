/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.vasmci.webservice;

import com.fidar.queue.handler.QueueHandler;
import com.fidar.queue.object.OTPObject;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alirezakhtm
 */
@WebServlet(name = "OTPPage", urlPatterns = {"/OTPPage"})
public class OTPPage extends HttpServlet {

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
        // this page designed for receive data of OTP and manage them for next proccess
        //http://yoursite/yourPage?otpId=@OtpId&statusId=@StatusId&recipient=@Recipient
        response.setContentType("text/html;charset=UTF-8");
        String otpId = request.getParameter("otpId");
        String statusId = request.getParameter("statusId");
        String recipient = request.getParameter("recipient");
        OTPObject otpo = new OTPObject(otpId, statusId, recipient);
        QueueHandler QHandler = new QueueHandler();
        QHandler.InsertToOTPQueue(otpo);
        String strJson = QHandler.getJsonObject(otpo);
        try(PrintWriter pw = response.getWriter()){
            pw.println(strJson);
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
