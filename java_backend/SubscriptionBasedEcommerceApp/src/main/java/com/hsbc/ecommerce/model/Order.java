package com.hsbc.ecommerce.model;

import java.util.Date;

public class Order {
    private int id;
    private int customerSubscriptionId;
    private int productId;  // New attribute
    private Date orderDate;
    private Date deliveryDate;
    private String status;
    private double totalAmount; // Assuming this is calculated elsewhere

    // Getters and Setters for all attributes including productId
    public int getOrderId() {
        return id;
    }

    public void setOrderId(int id) {
        this.id = id;
    }

    public int getCustomerSubscriptionId() {
        return customerSubscriptionId;
    }

    public void setCustomerSubscriptionId(int customerSubscriptionId) {
        this.customerSubscriptionId = customerSubscriptionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
