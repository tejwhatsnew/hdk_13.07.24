package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.CustomerNotFoundException;
import com.hsbc.ecommerce.model.Customer;
import com.hsbc.ecommerce.model.Subscription;
import com.hsbc.ecommerce.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CustomerDAOImpl implements CustomerDAO {

    private Connection connection = DatabaseConnection.getConnection();

    @Override
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customers (name, email, wallet_balance) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setDouble(3, customer.getWalletBalance());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
        String sql = "SELECT * FROM Customers WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapCustomer(rs);
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);  // Generic exception wrapping
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM Customers";
        List<Customer> customers = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(mapCustomer(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException{
        String sql = "UPDATE Customers SET name = ?, email = ?, wallet_balance = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setDouble(3, customer.getWalletBalance());
            ps.setInt(4, customer.getId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customer.getId() + " not found for update.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(int customerId) throws CustomerNotFoundException{
        String sql = "DELETE FROM Customers WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found for deletion.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void topUpWallet(int customerId, double amount) throws CustomerNotFoundException{
        String sql = "UPDATE Customers SET wallet_balance = wallet_balance + ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found for wallet top-up.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deductFromWallet(int customerId, double amount) throws CustomerNotFoundException{
        String sql = "UPDATE Customers SET wallet_balance = wallet_balance - ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, amount);
            ps.setInt(2, customerId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated == 0) {
                throw new CustomerNotFoundException("Customer with ID " + customerId + " not found for wallet deduction.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch subscriptions by customer ID
    public List<Subscription> getSubscriptionsByCustomerId(int customerId) throws CustomerNotFoundException{

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
        }

        // If customer exists, fetch subscriptions

        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT * FROM Subscriptions WHERE user_id = ?"; // Assuming 'user_id' is the correct column name
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Subscription subscription = new Subscription(
                        rs.getInt("id"),
                        rs.getInt("user_id"), // Corrected to match Subscription model
                        rs.getString("type"), // Assuming 'type' is the correct column name
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getBoolean("active") // Assuming 'active' is the correct column name
                );
                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    // Utility method to map ResultSet to Customer object
    private Customer mapCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getDouble("wallet_balance"),
                null  // Subscriptions can be fetched separately if needed
        );
        return customer;
    }
}
