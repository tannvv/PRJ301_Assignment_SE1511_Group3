/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dbContext.DBContext;
import dtos.Order;
import dtos.Watch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tan Nguyen
 */
public class OrderDAO {
    Connection conn = null;
    PreparedStatement ptsm = null;
    ResultSet rs = null;

    private void cleanConnect() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ptsm != null) {
            ptsm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public ArrayList<Order> getHistoryOrder(String userName) {
        ArrayList<Order> list = null;
        String sql = "SELECT * FROM tbl_Order WHERE payment = 'true' AND customerId = ?";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, userName);
            
            rs = ptsm.executeQuery();
       
            list = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("orderId");
                String customer = rs.getString("customerId");
                String day = rs.getString("orderDay");
                String address = rs.getString("address");
                int totalCost = rs.getInt("totalCost");
                boolean payment = rs.getBoolean("payment");
                list.add(new Order(id, customer, day, address, totalCost, payment));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public boolean createOrder(Order order) throws SQLException {
        boolean result = false;
        String sql = "INSERT INTO tbl_Order(customerId, orderDay, address, totalCost, payment) VALUES (?,?,?,?,?)";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, order.getCustomerId());
            ptsm.setString(2, order.getOrderDay());
            ptsm.setString(3, order.getAddress());
            ptsm.setInt(4, order.getTotalCost());
            ptsm.setBoolean(5, order.isPayment());      
            ptsm.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            cleanConnect();
        }
        return result;
    }
    
    public Order getOrderNotPay(String userName){
        Order order = null;
        String sql = "SELECT * FROM tbl_Order WHERE customerId=? AND payment='false'";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, userName);
            rs = ptsm.executeQuery();
            if(rs.next()) {
                int orderId = rs.getInt("orderId");
                String customer = rs.getString("customerId");
                String address = rs.getString("address");
                String orderDay = rs.getString("OrderDay");
                int totalCost = rs.getInt("totalCost");
                boolean payment = rs.getBoolean("payment");
                order = new Order(orderId, customer, address, orderDay, totalCost, payment);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return order;
    }
     public boolean updatePayment(int orderId) throws SQLException {
        boolean result = false;
        String sql = "UPDATE tbl_Order SET payment = 'true' \n"
                + "WHERE orderId = ?";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, orderId);
            ptsm.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            cleanConnect();
        }
        return result;
    }
}
