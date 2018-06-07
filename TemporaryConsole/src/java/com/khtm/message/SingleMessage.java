/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.khtm.object.SMSObject;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alireza
 */
@WebServlet(name = "SingleMessage", urlPatterns = {"/SingleMessage"})
public class SingleMessage extends HttpServlet {

    private final String amq_username = "admin";
    private final String amq_password = "alireza@2413";
    
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
        boolean bSuccess = false;
        String phone = request.getParameter("phone");
        String message = request.getParameter("message");
        String serviceCode = "1";
        SMSObject so = new SMSObject(serviceCode, phone, message);
        Gson gson = new GsonBuilder().create();
        String jsonSMS = gson.toJson(so, SMSObject.class);
        ActiveMQConnectionFactory factory = 
                    new ActiveMQConnectionFactory(amq_username, amq_password, "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("Q-SMS");
            MessageProducer producer = session.createProducer(destination);
            TextMessage myMessage = session.createTextMessage(jsonSMS);
            producer.send(myMessage);
            producer.close();
            session.close();
            connection.close();
            bSuccess = true;
        }catch(JMSException e){
            System.err.println("[*] ERROR - SingleMessage : " + e);
        }finally{
            try{
                connection.close();
            }catch(JMSException e){}
        }
        
        RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession();
        if(bSuccess){
            session.setAttribute("message", "Message send successfully.");
            dispatcher = request.getRequestDispatcher("form.jsp");
            dispatcher.forward(request, response);
        }else{
            session.setAttribute("message", "Message send failed.");
            dispatcher = request.getRequestDispatcher("form.jsp");
            dispatcher.forward(request, response);
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
