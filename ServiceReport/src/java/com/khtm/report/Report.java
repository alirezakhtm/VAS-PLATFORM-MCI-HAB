/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.report;

import com.khtm.database.DBHandler;
import com.khtm.object.NotificationObj;
import com.khtm.object.ServiceUserObj;
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
 * @author alireza
 */
@WebServlet(name = "Report", urlPatterns = {"/Report"})
public class Report extends HttpServlet {

    private DBHandler db = new DBHandler();
    
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
        if(Security.accessLevel == 0){
            String date = request.getParameter("date");
            String action = request.getParameter("action");
            switch(action){
                case "baduser":
                    db.open();
                    List<ServiceUserObj> lst = db.getListBadUser(date);
                    db.close();
                    String folder = UUID.randomUUID() + "";
                    new File(folder).mkdir();
                    BufferedWriter bw = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(
                                    new File(folder +"/report_bad_user_"+date+".csv"))));
                    String dataFile = "ServiceCode,MSISDN,SubStatus,MembershipDate,"
                            + "UnSubscribeDate,selectedCategoryIndx,unsubscribedBy,subscribedBy,numberType\n";
                    for(ServiceUserObj suo : lst){
                        dataFile += suo.getServiceCode()+","+suo.getMsisdn()+","+suo.getSubStatus()+","+suo.getMembershipDate()+","
                            + suo.getUnsubDate() +","+suo.getSelctedCategoryIndex()+","+suo.getUnsubBy()+","+suo.getSubBy()+","+suo.getNumberType()+"\n";
                    }
                    bw.write(dataFile);
                    bw.flush();
                    bw.close();
                    response.setContentType("application/download");
                    response.setHeader("Content-Disposition",
                         "attachment;filename=" + "report_bad_user_"+date+".csv");
                    OutputStream out = response.getOutputStream();
                    FileInputStream fileStream = new FileInputStream(new File(folder +"/report_bad_user_"+date+".csv"));
                    byte[] buffer = new byte[1024];
                    int length = 0;
                    while((length = fileStream.read(buffer)) > 0){
                        out.write(buffer, 0, length);
                    }
                    fileStream.close();
                    break;
                case "subuser":
                    db.open();
                    List<ServiceUserObj> lst_sub = db.getSubUser(date);
                    db.close();
                    String folder_sub = UUID.randomUUID() + "";
                    new File(folder_sub).mkdir();
                    BufferedWriter bw_sub = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(
                                    new File(folder_sub +"/report_sub_user_"+date+".csv"))));
                    String dataFile_sub = "ServiceCode,MSISDN,SubStatus,MembershipDate,"
                            + "UnSubscribeDate,selectedCategoryIndx,unsubscribedBy,subscribedBy,numberType\n";
                    for(ServiceUserObj suo : lst_sub){
                        dataFile_sub += suo.getServiceCode()+","+suo.getMsisdn()+","+suo.getSubStatus()+","+suo.getMembershipDate()+","
                            + suo.getUnsubDate() +","+suo.getSelctedCategoryIndex()+","+suo.getUnsubBy()+","+suo.getSubBy()+","+suo.getNumberType()+"\n";
                    }
                    bw_sub.write(dataFile_sub);
                    bw_sub.flush();
                    bw_sub.close();
                    response.setContentType("application/download");
                    response.setHeader("Content-Disposition",
                         "attachment;filename=" + "report_sub_user_"+date+".csv");
                    OutputStream out_sub = response.getOutputStream();
                    FileInputStream fileStream_sub = new FileInputStream(new File(folder_sub +"/report_sub_user_"+date+".csv"));
                    byte[] buffer_sub = new byte[1024];
                    int length_sub = 0;
                    while((length_sub = fileStream_sub.read(buffer_sub)) > 0){
                        out_sub.write(buffer_sub, 0, length_sub);
                    }
                    fileStream_sub.close();
                    break;
                case "unsubuser":
                    db.open();
                    List<ServiceUserObj> lst_unsub = db.getUnSubUser(date);
                    db.close();
                    String folder_unsub = UUID.randomUUID() + "";
                    new File(folder_unsub).mkdir();
                    BufferedWriter bw_unsub = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(
                                    new File(folder_unsub +"/report_unsub_user_"+date+".csv"))));
                    String dataFile_unsub = "ServiceCode,MSISDN,SubStatus,MembershipDate,"
                            + "UnSubscribeDate,selectedCategoryIndx,unsubscribedBy,subscribedBy,numberType\n";
                    for(ServiceUserObj suo : lst_unsub){
                        dataFile_unsub += suo.getServiceCode()+","+suo.getMsisdn()+","+suo.getSubStatus()+","+suo.getMembershipDate()+","
                            + suo.getUnsubDate() +","+suo.getSelctedCategoryIndex()+","+suo.getUnsubBy()+","+suo.getSubBy()+","+suo.getNumberType()+"\n";
                    }
                    bw_unsub.write(dataFile_unsub);
                    bw_unsub.flush();
                    bw_unsub.close();
                    response.setContentType("application/download");
                    response.setHeader("Content-Disposition",
                         "attachment;filename=" + "report_unsub_user_"+date+".csv");
                    OutputStream out_unsub = response.getOutputStream();
                    FileInputStream fileStream_unsub = new FileInputStream(new File(folder_unsub +"/report_unsub_user_"+date+".csv"));
                    byte[] buffer_unsub = new byte[1024];
                    int length_unsub = 0;
                    while((length_unsub = fileStream_unsub.read(buffer_unsub)) > 0){
                        out_unsub.write(buffer_unsub, 0, length_unsub);
                    }
                    fileStream_unsub.close();
                    break;
                case "renewal":
                    db.open();
                    List<NotificationObj> lst_renewal = db.getRenewal(date);
                    db.close();
                    String folder_renewal = UUID.randomUUID() + "";
                    new File(folder_renewal).mkdir();
                    BufferedWriter bw_renewal = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(
                                    new File(folder_renewal +"/report_renewal_"+date+".csv"))));
                    String dataFile_renewal = "Text,Keyword,Channel,From,To,NotificationId,UserId,ReceiveDate\n";
                    for(NotificationObj nfo : lst_renewal){
                        dataFile_renewal += nfo.getText()+","+nfo.getKeyword()+","+nfo.getChannel()
                                +","+nfo.getFrom()+","+nfo.getTo()+","+nfo.getNotificationId()+","
                                +nfo.getUserId()+","+nfo.getReceiveDate()+"\n";
                    }
                    bw_renewal.write(dataFile_renewal);
                    bw_renewal.flush();
                    bw_renewal.close();
                    response.setContentType("application/download");
                    response.setHeader("Content-Disposition",
                         "attachment;filename=" + "report_renewal_"+date+".csv");
                    OutputStream out_renewal = response.getOutputStream();
                    FileInputStream fileStream_renewal = new FileInputStream(new File(folder_renewal +"/report_renewal_"+date+".csv"));
                    byte[] buffer_renewal = new byte[1024];
                    int length_renewal = 0;
                    while((length_renewal = fileStream_renewal.read(buffer_renewal)) > 0){
                        out_renewal.write(buffer_renewal, 0, length_renewal);
                    }
                    fileStream_renewal.close();
                    break;
            }
        }else{
            RequestDispatcher dispatcher = request.getRequestDispatcher("default.jsp");
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
