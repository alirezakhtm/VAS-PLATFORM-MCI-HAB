<%-- 
    Document   : index
    Created on : May 20, 2018, 9:59:43 AM
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
        <h1>Login Page</h1>
        <form method="post" action="UserLogin">
            <table>
                <tr>
                    <td><label for="username-input">Username:</label></td>
                    <td>
                        <input type="text" name="username" id="username-input">
                    </td>
                </tr>
                <tr>
                    <td><label for="password-input">Password:</label></td>
                    <td>
                        <input type="password" name="password" id="password-input">
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Login"></td>
                </tr>
            </table>
            
        </form>
    </body>
</html>
