package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.model.Product;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOTest {

    private AdminDaoImpl adminDao;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        // Use SQLite in-memory database for testing
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
       // Ensure AdminDaoImpl uses this connection
        adminDao = new AdminDaoImpl();

        // Create necessary tables for testing
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Products (product_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, category TEXT, price REAL, image_url TEXT, is_active BOOLEAN, stock_quantity INTEGER)");
            stmt.execute("CREATE TABLE Delivery_Schedule (schedule_id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, FOREIGN KEY(product_id) REFERENCES Products(product_id) ON DELETE CASCADE)");
            stmt.execute("CREATE TABLE Subscriptions (subscription_id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, FOREIGN KEY(product_id) REFERENCES Products(product_id) ON DELETE CASCADE)");
            stmt.execute("CREATE TABLE Admin_Actions (action_id INTEGER PRIMARY KEY AUTOINCREMENT, admin_id INTEGER, action_type TEXT, action_details TEXT, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
            stmt.execute("CREATE TABLE Orders (order_id INTEGER PRIMARY KEY AUTOINCREMENT, customer_subscription_id INTEGER, order_date DATE, delivery_date DATE, status TEXT)");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testUpdateProduct() throws SQLException {
        // Insert a product to update
        Product product = new Product(1, "Test Product", "Description", "Category", 10.0, "image.jpg", true, 10);
        adminDao.updateProduct(product);

        // Retrieve and verify the updated product
        List<Product> products = adminDao.getAllProducts();
        assertEquals(1, products.size());
        assertEquals("Test Product", products.get(0).getName());
    }

    @Test
    void testDeleteProduct() throws SQLException {
        // Insert product and related entries first
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("INSERT INTO Products (product_id, name, description, category, price, image_url, is_active, stock_quantity) VALUES (1, 'Test Product', 'Description', 'Category', 10.0, 'image.jpg', 1, 10)");
            stmt.execute("INSERT INTO Delivery_Schedule (product_id) VALUES (1)");
            stmt.execute("INSERT INTO Subscriptions (product_id) VALUES (1)");
        }

        adminDao.deleteProduct(1);

        // Check that product and related entries are deleted
        List<Product> products = adminDao.getAllProducts();
        assertTrue(products.isEmpty());
    }
}
