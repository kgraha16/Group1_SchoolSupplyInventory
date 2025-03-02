/**
 * *********************************************************************
 *  FILE: TestDatabaseOperations.java
 *  DESCRIPTION: This class contains unit tests for verifying database operations
 *               in the School Supply Inventory system. It ensures that the database
 *               correctly handles inserting, retrieving, updating, and deleting items.
 *
 *  AUTHOR: Daria Ilchenko
 *  GROUP: Group1
 *  PROJECT: SchoolSupplyInventory
 *  CLASS: SER322Spring25
 *  DATE: March 1, 2025
 * *********************************************************************
 */

package SchoolSupplyInventory;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.List;

import SchoolSupplyInventory.database.DatabaseManager;
import SchoolSupplyInventory.util.Item;

import javafx.application.Platform;

/**
 * Unit tests for database operations, including item insertion, retrieval,
 * updating, and deletion in the School Supply Inventory system.
 */
public class TestDatabaseOperations {

    /** The database manager instance */
    private DatabaseManager dbManager;

    /** Unique supply ID for test items */
    private int testItemID;

    /**
     * Initializes the database manager before each test and assigns a unique test ID
     * to prevent conflicts.
     */
    @Before
    public void setUp() {
        dbManager = new DatabaseManager();
        testItemID = (int) (Math.random() * 1000) + 5000; // Unique Supply ID to avoid conflicts
    }

    /**
     * Tests whether the database connection is successfully established.
     */
    @Test
    public void testDatabaseConnection() {
        try (Connection conn = DatabaseManager.getConnection()) {
            assertNotNull("Connection should be established and not null", conn);
        } catch (Exception e) {
            fail("Database connection failed: " + e.getMessage());
        }
    }

    /**
     * Tests inserting an item into the database and verifies its existence.
     */
    @Test
    public void testInsertItem() {
        try {
            int initialSize = dbManager.getAllItems().size();

            dbManager.insertItem(testItemID, 101, "BrandX", 2.5, 10, "Notebook", "Aisle 3");

            List<Item> items = dbManager.getAllItems();
            boolean itemExists = items.stream().anyMatch(i -> i.getSupplyID() == testItemID);

            assertTrue("Inserted item should exist in database", itemExists);
            assertEquals("Item count should increase by 1 after insertion", initialSize + 1, items.size());
        } catch (Exception e) {
            fail("Item insertion failed: " + e.getMessage());
        }
    }

    /**
     * Tests retrieving all items from the database and verifies that the inserted item exists.
     */
    @Test
    public void testGetAllItems() {
        try {
            dbManager.insertItem(testItemID, 101, "BrandY", 3.0, 20, "Pen", "Shelf B");
            List<Item> items = dbManager.getAllItems();

            assertNotNull("Item list should not be null", items);
            assertTrue("Inserted item should be retrievable from database",
                    items.stream().anyMatch(i -> i.getSupplyID() == testItemID));
        } catch (Exception e) {
            fail("Failed to retrieve items: " + e.getMessage());
        }
    }

    /**
     * Tests updating an item's quantity to zero and verifies the update in the database.
     */
    @Test
    public void testUpdateItemQuantityToZero() {
        Platform.runLater(() -> {
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
        });

        // Allow JavaFX thread time to process
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Tests deleting an item and verifies that it is no longer in the database.
     */
    @Test
    public void testDeleteItem() {
        try {
            dbManager.insertItem(testItemID, 101, "BrandA", 4.0, 40, "Stapler", "Drawer D");
            boolean itemExistsBefore = dbManager.getAllItems().stream().anyMatch(i -> i.getSupplyID() == testItemID);
            assertTrue("Item should exist before deletion", itemExistsBefore);

            dbManager.deleteItem(testItemID);
            boolean itemExistsAfter = dbManager.getAllItems().stream().anyMatch(i -> i.getSupplyID() == testItemID);
            assertFalse("Item should not exist after deletion", itemExistsAfter);
        } catch (Exception e) {
            fail("Item deletion failed: " + e.getMessage());
        }
    }
}
