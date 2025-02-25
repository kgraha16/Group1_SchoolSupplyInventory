package main.java.SchoolSupplyInventory.database;

import java.sql.*;

public class jdbc {
    public static void main(String[] args) {
        int n = args.length;

        String url = args[0];
        String user = args[1];
        String password = args[2];
        String driver = args[3];
        String request = args[4];
        
        try {
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            if (request.equals("select")) {
                int selCount = Integer.parseInt(args[5]);
                if (selCount == 1) {
                    
                } else if (selCount == 2) {
                    
                } else if (selCount == 3) {
                    
                }
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static void select(Connection conn, String[] args) {
        
    }
    
    static void insert(Connection conn) {
        
    }
    
    static void update(Connection conn) {
        
    }
    
    static void delete(Connection conn) {
        
    }
}