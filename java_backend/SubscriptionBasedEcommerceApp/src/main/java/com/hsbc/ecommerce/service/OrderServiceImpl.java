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
    public void placeOrder(Order order) {
        orderDAO.addOrder(order);
    }

    @Override
    public Order viewOrder(int orderId) {
        Order order = orderDAO.getOrderById(orderId);
        if (order == null) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
        }
        return order;
    }

    @Override
    public List<Order> listAllOrders() {
        return orderDAO.getAllOrders();
    }

    @Override
    public void updateOrder(Order order) {
        try {
            orderDAO.updateOrder(order);
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException("Order with ID " + order.getOrderId() + " not found for update.");
        }
    }

    @Override
    public void cancelOrder(int orderId) {
        try {
            orderDAO.deleteOrder(orderId);
        } catch (OrderNotFoundException e) {
            throw new OrderNotFoundException("Order with ID " + orderId + " not found for cancellation.");
        }
    }
}