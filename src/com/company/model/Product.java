package com.company.model;


public class Product {
    private int productId;
    private String productName;
    private double price;
    private String description;
    private int categoryId;

    // Constructor
    public Product(int productId, String productName, double price, String description, int categoryId) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.description = description;
        this.categoryId = categoryId;
    }

    // Getters
    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    // Setters
    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
