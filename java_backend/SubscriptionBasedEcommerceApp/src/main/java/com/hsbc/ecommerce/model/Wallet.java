package com.hsbc.ecommerce.model;

import java.util.List;

public class Wallet {
    private int userId;
    private double balance;
    private List<Transaction> transactions;

    // Constructor
    public Wallet(int userId, double balance, List<Transaction> transactions) {
        this.userId = userId;
        this.balance = balance;
        this.transactions = transactions;
    }

    // Getters and Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }

    // Additional methods
    public void topUp(double amount) { this.balance += amount; }
    public void deduct(double amount) { this.balance -= amount; }
    public boolean hasSufficientBalance(double amount) { return this.balance >= amount; }
}