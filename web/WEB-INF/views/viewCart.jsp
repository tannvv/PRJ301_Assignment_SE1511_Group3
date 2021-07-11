<%-- 
    Document   : viewCart
    Created on : Jul 5, 2021, 9:03:40 PM
    Author     : Tan Nguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart</title>
        <style type="text/css">
         
            section{
                padding-top: 30px;
            }
            .img{
                text-align: center;
                border: 1px solid green;
                padding:5px;
                margin : 5px;
                height: 250px;
                width: 200px;
                float: left;
            }
            footer{
                padding-top: 30px;
                clear: both;
                height: 120px;
                align-items: center;
            }
        </style>
    </head>
    <body>
        <header>
            <img src="images/watch_main.png" width="40%" height="150px">
        </header>
        <h2 align="center">List cart</h2>
        <section>
            <table border="1px solid green" align="center" width="90%">
                <tr>
                    <th>Watch id </th>
                    <th>Name </th>
                    <th>Image</th>
                    <th>Manufacturer </th>
                    <th>Description </th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total cost</th>
                </tr>

                <c:forEach items="${sessionScope.listCart}" var="cart">
                    <tr>
                        <th align="center">${cart.watch.watchId}</th>
                        <td>${cart.watch.watchName}</td>
                        <td><img src="images/${cart.watch.urlImage}" width="100"></td>
                        <td>${cart.watch.manufacturer}</td>
                        <td>${cart.watch.description}</td>
                        <td>${cart.watch.price}</td>
                        <td>${cart.quantity}</td>
                        <td>${cart.quantity * cart.watch.price}</td>
                        <td align="center"><a href="CartServlet?action=remove&id=${cart.watch.watchId}"
                                              onclick="return confirm('Are you sure to delete !!!')" >Remove</a>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <th colspan="2">Total cost</th>
                    <th colspan="4">${totalCost}</th>
                </tr>
            </table>
                <font color="red">${errorPay}</font> </br>
                <form action="PayServlet" method="POST">
                    <label>Enter address to pay : </label><input type="tetx" required="" name="address"><input type="submit" value="Pay">
                </form>

        </section>
        <footer align="right">
            <div class="container" style="background-color:#f1f1f1">
                </br>Hello ${userData.fullName}- <a href="LoadListServlet">View smart watch</a>
                - <a href="LogoutServlet">Log out</a>   </br></br>
            </div>
        </footer>
    </body>
</html>
