<%-- 
    Document   : register
    Created on : Jul 5, 2021, 2:19:30 PM
    Author     : Tan Nguyen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <style type="text/css">
            /* Bordered form */
             font{
                padding-left: 30px;
                font-size: 15px;
                color: red;
            }
            form {
                border: 3px solid #f1f1f1;
            }
            .divMain{
                height: 700px;
                width: 500px;
                padding-left: 35%;
            }
            /* Full-width inputs */
            input[type=text], input[type=password] {
                width: 100%;
                padding: 12px 20px;
                margin: 8px 0;
                display: inline-block;
                border: 1px solid #ccc;
                box-sizing: border-box;
            }

            /* Set a style for all buttons */
            button {
                background-color: #04AA6D;
                color: white;
                padding: 14px 20px;
                margin: 8px 0;
                border: none;
                cursor: pointer;
                width: 100%;
            }

            /* Add a hover effect for buttons */
            button:hover {
                opacity: 0.8;
            }

            /* Extra style for the cancel button (red) */
            .cancelbtn {
                width: auto;
                padding: 10px 18px;
                background-color: #f44336;
            }

            /* Center the avatar image inside this container */
            .imgcontainer {
                text-align: center;
                margin: 24px 0 12px 0;
            }

            /* Avatar image */
            img.avatar {
                width: 40%;
                border-radius: 50%;
            }

            /* Add padding to containers */
            .container {
                padding: 16px;
            }

            /* The "Forgot password" text */
            span.psw {
                float: right;
                padding-top: 16px;
            }

            /* Change styles for span and cancel button on extra small screens */
            @media screen and (max-width: 300px) {
                span.psw {
                    display: block;
                    float: none;
                }
                .cancelbtn {
                    width: 100%;
                }
            }
            h3{
                text-align: center;
                font-size: 25px;

            }

        </style>

    </head>
    <body>
         <div class="divMain">
            <form action="RegisterServlet" method="POST">
                <h3>Register customer</h3> 
                <div class="imgcontainer">
                    <img src="images/watch_main.png" alt="Avatar" class="avatar">
                </div>
                <font>${error}</font>
                <div class="container">
                    <label for="userName"><b>Username</b></label>
                    <input type="text" placeholder="Enter Username" name="userName" required>

                    <label for="password"><b>Password</b></label>
                    <input type="password" placeholder="Enter Password" name="password" required>
                    
                    <label for="rePassword"><b>Re-password</b></label>
                    <input type="password" placeholder="Enter Re-password" name="rePassword" required>
                    
                    <label for="fullName"><b>Full name</b></label>
                    <input type="text" placeholder="Enter your full name" name="fullName" required>

                    <button type="submit">Register</button>
                </div>
                <div class="container" style="background-color:#f1f1f1">
                    <label>You have a account ?   </label>
                    <a href="LoginServlet">Login</a>
                </div>
            </form>
        </div>
    </body>
</html>
