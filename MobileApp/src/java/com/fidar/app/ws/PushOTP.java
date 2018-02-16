/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fidar.app.ws;

import com.fidar.database.handler.DatabaseHandler;
import com.fidar.database.object.OTPReqObject;
import com.fidar.mci.hub.webservice.handler.WSHandler;
import com.fidar.mci.hub.webservice.handler.WSXMLGenerator;
import com.fidar.mci.hub.webservice.object.PushOTPResponse;
import com.fidar.queue.object.ReceiveMsgObject;
import com.fidar.queue.object.SubUserObject;
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
@WebServlet(name = "PushOTP", urlPatterns = {"/pushotp"})
public class PushOTP extends HttpServlet {

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
        try{
            String strToken = request.getParameter("token");
            String strMsisdn = request.getParameter("msisdn");
            String strShortCode = request.getParameter("shortcode");
            String strStatus = request.getParameter("status");
            int iServiceCode = Integer.parseInt(request.getParameter("servicecode"));

            String strAns = "";
            if(strToken.equals(TOKEN)){
                // set cost parameter for subscription or unsubscription
                int iCost = (strStatus.toLowerCase().equals("subscription")) ? 5 : 6;
                // call web service for subscribe user
                WSXMLGenerator wsxmlg = new WSXMLGenerator();
                // subscribe new user to service
                SubUserObject subUserObject = new SubUserObject(iServiceCode, new ReceiveMsgObject("", strMsisdn, strShortCode, "0", "0"));
                String strOrder = wsxmlg.getPushOTP(subUserObject, iCost);
                String WSResponce = WSHandler.sendToWebServiceHUB(strOrder);
                // Analysis webservice response
                PushOTPResponse potpr = wsxmlg.getPushOTPResponse(WSResponce);
                response.setContentType("text/html;charset=UTF-8");
                strAns = (new GsonBuilder().create()).toJson(potpr, PushOTPResponse.class);
                // save request to database
                OTPReqObject otpro = new OTPReqObject(
                        strMsisdn,
                        strShortCode,
                        strStatus,
                        iServiceCode+"",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()),
                        potpr.getBody().getPushOtpResponse().getValue()
                );
                db.open();
                db.saveOTPRequest(otpro);
                db.close();
            }else{
                strAns = "{\"rsp\":\"token is not valid\"}";
            }

            try (PrintWriter out = response.getWriter()) {

                out.println(strAns);
            }
        }catch(Exception e){
            try(PrintWriter pw = response.getWriter()){
                pw.println("{\"rsp\":\"ERROR\"}");
            }
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
