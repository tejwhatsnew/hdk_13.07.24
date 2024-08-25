package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.CustomerNotFoundException;
import com.hsbc.ecommerce.model.Customer;
import com.hsbc.ecommerce.model.Subscription;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAOTest {

    private Connection connection;
    private CustomerDAOImpl customerDAO;

    @BeforeEach
    void setUp() throws SQLException {
        // Use SQLite in-memory database for testing
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        customerDAO = new CustomerDAOImpl();

        // Create necessary tables for testing
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, " +
                    "email TEXT, " +
                    "wallet_balance REAL)");

            stmt.execute("CREATE TABLE Subscriptions (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id INTEGER, " +
                    "type TEXT, " +
                    "start_date DATE, " +
                    "end_date DATE, " +
                    "active BOOLEAN, " +
                    "FOREIGN KEY(user_id) REFERENCES users(user_id))");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testAddCustomer() throws SQLException, CustomerNotFoundException {
        Customer customer = new Customer(101, "John Doe", "john@example.com", 50.0, null);
        customerDAO.addCustomer(customer);

        assertNotNull(customer.getId());  // Ensure customer ID is set
        Customer retrievedCustomer = customerDAO.getCustomerById(customer.getId());
        assertEquals("John Doe", retrievedCustomer.getName());
        assertEquals(50.0, retrievedCustomer.getWalletBalance());
    }

    @Test
    void testGetCustomerById() throws SQLException, CustomerNotFoundException {
        Customer customer = new Customer(11,"Jane Doe", "jane@example.com", 75.0, null);
        customerDAO.addCustomer(customer);

        Customer retrievedCustomer = customerDAO.getCustomerById(customer.getId());
        assertEquals("Jane Doe", retrievedCustomer.getName());
        assertEquals("jane@example.com", retrievedCustomer.getEmail());
        assertEquals(75.0, retrievedCustomer.getWalletBalance());
    }

    @Test
    void testGetAllCustomers() throws SQLException {
        Customer customer1 = new Customer(102, "Alice", "alice@example.com", 100.0, null);
        Customer customer2 = new Customer(101,"Bob", "bob@example.com", 200.0, null);

        customerDAO.addCustomer(customer1);
        customerDAO.addCustomer(customer2);

        List<Customer> customers = customerDAO.getAllCustomers();
        assertEquals(2, customers.size());
    }

    @Test
    void testUpdateCustomer() throws SQLException, CustomerNotFoundException {
        Customer customer = new Customer(101, "Charlie", "charlie@example.com", 150.0, null);
        customerDAO.addCustomer(customer);

        // Update the customer's details
        customer.setName("Charlie Updated");
        customer.setEmail("charlie.updated@example.com");
        customer.setWalletBalance(300.0);
        customerDAO.updateCustomer(customer);

        Customer updatedCustomer = customerDAO.getCustomerById(customer.getId());
        assertEquals("Charlie Updated", updatedCustomer.getName());
        assertEquals("charlie.updated@example.com", updatedCustomer.getEmail());
        assertEquals(300.0, updatedCustomer.getWalletBalance());
    }

    @Test
    void testDeleteCustomer() throws SQLException, CustomerNotFoundException {
        Customer customer = new Customer(101, "David", "david@example.com", 500.0, null);
        customerDAO.addCustomer(customer);

        customerDAO.deleteCustomer(customer.getId());

        assertThrows(CustomerNotFoundException.class, () -> {
            customerDAO.getCustomerById(customer.getId());
        });
    }

    @Test
    void testTopUpWallet() throws SQLException, CustomerNotFoundException {
        Customer customer = new Customer(102, "Eve", "eve@example.com", 250.0, null);
        customerDAO.addCustomer(customer);

        customerDAO.topUpWallet(customer.getId(), 100.0);
        Customer updatedCustomer = customerDAO.getCustomerById(customer.getId());
        assertEquals(350.0, updatedCustomer.getWalletBalance());
    }

    @Test
    void testDeductFromWallet() throws SQLException, CustomerNotFoundException {
        Customer customer = new Customer(101, "Frank", "frank@example.com", 400.0, null);
        customerDAO.addCustomer(customer);

        customerDAO.deductFromWallet(customer.getId(), 100.0);
        Customer updatedCustomer = customerDAO.getCustomerById(customer.getId());
        assertEquals(300.0, updatedCustomer.getWalletBalance());
    }

    @Test
    void testGetSubscriptionsByCustomerId() throws SQLException, CustomerNotFoundException {
        Customer customer = new Customer(101, "Grace", "grace@example.com", 600.0, null);
        customerDAO.addCustomer(customer);

        // Add a subscription for this customer
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO Subscriptions (user_id, type, start_date, end_date, active) " +
                    "VALUES (" + customer.getId() + ", 'Monthly', '2024-08-01', '2024-09-01', 1)");
        }

        List<Subscription> subscriptions = customerDAO.getSubscriptionsByCustomerId(customer.getId());
        assertEquals(1, subscriptions.size());
        assertEquals("Monthly", subscriptions.get(0).getType());
    }

    @Test
    void testGetCustomerById_NotFound() {
        assertThrows(CustomerNotFoundException.class, () -> {
            customerDAO.getCustomerById(999);  // Non-existing ID
        });
    }
}
