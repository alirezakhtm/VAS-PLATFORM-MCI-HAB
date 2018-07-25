<%-- 
    Document   : report
    Created on : May 25, 2018, 9:36:31 PM
    Author     : alireza
--%>

<%@page import="com.khtm.report.Search"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    try{
        String str = (String)session.getAttribute("pass");
        if(!str.equals("alirezakhtm@rahkar1397") && !str.equals("jayeze@rahkar1397")){
            RequestDispatcher dispatcher = request.getRequestDispatcher("default.jsp");
            dispatcher.forward(request, response);
        }
    }catch(Exception e){
        RequestDispatcher dispatcher = request.getRequestDispatcher("default.jsp");
        dispatcher.forward(request, response);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Service Report</title>
        <link rel="stylesheet" href="bootstrap.min.css">
        <script src="jquery.min.js"></script>
        <script src="popper.min.js"></script>
        <script src="bootstrap.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h2>Rahkar Co.</h2>
            <p>Report of <b>JAYEZE AVAL</b></p>
            <%
                out.print(Search.strTable);
            %>
        </div>
    </body>
</html>
