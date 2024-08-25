package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.CustomerNotFoundException;
import com.hsbc.ecommerce.dao.exceptions.SubscriptionNotFoundException;
import com.hsbc.ecommerce.model.Subscription;
import com.hsbc.ecommerce.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDAOImpl implements SubscriptionDAO {

    private Connection connection = DatabaseConnection.getConnection();

    @Override
    public void addSubscription(Subscription subscription) {
        String sql = "INSERT INTO Subscriptions (user_id, type, start_date, end_date, active) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, subscription.getUserId());
            stmt.setString(2, subscription.getType());
            stmt.setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
            stmt.setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
            stmt.setBoolean(5, subscription.isActive());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating subscription failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    subscription.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating subscription failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Subscription getSubscriptionById(int subscriptionId) throws SubscriptionNotFoundException {
        String sql = "SELECT * FROM Subscriptions WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subscriptionId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Subscription(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("type"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getBoolean("active")
                );
            } else {
                throw new SubscriptionNotFoundException("Subscription with ID " + subscriptionId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);  // Wrap SQL exceptions in a runtime exception
        }
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomerId(int customerId) {
        // Check if the customer exists
        String checkCustomerSql = "SELECT 1 FROM Customers WHERE id = ?";
        try (PreparedStatement checkCustomerStmt = connection.prepareStatement(checkCustomerSql)) {
            checkCustomerStmt.setInt(1, customerId);
            ResultSet rs = checkCustomerStmt.executeQuery();
            if (!rs.next()) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e); // Wrap SQL exceptions in a runtime exception
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Fetch subscriptions if the customer exists
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Subscriptions WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Subscription subscription = new Subscription(
                            rs.getInt("id"),
                            rs.getInt("user_id"),
                            rs.getString("type"),
                            rs.getDate("start_date"),
                            rs.getDate("end_date"),
                            rs.getBoolean("active")
                    );
                    subscriptions.add(subscription);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    @Override
    public void updateSubscription(Subscription subscription) throws SubscriptionNotFoundException {
        String sql = "UPDATE Subscriptions SET user_id = ?, type = ?, start_date = ?, end_date = ?, active = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subscription.getUserId());
            ps.setString(2, subscription.getType());
            ps.setDate(3, new java.sql.Date(subscription.getStartDate().getTime()));
            ps.setDate(4, new java.sql.Date(subscription.getEndDate().getTime()));
            ps.setBoolean(5, subscription.isActive());
            ps.setInt(6, subscription.getId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SubscriptionNotFoundException("Subscription with ID " + subscription.getId() + " not found for update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSubscription(int subscriptionId) throws SubscriptionNotFoundException {
        String sql = "DELETE FROM Subscriptions WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, subscriptionId);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SubscriptionNotFoundException("Subscription with ID " + subscriptionId + " not found for deletion.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
