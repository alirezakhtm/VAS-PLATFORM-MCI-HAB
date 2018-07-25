<%-- 
    Document   : customercharge
    Created on : Jun 12, 2018, 12:00:00 PM
    Author     : alirzea
--%>

<%@page import="com.khtm.report.CustomerCharge"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
            <br/>
            <div class="row">
                <div class="col-md-12">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Value</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Date of Subscription</td>
                                <td><% out.print(CustomerCharge.dateRegister); %></td>
                            </tr
                            <tr>
                                <td>Date of Charge</td>
                                <td><% out.print(CustomerCharge.dateCharge); %></td>
                            </tr>
                            <tr>
                                <td>Customer Number</td>
                                <td><% out.print(CustomerCharge.number); %></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><a href=<% out.print("CustomerCharge?action=download&dateRegister="+CustomerCharge.dateRegister+"&dateCharge=" + CustomerCharge.dateCharge); %> class="btn btn-primary">Download File</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
