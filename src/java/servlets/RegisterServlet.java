/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.UserDAO;
import dtos.Role;
import dtos.User;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tan Nguyen
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        User user = (User)request.getSession().getAttribute("userData");
        if (user == null) { // not user
            request.getRequestDispatcher("WEB-INF/views/register.jsp").forward(request, response);
        }else{
            request.getRequestDispatcher("LoadListServlet").forward(request, response);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
        String userName = request.getParameter("userName").trim();
        String password = request.getParameter("password").trim();
        String rePassword = request.getParameter("rePassword").trim();
        String fullName = request.getParameter("fullName").trim();
        if (password.equals(rePassword)) { //password duplicate
            User user = new User(userName, password, fullName, new Role(0));
            String error = validUser(user);
            if (error.equals("")) { // valid user
                UserDAO dao = new UserDAO();
                dao.createUser(user);
                request.setAttribute("notifi", "Register success");
                request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
            }else{
                request.setAttribute("error", error);
                request.getRequestDispatcher("WEB-INF/views/register.jsp").forward(request, response);
            }
        }else{      // password not duplicate
            request.setAttribute("error", "Password and re-password not duplicate");
            request.getRequestDispatcher("WEB-INF/views/register.jsp").forward(request, response);
        }
        }catch(Exception ex){
            request.getRequestDispatcher("LoadListServlet").forward(request, response);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    protected String validUser(User user) throws SQLException{
        String error = "";
        UserDAO dao = new UserDAO();
        if (dao.getUserId(user.getUserName()) != null) { // userName is existed
            error += "User name is existed";
        }
        if (user.getUserName().length() > 20 || user.getUserName().length() < 2 ) {
            error += "User name have to from 3-20 character - ";
        }
        if (user.getPassword().length() >20 || user.getPassword().length() < 2) {
            error += "Password have to from 3-20 character - ";
        }
        if (user.getFullName().length() >30 ) {
            error += "Full name have to less than 30 character";
        }
        if (error.endsWith("-")) {
            error = error.substring(0, error.length()-2);
        }
        return error;
    }

}
