package     com.hsbc.ecommerce.model;

import java.sql.Timestamp;
import java.util.Date;

public class Product {
    private int productId;
    private String name;
    private String description;
    private String category;
    private double price;
    private String imageUrl;
    private boolean isActive;
    private int stock; // New attribute for stock
    private Date createdAt;

    public Product() {
    }

    public Product(String name, String description, String category, double price, String imageUrl, boolean isActive, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.stock = stockQuantity;
    }

    public Product(int id, String name, String description, String category, double price, String imageUrl, boolean isActive, int stockQuantity) {
        this.productId = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.isActive = isActive;
        this.imageUrl = imageUrl;
        this.stock = stockQuantity;
    }

    // Getters and Setters for all attributes, including the new stock attribute
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format(
                "+------------------------------------------------------------------+\n" +
                        "| %-20s | %-40s |\n" +
                        "+------------------------------------------------------------------+\n" +
                        "| %-20s | %-40d |\n" +
                        "| %-20s | %-40s |\n" +
                        "| %-20s | %-40s |\n" +
                        "| %-20s | %-40s |\n" +
                        "| %-20s | %-40.2f |\n" +
                        "| %-20s | %-40s |\n" +
                        "| %-20s | %-40b |\n" +
                        "| %-20s | %-40s |\n" +
                        "| %-20s | %-40d |\n" +
                        "+------------------------------------------------------------------+\n",
                "Field", "Value",
                "Product ID", productId,
                "Name", name,
                "Description", description,
                "Category", category,
                "Price", price,
                "Image URL", imageUrl,
                "Active", isActive,
                "Created At", createdAt,
                "Stock Quantity", stock
        );
    }
}