/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.OrderDAO;
import daos.OrderDetailDAO;
import daos.WatchDAO;
import dtos.Order;
import dtos.OrderDetail;
import dtos.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tan Nguyen
 */
@WebServlet(name = "PayServlet", urlPatterns = {"/PayServlet"})
public class PayServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PayServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PayServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User user = (User)request.getSession().getAttribute("userData");
        if (user.getRole().getRoleId() != 0) { // not user
            request.setAttribute("error", "You can have permission to do this function");
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
        }
        
        
        String address = request.getParameter("address");
        if (address.trim().isEmpty()) {
            request.setAttribute("errorPay", "Address cannot empty");
            request.getRequestDispatcher("WEB-INF/views/viewCart").forward(request, response);
        }
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        ArrayList<OrderDetail> listCart = (ArrayList<OrderDetail>) request.getSession().getAttribute("listCart");
        try {
            boolean checkValidQuantity = orderDetailDAO.checkExistedWatch(listCart);
            if (checkValidQuantity && listCart.size() >0) {
                Order order = createOrder(address, request);       // tạo ra order trong databas
                setOrderIdForCart(listCart, order);                //lấy order mới được tạo trong database sau đó gán cho từng OrderDetail
                orderDetailDAO.createListOrderDetail(listCart);     // lưu giỏ hàng vào trong csdl
                OrderDAO orderDAO = new OrderDAO();
                orderDAO.updatePayment(order.getOrderId());         // cập nhật trạng thái đã thanh toán cho order
                WatchDAO watchDAO = new WatchDAO();
                request.getSession().removeAttribute("listCart");
                request.setAttribute("notifi", "Pay success");
                request.setAttribute("listWatch", watchDAO.getWatchValidQuantity());
                request.getRequestDispatcher("WEB-INF/views/indexUser.jsp").forward(request, response);
            }else{
                if (checkValidQuantity==false) {
                    request.setAttribute("errorPay", "Sorry, out of quantity product in shop");
                }else{
                    request.setAttribute("errorPay", "List cart is empty, cannot pay");
                }
                request.getRequestDispatcher("WEB-INF/views/viewCart.jsp").forward(request, response);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    private Order createOrder(String address, HttpServletRequest request) {//customerId, orderDay, address, totalCost, payment
        Order order = null;
        try {
            HttpSession session = request.getSession();
            ArrayList<OrderDetail> listCart = (ArrayList<OrderDetail>) session.getAttribute("listCart");
            User userData = (User) session.getAttribute("userData");
            int totalCost = getTotalCost(listCart);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String orderDay = LocalDateTime.now().format(fmt);
            Order orderDatabase = new Order(userData.getUserName(), orderDay, address, totalCost, false);
            OrderDAO dao = new OrderDAO();
            dao.createOrder(orderDatabase);
            order = dao.getOrderNotPay(userData.getUserName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return order;
    }

    private int getTotalCost(ArrayList<OrderDetail> listCart) {
        int result = 0;
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            result += listCart.get(i).getWatch().getPrice() * listCart.get(i).getQuantity();
        }
        return result;
    }

    private void setOrderIdForCart(ArrayList<OrderDetail> listCart, Order order) {
        int size = listCart.size();
        for (int i = 0; i < size; i++) {
            listCart.get(i).setOrder(order);
        }
    }

}
