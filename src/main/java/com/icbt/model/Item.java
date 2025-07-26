package com.icbt.model;

public class Item {
    private int item_id;
    private String item_name;
    private String item_description;
    private String unit_price;
    private String stock_quantity;

    // Constructors
    public Item() {}

    public Item(String item_name, String item_description, String unit_price, String stock_quantity) {
        this.item_name = item_name;
        this.item_description = item_description;
        this.unit_price = unit_price;
        this.stock_quantity = stock_quantity;

    }

    // Getters and Setters
    public int getItem_id() { return item_id; }
    public void setItem_id(int item_id) { this.item_id = item_id; }

    public String getItem_name() { return item_name; }
    public void setItem_name(String item_name) { this.item_name = item_name; }

    public String getItem_description() { return item_description; }
    public void setItem_description(String item_description) { this.item_description = item_description; }

    public String getUnit_price() { return unit_price; }
    public void setUnit_price(String unit_price) { this.unit_price = unit_price; }

    public String getStock_quantity() { return stock_quantity; }
    public void setStock_quantity(String stock_quantity) { this.stock_quantity = stock_quantity; }
}
