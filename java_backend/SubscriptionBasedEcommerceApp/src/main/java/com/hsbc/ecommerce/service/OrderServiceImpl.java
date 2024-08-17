package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.dao.OrderDAO;
import com.hsbc.ecommerce.dao.exceptions.OrderNotFoundException;
import com.hsbc.ecommerce.model.Order;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public List<Order> getOrderHistory() {
        List<Order> orders = orderDAO.getOrderHistory();
        if (orders == null || orders.isEmpty()) {
            throw new OrderNotFoundException("No order history found.");
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = orderDAO.getOrdersByUserId(userId);
        if (orders == null || orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for user ID: " + userId);
        }
        return orders;
    }

    @Override
    public Object getOrderDashboard() {
        Object dashboardData = orderDAO.getOrderDashboard();
        if (dashboardData == null) {
            throw new RuntimeException("Failed to retrieve order dashboard data.");
        }
        return dashboardData;
    }
}