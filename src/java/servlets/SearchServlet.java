/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.WatchDAO;
import dtos.User;
import dtos.Watch;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tan Nguyen
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        
        User user = (User)request.getSession().getAttribute("userData");
        
        WatchDAO dao = new WatchDAO();
        if (action.equalsIgnoreCase("idName") || user.getRole().getRoleId() == 1) { // staff search
            String idName = request.getParameter("idName");
            ArrayList<Watch> listWatch = dao.getWatchByIdName(idName);
            if (listWatch.isEmpty()) {
                request.setAttribute("error", "Cannot find any product !!");
            }
            request.setAttribute("listWatch", dao.getWatchByIdName(idName));
            request.getRequestDispatcher("WEB-INF/views/indexStaff.jsp").forward(request, response);
        }
        if (action.equalsIgnoreCase("manufacturer") || user.getRole().getRoleId() == 0) { //user serch
            String manufacturer = request.getParameter("manufacturer");
            request.setAttribute("listWatch", dao.getManufacturer(manufacturer));
            request.getRequestDispatcher("WEB-INF/views/indexUser.jsp").forward(request, response);
        }
        request.setAttribute("error", "Cannot search");
        request.getRequestDispatcher("LoadListServlet").forward(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
