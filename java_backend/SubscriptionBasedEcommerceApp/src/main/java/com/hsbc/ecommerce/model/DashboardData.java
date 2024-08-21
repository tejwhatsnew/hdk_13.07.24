package com.hsbc.ecommerce.model;

import java.util.Map;

public class DashboardData {
    private double totalRevenue;
    private int totalOrders;
    private Map<String, Integer> productSales;
    private Map<String, Integer> subscriptionTrends;

    // Getters and Setters
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

    public Map<String, Integer> getProductSales() { return productSales; }
    public void setProductSales(Map<String, Integer> productSales) { this.productSales = productSales; }

    public Map<String, Integer> getSubscriptionTrends() { return subscriptionTrends; }
    public void setSubscriptionTrends(Map<String, Integer> subscriptionTrends) { this.subscriptionTrends = subscriptionTrends; }
}

