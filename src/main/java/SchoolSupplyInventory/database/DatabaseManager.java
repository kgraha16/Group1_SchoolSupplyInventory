package SchoolSupplyInventory.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import SchoolSupplyInventory.util.Item;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://schoolsupplyinventory.cnmcooc42w90.us-east-2.rds.amazonaws.com:3306/Group1_SchoolSupplyInventory";
    private static final String USER = "JohnSmith";
    private static final String PASSWORD = "johnsmith123$";

    // **Establish Database Connection**
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // **Insert an Item into the Database**
    // Insert an Item into the Database
    public void insertItem(int supplyID, int manufacturerID, String brand, double price, int quantity, String itemDescription, String storageLocation) {
        String sql = "INSERT INTO Item (SupplyID, ManufacturerID, Brand, Price, Quantity, ItemDescription, StorageLocation) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplyID);
            stmt.setInt(2, manufacturerID);
            stmt.setString(3, brand);
            stmt.setDouble(4, price);
            stmt.setInt(5, quantity);
            stmt.setString(6, itemDescription);
            stmt.setString(7, storageLocation);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Item added successfully! Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Error inserting item");
            e.printStackTrace();
        }
    }


    // **Retrieve All Items from Database**
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT SupplyID, ManufacturerID, Brand, Price, Quantity, ItemDescription, StorageLocation FROM Item";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("SupplyID");
                int manufacturerID = rs.getInt("ManufacturerID");
                String brand = rs.getString("Brand");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                String description = rs.getString("ItemDescription");
                String location = rs.getString("StorageLocation");

                items.add(new Item(id, manufacturerID, brand, price, quantity, description, location));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving items");
            e.printStackTrace();
        }

        return items;
    }


    // **Delete an Item from the Database**
    public void deleteItem(int supplyID) {
        String sql = "DELETE FROM Item WHERE SupplyID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, supplyID);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Item deleted successfully! Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Error deleting item");
            e.printStackTrace();
        }
    }

    // **Update an Item's Quantity**
    public void updateItemQuantity(int supplyID, int newQuantity) {
        String sql = "UPDATE Item SET Quantity = ? WHERE SupplyID = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newQuantity);
            stmt.setInt(2, supplyID);

            int rowsAffected = stmt.executeUpdate();
            System.out.println("Item quantity updated! Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Error updating item");
            e.printStackTrace();
        }
    }

    // **Test the Database Operations**
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();


        // Retrieve All Items
        System.out.println("\nCurrent Inventory:");
        db.getAllItems().forEach(item ->
                System.out.println("ID: " + item.getSupplyID() +
                        ", Brand: " + item.getBrand() +
                        ", Price: $" + item.getPrice() +
                        ", Quantity: " + item.getQuantity()));

        // Update Item Quantity
        db.updateItemQuantity(1, 75);

        // Delete an Item
        db.deleteItem(1);
    }
}
