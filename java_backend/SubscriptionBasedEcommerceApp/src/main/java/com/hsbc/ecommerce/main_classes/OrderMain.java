package com.hsbc.ecommerce.main_classes;

import com.hsbc.ecommerce.dao.OrderDAO;
import com.hsbc.ecommerce.dao.OrderDAOImpl;
import com.hsbc.ecommerce.model.Order;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class OrderMain {
    public static void main(String[] args) {
        Connection connection = null;

        try {
            // Establish database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommercedb", "root", "tejas@123");

            // Initialize DAO
            OrderDAO orderDAO = new OrderDAOImpl(connection);

            // Add a new order
//            Order newOrder = new Order();
//            newOrder.setCustomerSubscriptionId(1);
//            newOrder.setProductId(1);  // Set the product ID here
//            newOrder.setOrderDate(new java.util.Date());
//            newOrder.setDeliveryDate(new java.util.Date(System.currentTimeMillis() + 86400000L)); // +1 day
//            newOrder.setStatus("PENDING");
//            newOrder.setTotalAmount(99.99);  // Example total amount
//            orderDAO.addOrder(newOrder);
//            System.out.println("Order added successfully!");

            // Fetch order history
            List<Order> orders = orderDAO.getAllOrders();
            System.out.println("Order History:");
            for (Order order : orders) {
                System.out.println(order);
            }

            // Fetch orders by customerSubscriptionId
            int customerSubscriptionId = 1; // Example user ID
            List<Order> userOrders = orderDAO.getOrdersByCustomerSubscriptionId(customerSubscriptionId);
            System.out.println("Orders for Customer Subscription ID " + customerSubscriptionId + ":");
            for (Order order : userOrders) {
                System.out.println(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the database connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
