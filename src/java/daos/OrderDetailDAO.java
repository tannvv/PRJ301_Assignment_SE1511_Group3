/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dbContext.DBContext;
import dtos.OrderDetail;
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
public class OrderDetailDAO {
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

    public boolean createOrderDetail(OrderDetail orderDetail) {
        boolean result = false;
        String sql = "INSERT INTO tbl_OrderDetail(orderId, watchId, quantity) VALUES (?,?,?)";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setInt(1, orderDetail.getOrder().getOrderId());
            ptsm.setString(2, orderDetail.getWatch().getWatchId());
            ptsm.setInt(3, orderDetail.getQuantity());
            ptsm.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public void createListOrderDetail(ArrayList<OrderDetail> listCart){
        int size = listCart.size();
        OrderDetailDAO dao = new OrderDetailDAO();
        for (int i = 0; i < size; i++) {
            dao.createOrderDetail(listCart.get(i));
        }
    }
    
    public boolean checkExistedWatch(ArrayList<OrderDetail> listCart){
        int size = listCart.size();
        WatchDAO dao = new WatchDAO();
        for (int i = 0; i < size; i++) {
            Watch watch = dao.getWatchById(listCart.get(i).getWatch().getWatchId());
            if ((watch.getQuantity() - listCart.get(i).getQuantity()) < 0) { // thiếu hàng trong data base
                return false;
            }
        }
        return true;
    }
}
