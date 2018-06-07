/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.message;

import com.google.gson.GsonBuilder;
import com.khtm.object.SMSObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
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
@WebServlet(name = "BulkMessage", urlPatterns = {"/BulkMessage"})
@MultipartConfig
public class BulkMessage extends HttpServlet {

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
        boolean bSuccess = false;
        PrintWriter printWriter = response.getWriter();
        printWriter.println("Please wait ... <br/>");
        Part filePart = request.getPart("file");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        InputStream fileContent = filePart.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(fileContent));
        List<String> lst = new ArrayList<>();
        String line = "";
        printWriter.println("Starting to reading file <" + fileName + "> ...<br/>");
        while((line = br.readLine()) != null){
            lst.add(line);
        }
        printWriter.println("reading file have been finish.<br/>");
        
        
        Part fileMessage = request.getPart("message");
        String fileMessageName = Paths.get(fileMessage.getSubmittedFileName()).getFileName().toString();
        InputStream input_message = fileMessage.getInputStream();
        BufferedReader br_Message = new BufferedReader(new InputStreamReader(input_message));
        StringBuilder sb = new StringBuilder();
        String messageLine = "";
        printWriter.println("Starting to reading file <" + fileMessageName + "> ...<br/>");
        while((messageLine = br_Message.readLine()) != null){
            sb.append(messageLine+"\n");
        }
        String message = sb.toString();
        printWriter.println("Reading Message file have been finish.<br/>");
        
        printWriter.println("Sending them to QUEUE<br/>Just a moment ...");
        
        ActiveMQConnectionFactory factory = 
                new ActiveMQConnectionFactory(amq_username, amq_password, "tcp://localhost:61616");
        Connection connection = null;
        try{
            connection = factory.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("Q-SMS");
            MessageProducer producer = session.createProducer(destination);
            for(String str : lst){
                String jsonMsg = 
                        new GsonBuilder().create().toJson(
                                new SMSObject(serviceCode, str, message), SMSObject.class
                        );
                TextMessage myMessage = session.createTextMessage(jsonMsg);
                producer.send(myMessage);
            }
            producer.close();
            session.close();
            bSuccess = true;
        }catch(JMSException e){
            
        }finally{
            try{connection.close();}catch(JMSException ee){}
        }
        
        if(bSuccess){
            printWriter.println("Successfully send all message to QUEUE. :-)<br/>");
        }else{
            printWriter.println("Sorry! proccess crashed. :-(<br/>");
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
