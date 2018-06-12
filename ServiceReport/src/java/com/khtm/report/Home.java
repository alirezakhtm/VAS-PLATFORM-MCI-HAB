/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.report;

import com.khtm.database.DBHandler;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
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
@WebServlet(name = "Home", urlPatterns = {"/Home"})
public class Home extends HttpServlet {

    private DBHandler db = new DBHandler();
    public static int totalUser = 0, activeUser = 0;
    public static String strTable = "";
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
        strTable = "";
        List<tableData> lst = new LinkedList<>();
        db.open();
        totalUser = db.getTotalUser();
        db.close();
        db.open();
        activeUser = db.getActiveUser();
        db.close();
        List<String> lstDate = TimeManager.getDateListProcess();
        for(String date : lstDate){
            db.open();
            int sub = db.getSubNumber(date);
            db.close();
            db.open();
            int unsub = db.getUnSubNumber(date);
            db.close();
            db.open();
            int badUser = db.getBadUserNumber(date);
            db.close();
            db.open();
            int renewalNO = db.getRenewalNumber(date);
            db.close();
            tableData td = new tableData(sub, unsub, badUser, renewalNO, date);
            lst.add(td);
        }
        for(tableData t : lst){
            if(Security.accessLevel == 0){
                strTable += "<tr>\n" +
    "                        <td>"+t.getDate()+"</td>\n" +
    "                        <td><a href=\"Report?action=subuser&date="+t.getDate()+"\">"+t.getSubNO()+"</a></td>\n" +
    "                        <td><a href=\"Report?action=unsubuser&date="+t.getDate()+"\">"+t.getUnsubNO()+"</a></td>\n" +
    "                        <td><a href=\"Report?action=baduser&date="+t.getDate()+"\">"+t.getBadUserNO()+"</a></td>\n" +
    "                        <td><a href=\"Report?action=renewal&date="+t.getDate()+"\">"+t.getRenewalNO()+"</a></td>" +
    "                    </tr>\n";
            }else{
                strTable += "<tr>\n" +
    "                        <td>"+t.getDate()+"</td>\n" +
    "                        <td><a href=\"Report?action=unsubuser&date="+t.getDate()+"\">"+t.getUnsubNO()+"</a></td>\n" +
    "                        <td><a href=\"Report?action=baduser&date="+t.getDate()+"\">"+t.getBadUserNO()+"</a></td>\n" +
    "                    </tr>\n";
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
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

class tableData {
    private int subNO, unsubNO, badUserNO, renewalNO;
    private String date;

    public tableData(int subNO, int unsubNO, int badUserNO, int renewalNO, String date) {
        this.subNO = subNO;
        this.unsubNO = unsubNO;
        this.badUserNO = badUserNO;
        this.renewalNO = renewalNO;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public tableData() {
    }
    
    public int getSubNO() {
        return subNO;
    }

    public void setSubNO(int subNO) {
        this.subNO = subNO;
    }

    public int getUnsubNO() {
        return unsubNO;
    }

    public void setUnsubNO(int unsubNO) {
        this.unsubNO = unsubNO;
    }

    public int getBadUserNO() {
        return badUserNO;
    }

    public void setBadUserNO(int badUserNO) {
        this.badUserNO = badUserNO;
    }

    public int getRenewalNO() {
        return renewalNO;
    }

    public void setRenewalNO(int renewalNO) {
        this.renewalNO = renewalNO;
    }
    
}