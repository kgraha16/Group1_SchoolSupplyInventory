package SchoolSupplyInventory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static void main(String[] args) {
        String url = "jdbc:mysql://schoolsupplyinventory.cnmcooc42w90.us-east-2.rds.amazonaws.com:3306/Group1_SchoolSupplyInventory";
        String username = "JohnSmith";  // Change if using another user or use your own username
        String password = "johnsmith123$";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        }
    }
}
