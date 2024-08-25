package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.OrderNotFoundException;
import com.hsbc.ecommerce.model.Order;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOTest {

    private Connection connection;
    private OrderDAO orderDAO;

    @BeforeEach
    void setUp() throws SQLException {
        // Use SQLite in-memory database for testing
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        orderDAO = new OrderDAOImpl(connection);

        // Create necessary tables for testing
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Orders (" +
                    "order_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "customer_subscription_id INTEGER, " +
                    "product_id INTEGER, " +
                    "order_date DATE, " +
                    "delivery_date DATE, " +
                    "status TEXT, " +
                    "total_amount REAL)");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testAddOrder() throws SQLException {
        Order order = new Order(1, 2, 10, new Date(), new Date(), "Pending", 100.0);
        orderDAO.addOrder(order);

        assertNotNull(order.getOrderId());  // Ensure order ID is set
        Order retrievedOrder = orderDAO.getOrderById(order.getOrderId());
        assertEquals(1, retrievedOrder.getCustomerSubscriptionId());
        assertEquals(2, retrievedOrder.getProductId());
        assertEquals("Pending", retrievedOrder.getStatus());
        assertEquals(100.0, retrievedOrder.getTotalAmount());
    }

    @Test
    void testGetOrderById() throws SQLException, OrderNotFoundException {
        Order order = new Order(1, 2, 10, new Date(), new Date(), "Completed", 200.0);
        orderDAO.addOrder(order);

        Order retrievedOrder = orderDAO.getOrderById(order.getOrderId());
        assertEquals(1, retrievedOrder.getCustomerSubscriptionId());
        assertEquals(2, retrievedOrder.getProductId());
        assertEquals("Completed", retrievedOrder.getStatus());
        assertEquals(200.0, retrievedOrder.getTotalAmount());
    }

    @Test
    void testGetAllOrders() throws SQLException {
        Order order1 = new Order(1, 2, 10, new Date(), new Date(), "Pending", 150.0);
        Order order2 = new Order(2, 3, 10, new Date(), new Date(), "Delivered", 250.0);

        orderDAO.addOrder(order1);
        orderDAO.addOrder(order2);

        List<Order> orders = orderDAO.getAllOrders();
        assertEquals(2, orders.size());
    }

    @Test
    void testUpdateOrder() throws SQLException, OrderNotFoundException {
        Order order = new Order(1, 2, 10, new Date(), new Date(), "Shipped", 300.0);
        orderDAO.addOrder(order);

        // Update the order's details
        order.setStatus("Delivered");
        order.setTotalAmount(350.0);
        orderDAO.updateOrder(order);

        Order updatedOrder = orderDAO.getOrderById(order.getOrderId());
        assertEquals("Delivered", updatedOrder.getStatus());
        assertEquals(350.0, updatedOrder.getTotalAmount());
    }

    @Test
    void testDeleteOrder() throws SQLException, OrderNotFoundException {
        Order order = new Order(1, 2,10, new Date(), new Date(), "Canceled", 400.0);
        orderDAO.addOrder(order);

        orderDAO.deleteOrder(order.getOrderId());

        assertThrows(OrderNotFoundException.class, () -> {
            orderDAO.getOrderById(order.getOrderId());
        });
    }

    @Test
    void testGetOrdersByCustomerSubscriptionId() throws SQLException {
        Order order1 = new Order(1, 2, 10, new Date(), new Date(), "Pending", 150.0);
        Order order2 = new Order(1, 3, 10, new Date(), new Date(), "Delivered", 250.0);

        orderDAO.addOrder(order1);
        orderDAO.addOrder(order2);

        List<Order> orders = orderDAO.getOrdersByCustomerSubscriptionId(1);
        assertEquals(2, orders.size());
    }

    @Test
    void testGetOrderById_NotFound() {
        assertThrows(OrderNotFoundException.class, () -> {
            orderDAO.getOrderById(999);  // Non-existing ID
        });
    }
}
