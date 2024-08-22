package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.model.Order;

import java.util.List;

public interface OrderService {
    void placeOrder(Order order);
    Order viewOrder(int orderId);
    List<Order> listAllOrders();
    void updateOrder(Order order);
    void cancelOrder(int orderId);
}