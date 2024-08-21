package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.model.Order;

import java.util.List;

public interface OrderDAO {
    List<Order> getOrderHistory();
    List<Order> getOrdersByUserId(int userId);
    Object getOrderDashboard();  // Customize this return type based on the specific dashboard requirements.
}