<%-- 
    Document   : watchForm
    Created on : Jul 5, 2021, 6:48:26 PM
    Author     : Tan Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create form</title>
    </head>
    <body>
        <h2>Create new watch</h2>
        
        <font color="red">${error}</font>
        <form action="InsertServlet" method="POST" enctype="multipart/form-data">
            <table>
                <tr>
                    <td>Watch id :</td>
                    <td><input type="text" value="" name="id" required="" minlength="3" maxlength="10"></td>
                </tr>
                <tr>
                    <td>Name :</td>
                    <td><input type="text" value="" name="name" required="" minlength="5" maxlength="25"></td>
                </tr>
                <tr>
                    <td>Manufacturer :</td>
                    <td><input type="text" value="" name="manufacturer" required=""></td>
                </tr>
                <tr>
                    <td>Description :</td>
                    <td><input type="text" value="" name="description" required=""></td>
                </tr>
                <tr>
                    <td>Price :</td>
                    <td><input type="number" value="" name="price" required="" min="0" step="1"></td>
                </tr>
                <tr>
                    <td>Quantity :</td>
                    <td><input type="number" value="" name="quantity" required="" min="0" step="1"></td>
                </tr>
                <tr>
                    <td>Sale :</td>
                    <td><select nam="sale">
                            <option value="true">True</option>
                            <option value="false">False</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Url image :</td>
                    <td><input type="file" accept=".png" name="photo" required=""></td>
                </tr>
            </table>
            <input type="submit" value="Create">
        </form>
    </body>
</html>
