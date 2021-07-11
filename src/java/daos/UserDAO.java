/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dbContext.DBContext;
import dtos.Role;
import dtos.User;
import java.rmi.ConnectIOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tan Nguyen
 */
public class UserDAO {
    Connection conn = null;
    PreparedStatement ptsm = null;
    ResultSet rs = null;
    
    private void cleanConnect() throws SQLException{
        if (rs!= null) {
            rs.close();
        }
        if (ptsm != null) {
            ptsm.close();
        }
        if (conn !=null) {
            conn.close();
        }
    }
    
    public User checkLogin(String userName, String password) throws SQLException{
        User user = null;
        String sql = "SELECT * FROM tbl_User WHERE userId = ? AND password = ?";
        try{
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, userName);
            ptsm.setString(2, password);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                Role role = new Role(rs.getInt("role"));
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("fullName"),role);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            cleanConnect();
        }
        return user;
    }
    
    public boolean createUser(User user){
        boolean result = false;
        String sql = "INSERT INTO tbl_User(userId,password,fullName,role) VALUES (?,?,?,?)";
        try{
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, user.getUserName());
            ptsm.setString(2, user.getPassword());
            ptsm.setString(3, user.getFullName());
            ptsm.setInt(4, user.getRole().getRoleId());
            ptsm.executeUpdate();
            result = true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    public User getUserId(String id) throws SQLException{
        User user = null;
        String sql = "SELECT * FROM tbl_User WHERE userId = ?";
        try{
            conn = DBContext.getConnect();
            ptsm = conn.prepareStatement(sql);
            ptsm.setString(1, id);
            rs = ptsm.executeQuery();
            if (rs.next()) {
                Role role = new Role(rs.getInt("role"));
                user = new User(rs.getString("userId"), rs.getString("password"), rs.getString("fullName"),role);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            cleanConnect();
        }
        return user;
    }
   
}
