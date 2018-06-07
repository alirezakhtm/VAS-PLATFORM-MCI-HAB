/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.message;

import com.google.gson.GsonBuilder;
import com.khtm.object.BulkObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 * @author alireza
 */
@WebServlet(name = "RapidBulkMessage", urlPatterns = {"/RapidBulkMessage"})
@MultipartConfig
public class RapidBulkMessage extends HttpServlet {

    private final String amq_username = "admin";
    private final String amq_password = "alireza@2413";
    private final String serviceCode = "1";
    
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        // read file of phone
        Part filePhone = request.getPart("phoneFile");
        String nameOfFilePhone = Paths.get(filePhone.getSubmittedFileName()).getFileName().toString();
        out.println("Reading file [" + nameOfFilePhone + "]<br/>");
        InputStream inputFilePhone = filePhone.getInputStream();
        BufferedReader brFilePhone = new BufferedReader(new InputStreamReader(inputFilePhone));
        List<String> lst = new ArrayList<>();
        String line = "";
        while((line = brFilePhone.readLine()) != null){
            lst.add(line);
        }
        out.print("All phones inserted.<br/>");
        
        
        // read file of message
        Part fileMessage = request.getPart("messageFile");
        String nameOfFileMessage = Paths.get(fileMessage.getSubmittedFileName()).getFileName().toString();
        out.print("Reading file [" + nameOfFileMessage + "]<br/>");
        InputStream inputFileMessage = fileMessage.getInputStream();
        BufferedReader brFileMessage = new BufferedReader(new InputStreamReader(inputFileMessage));
        line = "";
        StringBuilder sb = new StringBuilder();
        while((line = brFileMessage.readLine()) != null){
            sb.append(line + "\n");
        }
        String message = sb.toString();
        out.print("Message inserted.<br/>");
        out.print("Creating JSON string.<br/>");
        // send to Queue
        BulkObject bulkObject = new BulkObject(lst, message, serviceCode);
        
        String jsonBulk = new GsonBuilder().create().toJson(bulkObject, BulkObject.class);
        out.print("JSON string have been created.<br/>");
        out.print("Inserting to QUEUE.<br/>");
        
        boolean bSuccess = false;
        ActiveMQConnectionFactory factory = 
                    new ActiveMQConnectionFactory(amq_username, amq_password, "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("Q-BULKSMS");
            MessageProducer producer = session.createProducer(destination);
            TextMessage myMessage = session.createTextMessage(jsonBulk);
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
        
        if(bSuccess){
            out.print("JSON string have been created.<br/>");
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
