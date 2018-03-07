<%-- 
    Document   : index
    Created on : Jan 9, 2018, 7:32:00 PM
    Author     : alirezakhtm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
  RequestDispatcher requestDispatcher = request.getRequestDispatcher("WelComePage.jsp");
  requestDispatcher.forward(request, response);
%>
