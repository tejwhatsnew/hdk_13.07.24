package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.OrderNotFoundException;
import com.hsbc.ecommerce.model.DashboardData;
import com.hsbc.ecommerce.model.Order;
import com.hsbc.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAOImpl implements OrderDAO {

    // Mock database
    private List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> getOrderHistory() {
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No order history found.");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.getUserId() == userId) {
                userOrders.add(order);
            }
        }
        if (userOrders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for user ID: " + userId);
        }
        return userOrders;
    }

    @Override
    public Object getOrderDashboard() {
        DashboardData dashboardData = new DashboardData();

        // Initialize statistics
        double totalRevenue = 0.0;
        int totalOrders = 0;
        Map<String, Integer> productSales = new HashMap<>();

        List<Order> orders = getOrderHistory();

        for (Order order : orders) {
            // Update revenue and order count
            totalRevenue += order.getTotalAmount();
            totalOrders++;

            // Track sales per product
            for (Product item : order.getProducts()) {
                productSales.put(item.getName(), productSales.getOrDefault(item.getName(), 0) + item.getStockQuantity());
            }
        }

        // Populate dashboard data
        dashboardData.setTotalRevenue(totalRevenue);
        dashboardData.setTotalOrders(totalOrders);
        dashboardData.setProductSales(productSales);

        return dashboardData;
    }
}
