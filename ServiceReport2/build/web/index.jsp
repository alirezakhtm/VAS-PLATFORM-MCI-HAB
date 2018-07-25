<%-- 
    Document   : index
    Created on : May 25, 2018, 3:07:18 PM
    Author     : alireza
--%>

<%@page import="com.khtm.report.Security"%>
<%@page import="com.khtm.report.Home"%>
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
            <p>Report of <b>STICKERAM</b></p>    
            <p>Total User : <% out.print(Home.totalUser);%></p>
            <p>Active User : <% out.print(Home.activeUser);%></p>
            <table class="table table-primary table-striped">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Sub. NO.</th>
                        <th>Unsub. NO.</th>
                        <% if(Security.accessLevel == 0){ %>
                        <th>Bad User NO.</th>
                        <th>Renewal</th>
                        <% } %>
                    </tr>
                </thead>
                <tbody>
                    <%
                        out.print(Home.strTable);
                    %>
                </tbody>
            </table>
            <br/>
            <% 
                if(Security.accessLevel == 0){ 
            %>
                <div class="row">
                    <div class="col-md-6">
                        <form class="form-group" action="Search">
                            <label for="phone">Phone:</label>
                            <input type="tel" class="form-control" id="phone" placeholder="91********" name="phone">
                            <br/>
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>
                    <div class="col-md-6">
                        <form class="form-group" action="CustomerCharge" method="post">
                            <label for="dateRegister">Date of Register:</label>
                            <input type="date" class="form-control" id="dateRegister" placeholder="2018-01-01" name="dateRegister">
                            <label for="dateCharge">Date of Charge:</label>
                            <input type="date" class="form-control" id="dateRegister" placeholder="2018-01-01" name="dateCharge">
                            <br/>
                            <button type="submit" class="btn btn-primary">Search</button>
                        </form>
                    </div>
                </div>
            <%
                }
            %>
            <br/>
            <br/>
        </div>
        
    </body>
</html>
