/**
 * *********************************************************************
 *  FILE: TestApplicationLogic.java
 *  DESCRIPTION: This class contains unit tests to verify the core application
 *               logic of the School Supply Inventory system. The tests ensure
 *               correct JavaFX initialization, UI element existence, and
 *               application stability.
 *
 *  AUTHOR: Daria Ilchenko
 *  GROUP: Group1
 *  PROJECT: SchoolSupplyInventory
 *  CLASS: SER322Spring25
 *  DATE: March 1, 2025
 * *********************************************************************
 */

package SchoolSupplyInventory;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;

import SchoolSupplyInventory.ui.MainApp;
import SchoolSupplyInventory.util.Item;

/**
 * This class tests the core application logic, ensuring the School Supply Inventory
 * UI initializes correctly and that UI components exist as expected.
 */
public class TestApplicationLogic {

    /** The main application instance */
    private MainApp mainApp;

    /** The primary JavaFX stage for the UI */
    private Stage stage;

    /** Latch to synchronize JavaFX initialization */
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * Sets up the JavaFX environment and initializes the application before each test.
     * Ensures that the UI is loaded before tests execute.
     *
     * @throws Exception if JavaFX initialization fails
     */
    @Before
    public void setUp() throws Exception {
        new JFXPanel(); // Initializes JavaFX

        Platform.runLater(() -> {
            try {
                mainApp = new MainApp();
                stage = new Stage();
                mainApp.start(stage);
                latch.countDown(); // Signals UI is ready
            } catch (Exception e) {
                fail("Failed to initialize JavaFX UI: " + e.getMessage());
            }
        });

        latch.await(); // Ensures JavaFX is initialized before proceeding
    }

    /**
     * Tests whether an item can be successfully created with the correct attributes.
     */
    @Test
    public void testItemCreation() {
        Item item = new Item(1, 101, "BrandX", 2.5,
                10, "Notebook", "Aisle 3");
        assertNotNull("Item should be created", item);
        assertEquals("BrandX", item.getBrand());
        assertEquals(10, item.getQuantity());
        assertEquals(1, item.getSupplyID());
        assertEquals("Notebook", item.getItemDescription());
    }

    /**
     * Tests whether the storage location of an item is correctly assigned.
     */
    @Test
    public void testItemStorageLocation() {
        Item item = new Item(2, 202, "BrandY", 5.0,
                15, "Marker", "Shelf B");
        assertEquals("Storage location should be correctly assigned",
                "Shelf B", item.getStorageLocation());
    }

    /**
     * Tests whether the main application UI launches successfully.
     * Uses a countdown latch to ensure synchronization.
     *
     * @throws Exception if UI initialization fails
     */
    @Test
    public void testMainAppLaunches() throws Exception {
        CountDownLatch appLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            assertNotNull("Main application UI should launch without errors", stage);
            appLatch.countDown();
        });

        appLatch.await(); // Wait for UI test to complete
    }

    /**
     * Tests whether all necessary UI buttons exist in the application.
     * Uses a countdown latch to ensure UI elements are properly loaded.
     *
     * @throws Exception if UI buttons do not exist
     */
    @Test
    public void testButtonExistence() throws Exception {
        CountDownLatch buttonLatch = new CountDownLatch(1);

        Platform.runLater(() -> {
            try {
                assertNotNull("Add Item button should exist", mainApp.getAddItemButton());
                assertNotNull("Update Item button should exist", mainApp.getUpdateItemButton());
                assertNotNull("Delete Item button should exist", mainApp.getDeleteItemButton());
                assertNotNull("View Items button should exist", mainApp.getViewItemsButton());
            } finally {
                buttonLatch.countDown(); // Ensure test doesn't exit before checking UI
            }
        });

        buttonLatch.await(); // Wait until assertions complete
    }
}
