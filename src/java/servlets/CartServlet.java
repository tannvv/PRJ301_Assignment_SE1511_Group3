/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.WatchDAO;
import dtos.OrderDetail;
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
@WebServlet(name = "CartServlet", urlPatterns = {"/CartServlet"})
public class CartServlet extends HttpServlet {
    private final String VIEW_CART = "WEB-INF/views/viewCart.jsp";

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
        if (user.getRole().getRoleId() != 0) { // not user
            request.setAttribute("error", "You can have permission to do this function");
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
        }
        
        String action = request.getParameter("action");
        //String id = request.getParameter("id"); // watch id to do action
        if (action.equals("view")) { // view
            ArrayList<OrderDetail> listCart = null;
            listCart = (ArrayList<OrderDetail>)request.getSession().getAttribute("listCart");
            if (listCart == null) {
                listCart = new ArrayList<>();
                request.getSession().setAttribute("listCart", listCart);
            }
            request.setAttribute("totalCost", getTotalCost(listCart));
            request.getRequestDispatcher(VIEW_CART).forward(request, response);
        }
        if (action.equals("buy")) { // add to cart
            buyWatch(request,response);
        }
        if (action.equals("remove")) { // remove cart
            removeCart(request,response);
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

    private void buyWatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<OrderDetail> listCart = (ArrayList<OrderDetail>)request.getSession().getAttribute("listCart");
        if (listCart == null) {
            listCart = new ArrayList<>();
        }
        String id = request.getParameter("id");
        int indexCart = checkExistedCart(listCart,id);
        if (indexCart >= 0) { // cart is existed
            int newQuantity = listCart.get(indexCart).getQuantity()+1;
            listCart.get(indexCart).setQuantity(newQuantity);
        }else{
            WatchDAO watchDAO = new WatchDAO();
            Watch watch = watchDAO.getWatchById(id);
            listCart.add(new OrderDetail(watch, 1));
        }
        request.setAttribute("totalCost", getTotalCost(listCart));
        request.getSession().setAttribute("listCart", listCart);
        request.getRequestDispatcher("WEB-INF/views/viewCart.jsp").forward(request, response);
    }
    
    private int checkExistedCart(ArrayList<OrderDetail> listCart, String watchId){
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            if (listCart.get(i).getWatch().getWatchId().equals(watchId)) {
                return i;
            }
        }
        return -1;
    }

    private void removeCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         String id = request.getParameter("id");
         ArrayList<OrderDetail> listCart = (ArrayList<OrderDetail>)request.getSession().getAttribute("listCart");
         int index = checkExistedCart(listCart, id);
         listCart.remove(index);
          request.setAttribute("totalCost", getTotalCost(listCart));
         request.getSession().setAttribute("listCart", listCart);
         request.getRequestDispatcher("WEB-INF/views/viewCart.jsp").forward(request, response);
    }
    
    private float getTotalCost(ArrayList<OrderDetail> listCart){
        float result = 0;
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            result += listCart.get(i).getWatch().getPrice() * listCart.get(i).getQuantity();
        }
        return result;
    }

}
