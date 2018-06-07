/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.khtm.report;

import com.khtm.database.DBHandler;
import com.khtm.object.ServiceUserObj;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "Search", urlPatterns = {"/Search"})
public class Search extends HttpServlet {

    private DBHandler db = new DBHandler();
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
        String phone = request.getParameter("phone");
        db.open();
        ServiceUserObj suo = db.getRecordSub(phone);
        db.close();
        strTable = "<table class=\"table table-primary table-striped\">\n" +
"                <thead>\n" +
"                    <tr>\n" +
"                        <th>MSISDN</th>\n" +
"                        <th>Sub Date</th>\n" +
"                        <th>Unsub Date</th>\n" +
"                        <th>Status</th>\n" +
"                    </tr>\n" +
"                </thead>\n" +
"                <tbody>\n" +
                "<tr>" +
                "<td>"+suo.getMsisdn()+"</td>" +
                "<td>"+suo.getMembershipDate()+"</td>" +
                "<td>"+suo.getUnsubDate()+"</td>" +
                "<td>"+suo.getSubStatus()+"</td>" +
                "</tr>"+
"                </tbody>\n" +
"            </table>";
        RequestDispatcher dispatcher = request.getRequestDispatcher("report.jsp");
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
