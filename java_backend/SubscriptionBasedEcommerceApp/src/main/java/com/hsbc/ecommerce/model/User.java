package com.hsbc.ecommerce.model;

import java.util.List;

public class User {
    private int id;
    private String name;
    private String email;
    private double walletBalance;
    private List<Subscription> subscriptions;

    // Constructor
    public User(int id, String name, String email, double walletBalance, List<Subscription> subscriptions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.walletBalance = walletBalance;
        this.subscriptions = subscriptions;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getWalletBalance() { return walletBalance; }
    public void setWalletBalance(double walletBalance) { this.walletBalance = walletBalance; }

    public List<Subscription> getSubscriptions() { return subscriptions; }
    public void setSubscriptions(List<Subscription> subscriptions) { this.subscriptions = subscriptions; }

    // Additional methods
    public void topUpWallet(double amount) { this.walletBalance += amount; }
    public void deductFromWallet(double amount) { this.walletBalance -= amount; }
    public boolean hasSufficientBalance(double amount) { return this.walletBalance >= amount; }
}
