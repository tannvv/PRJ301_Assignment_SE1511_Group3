<%-- 
    Document   : indexStaff
    Created on : Jul 5, 2021, 3:20:21 PM
    Author     : Tan Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
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
            .watch{
                height: 40px;
            }
            table{
                border-collapse:separate;
                border-spacing : 0px 15px;
                
            }
            font{
                padding-left: 100px;
            }
            .detail{
                border: 3px solid green;
            }
        </style>
    </head>
    <body>
        <img src="images/watch_main.jpg" width="70%" height="150px" class="header">
        <h2>Smart watch shopping</h2>
        <form action="SearchServlet" method="POST">
            <input type="hidden" value="idName" name="action">
            <label class="search">Search with name or id : </label>
            <input type="text" name="idName" required="">
            <input type="submit" value="Search">
        </form>
        
        <a href="InsertServlet" class="search">Insert new smart watch</a></br>
        <font color="red">${error}</font>
        <section>
            <table border="2px solid green" align="center" width="70%">
                <tr>
                    <th>Watch id </th>
                    <th>Name </th>
                    <th>Image</th>
                    <th>Manufacturer </th>
                    <th>Description </th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <td>Sale</th>
                </tr>

                <c:forEach items="${listWatch}" var="watch">
                    <form action="UpdateServlet" method="POST" enctype="multipart/form-data" width="70%">
                    <tr class="detail">
                        <th><input type="text" value="${watch.watchId}" readonly="" name="id"></th>
                        <td><input type="text" value="${watch.watchName}" required="" name="name" minlength="5" maxlength="25"></td>
                        <td><img src="images/${watch.urlImage}" width="100" class="watch" name="urlImage"></br>
                            <input type="file" name="photo" accept=".png" required="">
                        </td>
                        <td><input type="text" value="${watch.manufacturer}" required="" name="manufacturer"></td>
                        <td><input type="text" value="${watch.description}" required="" name="description"></td>
                        <td><input type="number" value="${watch.price}" required="" name="price" min="0" step="1"></td>
                        <td><input type="number" value="${watch.quantity}" required="" name="quantity" min="0" step="1"></td>
                        <td>
                            <select name="sale">
                                <option ${(watch.sale eq true)?"selected" : ""} value="true" >True</option>
                                <option ${(watch.sale eq false)?"selected" : ""} value="false">False</option>
                            </select>
                        </td>
                        <td><input type="submit" value="Update"></td>
                        <td align="center"><a href="DeleteServlet?id=${watch.watchId}"
                                              onclick="return confirm('Are you sure to delete !!!')" >Remove</a>
                        </td>
                    </tr>
                    </form>
                </c:forEach>
            </table>

        </section>
        <footer align="right">
            <div class="container" style="background-color:#f1f1f1">
                </br>Hello staff : ${userData.fullName}- <a href="LoadListServlet"> View list product </a>
                - <a href="LogoutServlet">Log out</a>   </br></br>
            </div>
        </footer>
    </body>
</html>
