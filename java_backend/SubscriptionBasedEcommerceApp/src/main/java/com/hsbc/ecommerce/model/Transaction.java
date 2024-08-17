package com.hsbc.ecommerce.model;

import java.util.Date;

public class Transaction {
    private int id;
    private int userId;
    private double amount;
    private Date transactionDate;
    private String type; // e.g., "Top-up", "Purchase", "Refund", etc.

    // Constructor
    public Transaction(int id, int userId, double amount, Date transactionDate, String type) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}