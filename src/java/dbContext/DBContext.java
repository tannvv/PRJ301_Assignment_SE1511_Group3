/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbContext;

import com.sun.org.apache.bcel.internal.generic.AALOAD;
import com.sun.org.apache.xalan.internal.lib.ExsltDatetime;
import daos.OrderDAO;
import daos.OrderDetailDAO;
import daos.UserDAO;
import daos.WatchDAO;
import dtos.Order;
import dtos.OrderDetail;
import dtos.Watch;
import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 *
 * @author Tan Nguyen
 */
public class DBContext {

    public static Connection getConnect() throws ClassNotFoundException{
       Connection conn = null;
        try{        
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String url = "jdbc:sqlserver://localhost:1433;databaseName=PRJ301_Assignment_SE1511_Group3";
            conn = DriverManager.getConnection(url, "sa", "123456");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return conn ;
    }
    
}
