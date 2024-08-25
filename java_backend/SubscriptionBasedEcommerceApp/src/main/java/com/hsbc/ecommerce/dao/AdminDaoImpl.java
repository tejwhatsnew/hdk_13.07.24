package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.AdminDAO;
import com.hsbc.ecommerce.model.AdminAction;
import com.hsbc.ecommerce.model.DeliverySchedule;
import com.hsbc.ecommerce.model.Order;
import com.hsbc.ecommerce.model.Product;
import com.hsbc.ecommerce.model.Subscription;
import com.hsbc.ecommerce.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDAO {



    @Override
    public void updateProduct(Product product) {
        String query = "UPDATE Products SET name = ?, description = ?, category = ?, price = ?, image_url = ?, is_active = ? WHERE product_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setString(3, product.getCategory());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getImageUrl());
            stmt.setBoolean(6, product.isActive());
            stmt.setInt(7, product.getProductId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating product: " + e.getMessage());
        }
    }

    public void deleteProduct(int productId) {
        String deleteScheduleQuery = "DELETE FROM Delivery_Schedule WHERE product_id = ?";
        String deleteSubscriptionQuery = "DELETE FROM Subscriptions WHERE product_id= ?";
        String deleteProductQuery = "DELETE FROM Products WHERE product_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement deleteScheduleStmt = connection.prepareStatement(deleteScheduleQuery);
             PreparedStatement deleteSubscriptionStmt = connection.prepareStatement(deleteSubscriptionQuery);
             PreparedStatement deleteProductStmt = connection.prepareStatement(deleteProductQuery)) {

            connection.setAutoCommit(false); // Start transaction

            // First, delete related rows in Delivery_Schedule
            deleteScheduleStmt.setInt(1, productId);
            deleteScheduleStmt.executeUpdate();

            deleteSubscriptionStmt.setInt(1, productId);
            deleteSubscriptionStmt.executeUpdate();

            // Then, delete the product
            deleteProductStmt.setInt(1, productId);
            deleteProductStmt.executeUpdate();

            connection.commit(); // Commit transaction

        } catch (SQLException e) {
            System.err.println("Error while deleting product: " + e.getMessage());
        }
    }


    @Override
    public void addSubscription(Subscription subscription) {
        String query = "INSERT INTO Subscriptions (product_id, frequency, custom_start_date, custom_end_date, price, is_active) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subscription.getId());
            stmt.setString(2, subscription.getType());
            stmt.setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
            stmt.setDouble(5, 1000);
            stmt.setBoolean(6, subscription.isActive());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while adding subscription: " + e.getMessage());
        }
    }

    @Override
    public void deactivateSubscription(int subscriptionId) {
        String query = "UPDATE Subscriptions SET is_active = FALSE WHERE subscription_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subscriptionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while deactivating subscription: " + e.getMessage());
        }
    }

    @Override
    public void logAdminAction(int adminId, String actionType, String actionDetails) {
        String query = "INSERT INTO Admin_Actions (admin_id, action_type, action_details) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, adminId);
            stmt.setString(2, actionType);
            stmt.setString(3, actionDetails);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while logging admin action: " + e.getMessage());
        }
    }

    @Override
    public List<AdminAction> getAdminActions() {
        List<AdminAction> adminActions = new ArrayList<>();
        String query = "SELECT * FROM Admin_Actions";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                AdminAction action = new AdminAction();
                action.setActionId(rs.getInt("action_id"));
                action.setAdminId(rs.getInt("admin_id"));
                action.setActionType(rs.getString("action_type"));
                action.setActionDetails(rs.getString("action_details"));
                action.setActionTimestamp(rs.getTimestamp("timestamp"));
                adminActions.add(action);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving admin actions: " + e.getMessage());
        }
        return adminActions;
    }

    @Override
    public List<Order> getPendingOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM Orders WHERE status = 'PENDING'";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
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
            System.err.println("Error while retrieving pending orders: " + e.getMessage());
        }
        return orders;
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        String query = "UPDATE Orders SET status = ? WHERE order_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating order status: " + e.getMessage());
        }
    }

    @Override
    public List<DeliverySchedule> getPendingDeliveries() {
        List<DeliverySchedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM Delivery_Schedule WHERE status = 'PENDING'";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                DeliverySchedule schedule = new DeliverySchedule();
                schedule.setScheduleId(rs.getInt("schedule_id"));
                schedule.setScheduledDeliveryDate(rs.getDate("delivery_date"));
                schedule.setOrderId(rs.getInt("order_id"));
                schedule.setProductId(rs.getInt("product_id"));
                schedule.setCustomerName(rs.getString("customer_name"));
                schedule.setAddress(rs.getString("address"));
                schedule.setStatus(rs.getString("status"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving pending deliveries: " + e.getMessage());
        }
        return schedules;
    }

    @Override
    public void updateDeliveryStatus(int scheduleId, String status) {
        String query = "UPDATE Delivery_Schedule SET status = ? WHERE schedule_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, status);
            stmt.setInt(2, scheduleId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while updating delivery status: " + e.getMessage());
        }
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setStock(rs.getInt("stock_quantity"));
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving all products: " + e.getMessage());
        }
        return products;
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        String query = "SELECT * FROM Subscriptions";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Subscription subscription = new Subscription();
                subscription.setId(rs.getInt("subscription_id"));
                subscription.setUserId(rs.getInt("product_id"));
                subscription.setType(rs.getString("frequency"));
                subscription.setStartDate(rs.getDate("custom_start_date"));
                subscription.setEndDate(rs.getDate("custom_end_date"));
                subscription.setActive(rs.getBoolean("is_active"));
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            System.err.println("Error while retrieving all subscriptions: " + e.getMessage());
        }
        return subscriptions;
    }

    @Override
    public void activateSubscription(int subscriptionId) {
        String query = "UPDATE Subscriptions SET is_active = TRUE WHERE subscription_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, subscriptionId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error while activating subscription: " + e.getMessage());
        }
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE name LIKE ? OR description LIKE ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setName(rs.getString("name"));
                    product.setDescription(rs.getString("description"));
                    product.setPrice(rs.getDouble("price"));
                    product.setStock(rs.getInt("stock_quantity"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error while searching products: " + e.getMessage());
        }
        return products;
    }
}
