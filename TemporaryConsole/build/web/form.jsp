<%-- 
    Document   : form
    Created on : May 20, 2018, 10:11:54 AM
    Author     : alireza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome Dear Maryam :-)</h1>
        <p>This page contain two forms. first form for testing message to specific 
            phone number and second for BULK message for uploaded phone number.</p>
        
        <%
            try{
                String message = (String) session.getAttribute("message");
                if(message != null && message != ""){
        %>
        <br/><p style="color:green"><% out.print(message); %></p><br/>
        <%
                }
            }catch(Exception e){}
        %>
        
        
        <table border="1" cellspace="25" cellpadding="25">
            <tr>
                <td>
                    <!-- test form -->
                    <form method="get" action="SingleMessage">
                        <table>
                            <tr>
                                <td><h2>Single Message</h2></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><lable for="phone-test-form">Tel:</lable></td>
                                <td><input type="text" id="phone-test-form" name="phone"></td>
                            </tr>
                            <tr>
                                <td><lable for="message-test-form">Message:</lable></td>
                                <td><textarea id="message-test-form" name="message" rows="12" cols="30"></textarea></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="submit" value="Send Message"></td>
                            </tr>
                        </table>
                    </form>                    
                </td>
                <td>
                    <!-- BULK form -->
                    <form method="post" action="BulkMessage" enctype="multipart/form-data">
                        <table>
                            <tr>
                                <td><h2>BULK Message</h2></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td><lable for="upload-file">File:</lable></td>
                                <td><input type="file" id="upload-file" name="file"></td>
                            </tr>
                            <tr>
                                <td><lable for="message-form">Message:</lable></td>
                                <td><input type="file" id="message-form" name="message"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="submit" value="Send Bulk Message"></td>
                            </tr>
                        </table>
                    </form>         
                </td>
                <td>
                    <!-- Rapid bulk sms -->
                    <form method="post" action="RapidBulkMessage" enctype="multipart/form-data">
                        <table cellpadding="5">
                            <tr>
                                <td><img src="rocket-icon.jpg" alt="rocket" height="60" width="60"/></td>
                                <td><h2>Rapid BULK SMS</h2></td>
                            </tr>
                            <tr>
                                <td><label for="rapid-phone-file">Phones:</label></td>
                                <td><input type="file" id="rapid-phone-file" name="phoneFile"></td>
                            </tr>
                            <tr>
                                <td><label for="rapid-message-file">Message:</label></td>
                                <td><input type="file" id="rapid-message-file" name="messageFile"></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="submit" value="Send Rapid Bulk Message"></td>
                            </tr>
                        </table>
                    </form>
                </td>
            </tr>
        </table>
        <br/>
        <hr/>
        <br/>
        
    </body>
</html>
