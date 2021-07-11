<%-- 
    Document   : index
    Created on : Jul 5, 2021, 2:30:58 PM
    Author     : Tan Nguyen
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main page</title>
        <style type="text/css">
            header{
                height: 120px;
                align-items: center;
            }
            section{
                padding-left: 5%;
                padding-bottom: 40px;
                padding-top: 10px;
            }
            .img{
                text-align: center;
                border : 1px solid green;
                padding: 5px;
                margin: 5px;
                height: 250px;
                width: 200px;
                float: left;
            }
            footer{
                padding-top: 50px;
                clear:both;
                height: 120px;
                align-items: center;
            }
            #watch{
                padding-bottom: 10px;
                padding-top: 10px;
            }
            .header{
                padding-left: 13%;
                padding-bottom: 30px;
            }
            h2{
                padding-left: 40%;
                padding-bottom: 30px;
            }
            .search{
                padding-left: 7%;
                padding-bottom: 20px;
            }
            font{
                padding-left: 70px;
                font-size: 20px;
            }
          
        </style>
    </head>
    <body>
        <img src="images/watch_main.jpg" width="70%" height="150px" class="header">
        <h2>Smart watch shopping</h2>
        <form action="SearchServlet" method="POST">
            <input type="hidden" value="manufacturer" name="action">
            <label class="search">Search with manufacturer : </label>
            <select name="manufacturer">
                <option value="Samsung">Samsung</option>
                <option value="Huawei">Huawei</option>
                <option value="Apple">Apple</option>
                <option value="Garmin">Garmin</option>
            </select>
            <input type="submit" value="Search">
        </form>
        <font color="green">${notifi}</font>
        <section>
            <c:forEach items="${listWatch}" var="watch">
                <div class="img">
                    ${watch.watchId}-${watch.watchName}</br>
                    <img id="watch" src="images/${watch.urlImage}" width="120" height="150"></br>
                    ${watch.description} - ${watch.manufacturer}</br>
                    ${watch.price}</br>
                    <a href="CartServlet?action=buy&id=${watch.watchId}">Add to cart</a>
                </div>
            </c:forEach>
        </section>
        </br></br></br>
        <footer>
            <div class="container" style="background-color:#f1f1f1">
                </br>Hello ${userData.fullName}-<a href="LoadListServlet">View list product</a> - <a href="CartServlet?action=view">View shopping cart</a> - <a href="HistoryOrderServlet">View history order</a>
                    - <a href="LogoutServlet">Log out</a>   </br></br>
             </div>
        </footer>
    </body>
</html>
