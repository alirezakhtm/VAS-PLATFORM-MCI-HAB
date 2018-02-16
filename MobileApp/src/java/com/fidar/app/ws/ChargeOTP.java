/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.app.ws;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.ChargeOTPObject;
import com.fidar.mci.hub.webservice.handler.WSHandler;
import com.fidar.mci.hub.webservice.handler.WSXMLGenerator;
import com.fidar.mci.hub.webservice.object.ChargeOTPResponse;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alireza
 */
@WebServlet(name = "ChargeOTP", urlPatterns = {"/chargeotp"})
public class ChargeOTP extends HttpServlet {

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
            String strMsisdn = request.getParameter("msisdn");
            String strShortCode = request.getParameter("shortcode");
            int iServiceCode = Integer.parseInt(request.getParameter("servicecode"));
            String pinCode = request.getParameter("pin");
            String optId = request.getParameter("otpid");
            if(strToken.equals(TOKEN)){
                ChargeOTPObject cotpo = new ChargeOTPObject(strMsisdn, strShortCode, iServiceCode+"", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()), pinCode, optId);
                db.open();
                db.saveChargeOTP(cotpo);
                db.close();
                WSXMLGenerator xMLGenerator = new WSXMLGenerator();
                String reqWS = xMLGenerator.getChargeOTP(cotpo);
                String rspWS = WSHandler.sendToWebServiceHUB(reqWS);
                ChargeOTPResponse cotpr = xMLGenerator.getChargeOTPResponse(rspWS);
                strAns = (new GsonBuilder().create()).toJson(cotpr, ChargeOTPResponse.class);
            }else{
                strAns = "{\"rsp\":\"token is not valid\"}";
            }
        }catch(Exception e){
            strAns = "{\"rsp\":\"ERROR\"}";
        }
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
