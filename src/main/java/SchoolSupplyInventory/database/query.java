package main.java.SchoolSupplyInventory.database;
import java.sql.*;
import java.util.ArrayList;

public class query {

    public ArrayList<String> getAllItems(String user, String pwd) {
        ArrayList<String> result = new ArrayList<String>();
        
        String sql = "SELECT * FROM Item";
        
        jdbc jdbc = new jdbc();
        ResultSet data = jdbc.getData(user, pwd, sql);
        
        try {
            while (data.next()) {
                result.add(Integer.toString(data.getInt("SupplyID")));
                result.add(Integer.toString(data.getInt("ManufacturerID")));
                result.add(data.getString("Brand"));
                result.add(Float.toString(data.getFloat("Price")));
                result.add(Integer.toString(data.getInt("Quantity")));
                result.add(data.getString("ItemDescription"));
                result.add(data.getString("StorageLocation"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
