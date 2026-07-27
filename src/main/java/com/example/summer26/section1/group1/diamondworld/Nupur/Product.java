package com.example.summer26.section1.group1.diamondworld.Nupur;

public class Product  {
    private String productId;
    private String productName;
    private int stockQuantity;
    private double price;

    public Product(String productId, String productName, int stockQuantity, double price) {
        this.productId = productId;
        this.productName = productName;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", price=" + price +
                '}';
    }
}
