package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.ProductNotFoundException;
import com.hsbc.ecommerce.model.Product;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDAOTest {

    private Connection connection;
    private ProductDAOImpl productDAO;

    @BeforeEach
    void setUp() throws SQLException {
        // Use SQLite in-memory database for testing
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        productDAO = new ProductDAOImpl(connection);

        // Create necessary tables for testing
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE Products (" +
                    "product_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "description TEXT, " +
                    "category TEXT, " +
                    "price REAL, " +
                    "image_url TEXT, " +
                    "is_active BOOLEAN, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "stock_quantity INTEGER)");
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testAddProduct() throws SQLException {
        Product product = new Product("Test Product", "Test Description", "Test Category", 100.0, "test_image.jpg", true, 10);
        productDAO.addProduct(product);

        assertNotNull(product.getProductId());  // Ensure product ID is set
        Product retrievedProduct = productDAO.getProductById(product.getProductId());
        assertEquals("Test Product", retrievedProduct.getName());
    }

    @Test
    void testGetProductById() throws SQLException {
        Product product = new Product("Test Product", "Test Description", "Test Category", 100.0, "test_image.jpg", true, 10);
        productDAO.addProduct(product);

        Product retrievedProduct = productDAO.getProductById(product.getProductId());
        assertEquals("Test Product", retrievedProduct.getName());
        assertEquals("Test Description", retrievedProduct.getDescription());
    }

    @Test
    void testGetAllProducts() throws SQLException {
        Product product1 = new Product("Product 1", "Description 1", "Category 1", 100.0, "image1.jpg", true, 10);
        Product product2 = new Product("Product 2", "Description 2", "Category 2", 200.0, "image2.jpg", true, 20);

        productDAO.addProduct(product1);
        productDAO.addProduct(product2);

        List<Product> products = productDAO.getAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    void testUpdateProduct() throws SQLException {
        Product product = new Product("Original Product", "Original Description", "Original Category", 100.0, "original_image.jpg", true, 10);
        productDAO.addProduct(product);

        // Update the product details
        product.setName("Updated Product");
        product.setDescription("Updated Description");
        productDAO.updateProduct(product);

        Product updatedProduct = productDAO.getProductById(product.getProductId());
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals("Updated Description", updatedProduct.getDescription());
    }

    @Test
    void testDeleteProduct() throws SQLException {
        Product product = new Product("Test Product", "Test Description", "Test Category", 100.0, "test_image.jpg", true, 10);
        productDAO.addProduct(product);

        productDAO.deleteProduct(product.getProductId());

        assertThrows(ProductNotFoundException.class, () -> {
            productDAO.getProductById(product.getProductId());
        });
    }

    @Test
    void testGetProductById_NotFound() {
        assertThrows(ProductNotFoundException.class, () -> {
            productDAO.getProductById(999);  // Non-existing ID
        });
    }
}
