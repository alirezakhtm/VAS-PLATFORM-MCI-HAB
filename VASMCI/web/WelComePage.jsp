<%-- 
    Document   : WelComePage
    Created on : Jan 7, 2018, 4:16:01 PM
    Author     : alirezakhtm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/bootstrap.min.css">
        <script src="style/jquery.min.js"></script>
        <script src="style/popper.min.js"></script>
        <script src="style/bootstrap.min.js"></script>
        <title>JSP Page</title>
    </head>
    <body>
        <div class="container">
            <h2>Callable Pages for MCI</h2>
            <p>This web application designed for VAS PLATFORM MCI- HUB</p>            
            <table class="table table-success table-hover">
              <thead>
                <tr>
                  <th>Page Name</th>
                  <th>Function</th>
                  <th>Themplate</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>OTPPage</td>
                  <td>Called when user subscribe or unsubscribe from service.</td>
                  <td>http://localhost:8080/VASMCI/OTPPage</td>
                </tr>
                <tr>
                  <td>ReceiveMsg</td>
                  <td>Called when one message received.</td>
                  <td>http://localhost:8080/VASMCI/ReceiveMsg</td>
                </tr>
              </tbody>
            </table>
         </div>

    </body>
</html>
