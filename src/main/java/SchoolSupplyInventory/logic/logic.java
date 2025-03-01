package main.java.SchoolSupplyInventory.logic;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;

import main.java.SchoolSupplyInventory.database.jdbc;
import main.java.SchoolSupplyInventory.util.Item;

public class logic {
    ArrayList<Item> items;

    public logic() {
        items = new ArrayList<>();
    }

    // Doesn't work yet, will need a way to get connection from jdbc class and an insertData method that returns a boolean
    public boolean addItem(HashMap<String, String> attributes) {
        Item item = null;
        Connection conn = jdbc.getConn();

        try {
            item = new Item(attributes);
        } catch (NumberFormatException e) {
            return false;
        }

        for (Item product : items) {
            if (product.isEqual(item)) {
                return false;
            }
        }

        items.add(item);

        String insertStatement = "INSERT INTO item (SupplyId, ManufacturerId, Brand, Price, Quantity, ItemDescription, StorageLocation) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        int i = 0;
        PreparedStatement statement = conn.prepareStatement(insertStatement);
        statement.setInt(i++, item.getSupplyID());
        statement.setInt(i++, item.getManufacturerID());
        statement.setString(i++, item.getBrand());
        statement.setFloat(i++, item.getPrice());
        statement.setInt(i++, item.getQuantity());
        statement.setString(i++, item.getDescription());
        statement.setString(i, item.getLocation());

        return jdbc.insertData();
    }
}
