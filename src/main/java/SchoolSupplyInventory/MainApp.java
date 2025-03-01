package SchoolSupplyInventory;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class MainApp extends Application {
    private DatabaseManager dbManager = new DatabaseManager();
    private TableView<Item> tableView = new TableView<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("School Supply Inventory");

        // **Title Label**
        Label titleLabel = new Label("Welcome to School Supply Inventory!");

        // **Table to Display Items**
        tableView.setPlaceholder(new Label("No items in inventory"));
        setupTableView();

        // **Buttons**
        Button addItemButton = new Button("Add Item");
        Button updateItemButton = new Button("Update Item Quantity");
        Button deleteItemButton = new Button("Delete Item");
        Button viewItemsButton = new Button("View Items");

        // **Event Handlers**
        addItemButton.setOnAction(e -> showAddItemDialog());
        updateItemButton.setOnAction(e -> showUpdateItemDialog());
        deleteItemButton.setOnAction(e -> showDeleteItemDialog());
        viewItemsButton.setOnAction(e -> loadItems());

        // **Layout**
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(titleLabel, addItemButton, updateItemButton, deleteItemButton, viewItemsButton, tableView);

        // **Scene Setup**
        Scene scene = new Scene(layout, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // **Setup TableView Columns**
    private void setupTableView() {
        TableColumn<Item, Integer> idColumn = new TableColumn<>("Supply ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("supplyID"));

        TableColumn<Item, Integer> manufacturerColumn = new TableColumn<>("Manufacturer ID");
        manufacturerColumn.setCellValueFactory(new PropertyValueFactory<>("manufacturerID"));

        TableColumn<Item, String> brandColumn = new TableColumn<>("Brand");
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));

        TableColumn<Item, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Item, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));

        TableColumn<Item, String> locationColumn = new TableColumn<>("Storage Location");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("storageLocation"));

        tableView.getColumns().clear();
        tableView.getColumns().addAll(idColumn, manufacturerColumn, brandColumn, priceColumn, quantityColumn, descriptionColumn, locationColumn);
    }

    // **Load Items from Database**
    private void loadItems() {
        List<Item> items = dbManager.getAllItems();
        tableView.setItems(FXCollections.observableArrayList(items));
    }

    // **Add New Item Dialog**
    private void showAddItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Item");

        // **Input Fields**
        TextField idField = new TextField();
        idField.setPromptText("Supply ID");

        TextField manufacturerField = new TextField();
        manufacturerField.setPromptText("Manufacturer ID");

        TextField brandField = new TextField();
        brandField.setPromptText("Brand");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Item Description");

        TextField locationField = new TextField();
        locationField.setPromptText("Storage Location");

        VBox content = new VBox(10, idField, manufacturerField, brandField, priceField, quantityField, descriptionField, locationField);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // **Handle Button Click**
        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int manufacturerID = Integer.parseInt(manufacturerField.getText());
                    String brand = brandField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    String description = descriptionField.getText();
                    String location = locationField.getText();

                    dbManager.insertItem(id, manufacturerID, brand, price, quantity, description, location);
                    loadItems(); // Refresh table
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input! Please enter valid values.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // **Update Item Quantity**
    private void showUpdateItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Item Quantity");

        TextField idField = new TextField();
        idField.setPromptText("Supply ID");
        TextField quantityField = new TextField();
        quantityField.setPromptText("New Quantity");

        VBox content = new VBox(10, idField, quantityField);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int quantity = Integer.parseInt(quantityField.getText());
                    dbManager.updateItemQuantity(id, quantity);
                    loadItems();
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input! Please enter valid numbers.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // **Delete Item Dialog**
    private void showDeleteItemDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Delete Item");

        TextField idField = new TextField();
        idField.setPromptText("Supply ID");

        VBox content = new VBox(10, idField);
        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    dbManager.deleteItem(id);
                    loadItems();
                } catch (NumberFormatException ex) {
                    showAlert("Invalid input! Please enter a valid ID.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    // **Helper Method for Alerts**
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
