package main.java.SchoolSupplyInventory.util;
import java.util.HashMap;

public class Item {
    private int supplyID;
    private int manufacturerID;
    private String brand;
    private float price;
    private int quantity;
    private String description;
    private String location;

    public Item(HashMap<String, String> attributes) throws NumberFormatException {
        try {
            supplyID = Integer.parseInt(attributes.get("supplyID"));
            manufacturerID = Integer.parseInt(attributes.get("manufacturerID"));
            price = Float.parseFloat(attributes.get("price"));
            quantity = Integer.parseInt(attributes.get("quantity"));
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }

        brand = attributes.get("brand");
        description = attributes.get("description");
        location = attributes.get("location");
    }

    public int getSupplyID() { return supplyID; }
    public int getManufacturerID() { return manufacturerID; }
    public String getBrand() { return brand; }
    public float getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }

    public void changeQuantity(int amount) {
        quantity += amount;
    }

    public void changePrice(float newPrice) {
        price = newPrice;
    }

    public void changeLocation(String newLocation) {
        location = newLocation;
    }

}
