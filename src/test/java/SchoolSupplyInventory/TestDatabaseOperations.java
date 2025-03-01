package SchoolSupplyInventory;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.Connection;
import java.util.List;

public class TestDatabaseOperations {

    private DatabaseManager dbManager;
    private int testItemID;

    @Before
    public void setUp() {
        dbManager = new DatabaseManager();
        testItemID = (int) (Math.random() * 1000) + 5000; // Unique Supply ID to avoid conflicts
    }

    @Test
    public void testDatabaseConnection() throws Exception {
        try (Connection conn = DatabaseManager.getConnection()) {
            assertNotNull("Connection should not be null", conn);
        }
    }

    @Test
    public void testInsertItem() {
        try {
            int initialSize = dbManager.getAllItems().size();
            dbManager.insertItem(testItemID, 101, "BrandX", 2.5, 10, "Notebook", "Aisle 3");

            List<Item> items = dbManager.getAllItems();
            assertTrue("Item should be found in database", items.stream().anyMatch(i -> i.getSupplyID() == testItemID));
            assertEquals("Item count should increase after insertion", initialSize + 1, items.size());
        } catch (Exception e) {
            fail("Item insertion failed: " + e.getMessage());
        }
    }

    @Test
    public void testInsertDuplicateItem() {
        try {
            // Insert an item once
            dbManager.insertItem(testItemID, 101, "BrandD", 3.0, 15, "Eraser", "Drawer B");

            // Try inserting the same item again
            dbManager.insertItem(testItemID, 101, "BrandD", 3.0, 15, "Eraser", "Drawer B");

            // Retrieve all items with this Supply ID
            long count = dbManager.getAllItems().stream()
                    .filter(i -> i.getSupplyID() == testItemID)
                    .count();

            // Ensure only one entry exists for this Supply ID
            assertEquals("Only one instance of the same SupplyID should exist", 1, count);

        } catch (Exception e) {
            System.out.println("Duplicate item insertion attempted: " + e.getMessage());
            assertTrue("If an error is thrown, it should mention a PRIMARY constraint",
                    e.getMessage().toLowerCase().contains("primary"));
        }
    }

    @Test
    public void testGetAllItems() {
        try {
            dbManager.insertItem(testItemID, 101, "BrandY", 3.0, 20, "Pen", "Shelf B");
            List<Item> items = dbManager.getAllItems();

            assertNotNull("Item list should not be null", items);
            assertTrue("Item should be retrieved from database", items.stream().anyMatch(i -> i.getSupplyID() == testItemID));
        } catch (Exception e) {
            fail("Failed to retrieve items: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateItemQuantity() {
        try {
            dbManager.insertItem(testItemID, 101, "BrandZ", 5.0, 30, "Marker", "Cabinet A");
            dbManager.updateItemQuantity(testItemID, 50);

            List<Item> items = dbManager.getAllItems();
            Item updatedItem = items.stream().filter(i -> i.getSupplyID() == testItemID).findFirst().orElse(null);

            assertNotNull("Updated item should exist", updatedItem);
            assertEquals("Quantity should be updated", 50, updatedItem.getQuantity());
        } catch (Exception e) {
            fail("Item update failed: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateItemQuantityToZero() {
        try {
            dbManager.insertItem(testItemID, 101, "BrandX", 2.5, 10, "Notebook", "Aisle 3");
            dbManager.updateItemQuantity(testItemID, 0);

            List<Item> items = dbManager.getAllItems();
            Item updatedItem = items.stream().filter(i -> i.getSupplyID() == testItemID).findFirst().orElse(null);

            assertNotNull("Updated item should exist", updatedItem);
            assertEquals("Quantity should be updated to 0", 0, updatedItem.getQuantity());
        } catch (Exception e) {
            fail("Failed to update quantity: " + e.getMessage());
        }
    }

    @Test
    public void testUpdateNonExistentItemQuantity() {
        try {
            dbManager.updateItemQuantity(9999, 50); // ID 9999 does not exist
            assertTrue("Database should remain unchanged", dbManager.getAllItems().stream().noneMatch(i -> i.getSupplyID() == 9999));
        } catch (Exception e) {
            fail("Unexpected failure when updating non-existent item: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteItem() {
        try {
            dbManager.insertItem(testItemID, 101, "BrandA", 4.0, 40, "Stapler", "Drawer D");
            assertTrue("Item should exist before deletion", dbManager.getAllItems().stream().anyMatch(i -> i.getSupplyID() == testItemID));

            dbManager.deleteItem(testItemID);
            assertFalse("Item should not exist after deletion", dbManager.getAllItems().stream().anyMatch(i -> i.getSupplyID() == testItemID));
        } catch (Exception e) {
            fail("Item deletion failed: " + e.getMessage());
        }
    }

    @Test
    public void testDeleteNonExistentItem() {
        try {
            dbManager.deleteItem(9999);
            assertFalse("Non-existent item should not be found in database", dbManager.getAllItems().stream().anyMatch(i -> i.getSupplyID() == 9999));
        } catch (Exception e) {
            fail("Unexpected error while deleting non-existent item: " + e.getMessage());
        }
    }
}
