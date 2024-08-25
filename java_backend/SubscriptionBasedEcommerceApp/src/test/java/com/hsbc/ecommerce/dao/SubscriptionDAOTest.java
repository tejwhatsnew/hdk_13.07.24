package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.CustomerNotFoundException;
import com.hsbc.ecommerce.dao.exceptions.SubscriptionNotFoundException;
import com.hsbc.ecommerce.model.Subscription;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SubscriptionDAOTest {

    private Connection connection;
    private SubscriptionDAOImpl subscriptionDAO;

    @BeforeEach
    void setUp() throws SQLException {
        // Use SQLite in-memory database for testing
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        subscriptionDAO = new SubscriptionDAOImpl();

        // Create necessary tables for testing
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE users (" +
                    "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, " +
                    "email TEXT)");

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
    void testAddSubscription() throws SQLException, SubscriptionNotFoundException {
        Subscription subscription = new Subscription(1, 1, "Monthly", new java.util.Date(), new java.util.Date(), true);
        subscriptionDAO.addSubscription(subscription);

        assertNotNull(subscription.getId());  // Ensure subscription ID is set
        Subscription retrievedSubscription = subscriptionDAO.getSubscriptionById(subscription.getId());
        assertEquals("Monthly", retrievedSubscription.getType());
        assertTrue(retrievedSubscription.isActive());
    }

    @Test
    void testGetSubscriptionById() throws SQLException, SubscriptionNotFoundException {
        Subscription subscription = new Subscription(1, 1, "Annual", new java.util.Date(), new java.util.Date(), false);
        subscriptionDAO.addSubscription(subscription);

        Subscription retrievedSubscription = subscriptionDAO.getSubscriptionById(subscription.getId());
        assertEquals("Annual", retrievedSubscription.getType());
        assertFalse(retrievedSubscription.isActive());
    }

    @Test
    void testGetSubscriptionsByCustomerId() throws SQLException, CustomerNotFoundException {
        // Adding subscriptions for the customer
        Subscription subscription1 = new Subscription(1, 1, "Weekly", new java.util.Date(), new java.util.Date(), true);
        Subscription subscription2 = new Subscription(1, 1, "Monthly", new java.util.Date(), new java.util.Date(), false);

        subscriptionDAO.addSubscription(subscription1);
        subscriptionDAO.addSubscription(subscription2);

        List<Subscription> subscriptions = subscriptionDAO.getSubscriptionsByCustomerId(1);
        assertEquals(2, subscriptions.size());
    }

    @Test
    void testUpdateSubscription() throws SQLException, SubscriptionNotFoundException {
        Subscription subscription = new Subscription(1, 1, "Quarterly", new java.util.Date(), new java.util.Date(), true);
        subscriptionDAO.addSubscription(subscription);

        // Update the subscription's details
        subscription.setType("Bi-Annual");
        subscription.setActive(false);
        subscriptionDAO.updateSubscription(subscription);

        Subscription updatedSubscription = subscriptionDAO.getSubscriptionById(subscription.getId());
        assertEquals("Bi-Annual", updatedSubscription.getType());
        assertFalse(updatedSubscription.isActive());
    }

    @Test
    void testDeleteSubscription() throws SQLException, SubscriptionNotFoundException {
        Subscription subscription = new Subscription(1, 1, "Monthly", new java.util.Date(), new java.util.Date(), true);
        subscriptionDAO.addSubscription(subscription);

        subscriptionDAO.deleteSubscription(subscription.getId());

        assertThrows(SubscriptionNotFoundException.class, () -> {
            subscriptionDAO.getSubscriptionById(subscription.getId());
        });
    }

    @Test
    void testGetSubscriptionById_NotFound() {
        assertThrows(SubscriptionNotFoundException.class, () -> {
            subscriptionDAO.getSubscriptionById(999);  // Non-existing ID
        });
    }

    @Test
    void testGetSubscriptionsByCustomerId_NotFound() {
        assertThrows(RuntimeException.class, () -> {
            subscriptionDAO.getSubscriptionsByCustomerId(999);  // Non-existing customer ID
        });
    }
}
