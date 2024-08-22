package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.OrderNotFoundException;
import com.hsbc.ecommerce.model.Order;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private Connection connection;

    public OrderDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addOrder(Order order) {
        String sql = "INSERT INTO Orders (customer_subscription_id, product_id, order_date, delivery_date, status, total_amount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, order.getCustomerSubscriptionId());
            stmt.setInt(2, order.getProductId()); // Include productId
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDate(4, new java.sql.Date(order.getDeliveryDate().getTime()));
            stmt.setString(5, order.getStatus());
            stmt.setDouble(6, order.getTotalAmount());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM Orders WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerSubscriptionId(rs.getInt("customer_subscription_id"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setDeliveryDate(rs.getDate("delivery_date"));
                order.setStatus(rs.getString("status"));
                return order;
            } else {
                throw new OrderNotFoundException("Order with ID " + orderId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);  // Generic exception wrapping
        }
    }

    @Override
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM Orders";
        List<Order> orders = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerSubscriptionId(rs.getInt("customer_subscription_id"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setDeliveryDate(rs.getDate("delivery_date"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public void updateOrder(Order order) {
        String sql = "UPDATE Orders SET customer_subscription_id = ?, order_date = ?, delivery_date = ?, status = ? WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order.getCustomerSubscriptionId());
            ps.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            ps.setDate(3, new java.sql.Date(order.getDeliveryDate().getTime()));
            ps.setString(4, order.getStatus());
            ps.setInt(5, order.getOrderId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new OrderNotFoundException("Order with ID " + order.getOrderId() + " not found for update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM Orders WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new OrderNotFoundException("Order with ID " + orderId + " not found for deletion.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getOrdersByCustomerSubscriptionId(int customerSubscriptionId) {
        String sql = "SELECT * FROM Orders WHERE customer_subscription_id = ?";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerSubscriptionId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setCustomerSubscriptionId(rs.getInt("customer_subscription_id"));
                    order.setProductId(rs.getInt("product_id"));
                    order.setOrderDate(rs.getDate("order_date"));
                    order.setDeliveryDate(rs.getDate("delivery_date"));
                    order.setStatus(rs.getString("status"));
                    order.setTotalAmount(rs.getDouble("total_amount"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

}