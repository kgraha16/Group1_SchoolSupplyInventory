package main.java.SchoolSupplyInventory.database;

import java.sql.*;

public class jdbc {
    
    String url = "jdbc:mysql://localhost:3306/SchoolSupplyInventory";
    String driver = "com.mysql.cj.jdbc.Driver";
    
    public ResultSet getData(String user, String pwd, String statement) {
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        String username = user;
        String password = pwd;
        String sql = statement;
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql); 
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }
}