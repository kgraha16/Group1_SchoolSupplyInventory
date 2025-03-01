package SchoolSupplyInventory.util;

import javafx.beans.property.*;

public class Item {
    private int supplyID;
    private int manufacturerID;
    private String brand;
    private double price;
    private int quantity;
    private String itemDescription;
    private String storageLocation;

    // Constructor
    public Item(int supplyID, int manufacturerID, String brand, double price, int quantity, String itemDescription, String storageLocation) {
        this.supplyID = supplyID;
        this.manufacturerID = manufacturerID;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.itemDescription = itemDescription;
        this.storageLocation = storageLocation;
    }

    // Getters for JavaFX TableView
    public int getSupplyID() { return supplyID; }
    public int getManufacturerID() { return manufacturerID; }
    public String getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getItemDescription() { return itemDescription; }
    public String getStorageLocation() { return storageLocation; }
}

