package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrderHistory();
    List<Order> getOrdersByUserId(int userId);
    Object getOrderDashboard();  // You may use a more specific return type for analytics and statistics.
}