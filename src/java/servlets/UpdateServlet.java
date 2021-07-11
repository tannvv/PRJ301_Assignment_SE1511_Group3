/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import daos.WatchDAO;
import dtos.User;
import dtos.Watch;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Tan Nguyen
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 10,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)
public class UpdateServlet extends HttpServlet {
     private final String UPLOAD_DIR = "images";

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
        if (user.getRole().getRoleId() != 1) { // not staff
            request.setAttribute("error", "You can have permission to do this function");
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request, response);
        }
        
        String id = request.getParameter("id");
        WatchDAO dao = new WatchDAO();
        Watch watch = dao.getWatchById(id);
        request.setAttribute("watch", watch);
        request.getRequestDispatcher("WEB-INF/views/watchForm.jsp").forward(request, response);
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
        try {
            String id = request.getParameter("id").trim();
            String name = request.getParameter("name").trim();
            String manufacturer = request.getParameter("manufacturer").trim();
            String description = request.getParameter("description").trim();
            int price = Integer.parseInt(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            boolean sale = Boolean.parseBoolean(request.getParameter("sale"));
            String url = uploadFile(request);
            Watch watch = new Watch(id, name, manufacturer, description, url, price, quantity, sale);
            String error = validWatch(watch);
             WatchDAO dao = new WatchDAO();
            if (error.equals("")) { // watch valid            
                dao.updateWatch(watch);
                request.setAttribute("listWatch", dao.getAllWatch());
                request.getRequestDispatcher("WEB-INF/views/indexStaff.jsp").forward(request, response);
            } else {
                request.setAttribute("listWatch", dao.getAllWatch());
                request.setAttribute("error", error);
                request.getRequestDispatcher("WEB-INF/views/indexStaff.jsp").forward(request, response);
            }
        }catch(Exception ex){
                WatchDAO dao = new WatchDAO();
                request.setAttribute("listWatch", dao.getAllWatch());
                request.setAttribute("error", "Cannot update");
                request.getRequestDispatcher("WEB-INF/views/indexStaff.jsp").forward(request, response);
            
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
    
    private String uploadFile(HttpServletRequest request) throws IOException, ServletException {
        String fileName = "";
        try {
            Part filePart = request.getPart("photo");
            fileName = (String) getFileName(filePart);

            String applicationPath = request.getServletContext().getRealPath("");
            String project = applicationPath.substring(0, applicationPath.length() - 10) + "web";
            String basePath = project + File.separator + UPLOAD_DIR + File.separator;
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (Exception e) {
            fileName = "";
        }
        return fileName;
    }

    private String getFileName(Part filePart) {
        for (String content : filePart.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private String validWatch(Watch watch) {
        String error = "";
        if (watch.getWatchId().length() < 3 || watch.getWatchId().length() > 10) {
            error += "The id have to from 3-10 character - ";
        }
        if (watch.getWatchName().length() < 5 || watch.getWatchName().length() > 25) {
            error += "Name have to from 5-25 character - ";
        }
         if (watch.getPrice() <= 0) {
            error += "Price have to a integer positive number - ";
        }
        if (watch.getQuantity() <= 0) {
            error += "Quantity hava to a integer positive number - ";
        }
        if (watch.getUrlImage().isEmpty()) {
            error += "Photo cannot empty";
        }
        if (error.endsWith(" - ")) {
            error = error.substring(0, error.length() - 3);
        }
        return error;
    }

}
