/**
 * *********************************************************************
 *  FILE: TestUserInterface.java
 *  DESCRIPTION: This class contains unit tests to verify the UI components
 *               of the School Supply Inventory system. The tests ensure that
 *               the JavaFX application initializes correctly, UI elements exist,
 *               and the main UI loads as expected.
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
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;

import SchoolSupplyInventory.ui.MainApp;

/**
 * This class tests the graphical user interface (GUI) components of the
 * School Supply Inventory system to ensure correct JavaFX initialization
 * and verify the presence of essential UI elements.
 */
public class TestUserInterface {

    /** The main application instance */
    private MainApp mainApp;

    /** The primary JavaFX stage for the UI */
    private Stage stage;

    /** Latch to synchronize JavaFX initialization before tests */
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * Sets up the JavaFX environment and initializes the main application
     * before each test. Ensures that JavaFX is fully loaded before running UI tests.
     *
     * @throws Exception if JavaFX initialization fails
     */
    @Before
    public void setUp() throws Exception {
        new JFXPanel(); // Initializes JavaFX (required for JavaFX tests)

        Platform.runLater(() -> {
            try {
                mainApp = new MainApp();
                stage = new Stage();
                mainApp.start(stage);
                latch.countDown(); // Mark UI as initialized
            } catch (Exception e) {
                fail("Failed to initialize JavaFX UI: " + e.getMessage());
            }
        });

        latch.await(); // Ensures JavaFX UI is fully loaded before tests run
    }

    /**
     * Tests whether the main application UI launches successfully.
     * This ensures that the JavaFX application initializes without errors.
     *
     * @throws Exception if UI initialization fails
     */
    @Test
    public void testApplicationLaunches() throws Exception {
        Platform.runLater(() -> {
            assertNotNull("Main application UI should launch without errors", stage);
        });

        // Allow JavaFX thread to process UI updates before assertions
        Thread.sleep(500);
    }

    /**
     * Tests whether all essential UI buttons exist in the application.
     * Verifies the presence of:
     * - Add Item button
     * - Update Item button
     * - Delete Item button
     * - View Items button
     *
     * @throws Exception if UI elements do not exist
     */
    @Test
    public void testUIHasButtons() throws Exception {
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
