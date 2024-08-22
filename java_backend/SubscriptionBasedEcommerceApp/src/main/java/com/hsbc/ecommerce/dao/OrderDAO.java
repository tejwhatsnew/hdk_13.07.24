package com.hsbc.ecommerce.dao;



import com.hsbc.ecommerce.model.Order;

import java.util.List;

public interface OrderDAO {
    void addOrder(Order order);
    Order getOrderById(int orderId);
    List<Order> getAllOrders();
    void updateOrder(Order order);
    void deleteOrder(int orderId);
    List<Order> getOrdersByCustomerSubscriptionId(int customerSubscriptionId);
}
