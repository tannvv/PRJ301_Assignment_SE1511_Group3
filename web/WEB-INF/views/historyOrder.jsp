<%-- 
    Document   : historyOrder
    Created on : Jul 6, 2021, 11:51:21 AM
    Author     : Tan Nguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History order</title>
    </head>
    <body>
        <img src="images/watch_main.jpg" width="70%" height="150px" class="header">
        <h2>History order</h2>
        <font color="red">${error}</font>
        <table border="1px solid green">
            <thead>
                <tr>
                <th>Order id</th>
                <th>Address</th>
                <th>Order day</th>
                <th>Total Cost</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listOrder}" var="order">
                <tr>
                    <th>${order.orderId}</th>
                    <td>${order.address}</td>
                    <td>${order.orderDay}</td>
                    <td>${order.totalCost}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <footer>
            <div class="container" style="background-color:#f1f1f1">
                </br>Hello ${userData.fullName}- <a href="CartServlet?action=view">View shopping cart</a>-<a href="LoadListServlet">Main page</a>
                    - <a href="LogoutServlet">Log out</a>   </br></br>
             </div>
        </footer>
    </body>
</html>
