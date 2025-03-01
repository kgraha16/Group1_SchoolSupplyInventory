package SchoolSupplyInventory;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestApplicationLogic {

    private MainApp mainApp;

    @Before
    public void setUp() {
        // Initialize JavaFX Toolkit before running any tests
        new JFXPanel();
        Platform.runLater(() -> {
            mainApp = new MainApp();
        });
    }

    @Test
    public void testItemCreation() {
        Item item = new Item(1, 101, "BrandX", 2.5, 10, "Notebook", "Aisle 3");
        assertEquals("BrandX", item.getBrand());
        assertEquals(10, item.getQuantity());
    }

    // **DO NOT ADD TESTS THAT REQUIRE UI ELEMENTS HERE**
}
