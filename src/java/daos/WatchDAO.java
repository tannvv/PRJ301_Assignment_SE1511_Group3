/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dbContext.DBContext;
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
public class WatchDAO {

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

    public ArrayList<Watch> getAllWatch() {
        ArrayList<Watch> list = null;
        String sql = "SELECT * FROM tbl_Watch";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            rs = ptsm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("watchId");
                String name = rs.getString("watchName");
                String manufacturer = rs.getString("manufacturer");
                String description = rs.getString("description");
                String url = rs.getString("urlImage");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                boolean sale = rs.getBoolean("sale");
                list.add(new Watch(id, name, manufacturer, description, url, price, quantity, sale));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
    public ArrayList<Watch> getWatchValidQuantity() {
        ArrayList<Watch> list = null;
        String sql = "SELECT * FROM tbl_Watch WHERE quantity > 0";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            rs = ptsm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("watchId");
                String name = rs.getString("watchName");
                String manufacturer = rs.getString("manufacturer");
                String description = rs.getString("description");
                String url = rs.getString("urlImage");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                boolean sale = rs.getBoolean("sale");
                list.add(new Watch(id, name, manufacturer, description, url, price, quantity, sale));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
    
     public Watch getWatchById(String input) {
        Watch watch = null;
        String sql = "SELECT * FROM tbl_Watch WHERE watchId = ?";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, input);
            rs = ptsm.executeQuery();

            if(rs.next()) {
                String id = rs.getString("watchId");
                String name = rs.getString("watchName");
                String manufacturer = rs.getString("manufacturer");
                String description = rs.getString("description");
                String url = rs.getString("urlImage");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                boolean sale = rs.getBoolean("sale");
                watch = new Watch(id, name, manufacturer, description, url, price, quantity, sale);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return watch;
    }

    public boolean createWatch(Watch watch) {
        boolean result = false;
        String sql = "INSERT INTO tbl_Watch(watchId,watchName,manufacturer,description,urlImage,price,quantity,sale) VALUES (?,?,?,?,?,?,?,?)";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, watch.getWatchId());
            ptsm.setString(2, watch.getWatchName());
            ptsm.setString(3, watch.getManufacturer());
            ptsm.setString(4, watch.getDescription());
            ptsm.setString(5, watch.getUrlImage());
            ptsm.setInt(6, watch.getPrice());
            ptsm.setInt(7, watch.getQuantity());
            ptsm.setBoolean(8, watch.isSale());
            ptsm.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean updateWatch(Watch watch) {
        boolean result = false;
        String sql = "UPDATE tbl_Watch \n"
                + "SET watchName = ? , manufacturer = ?, description = ?, urlImage = ?, price = ?, quantity = ?, sale = ? \n"
                + "WHERE watchId = ?";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, watch.getWatchName());
            ptsm.setString(2, watch.getManufacturer());
            ptsm.setString(3, watch.getDescription());
            ptsm.setString(4, watch.getUrlImage());
            ptsm.setInt(5, watch.getPrice());
            ptsm.setInt(6, watch.getQuantity());
            ptsm.setBoolean(7, watch.isSale());
            ptsm.setString(8, watch.getWatchId());
            ptsm.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public boolean deleteWatch(String id) {
        boolean result = false;
        String sql = "DELETE FROM tbl_Watch WHERE watchId = ?";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, id);
            ptsm.executeUpdate();
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
     public ArrayList<Watch> getManufacturer(String input) { // search watch by manufacturer
        ArrayList<Watch> list = null;
        String sql = "SELECT * FROM tbl_Watch WHERE manufacturer = ?";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, input);
            rs = ptsm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("watchId");
                String name = rs.getString("watchName");
                String manufacturer = rs.getString("manufacturer");
                String description = rs.getString("description");
                String url = rs.getString("urlImage");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                boolean sale = rs.getBoolean("sale");
                list.add(new Watch(id, name, manufacturer, description, url, price, quantity, sale));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list; 
     }
     
    public ArrayList<Watch> getWatchByIdName(String idName){
        ArrayList<Watch> list = null;
        String sql = "SELECT * FROM tbl_Watch WHERE watchId = ? OR watchName = ?";
        try {
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, idName);
            ptsm.setString(2, idName);
            rs = ptsm.executeQuery();
            list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("watchId");
                String name = rs.getString("watchName");
                String manufacturer = rs.getString("manufacturer");
                String description = rs.getString("description");
                String url = rs.getString("urlImage");
                int price = rs.getInt("price");
                int quantity = rs.getInt("quantity");
                boolean sale = rs.getBoolean("sale");
                list.add(new Watch(id, name, manufacturer, description, url, price, quantity, sale));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list; 
    } 
}
