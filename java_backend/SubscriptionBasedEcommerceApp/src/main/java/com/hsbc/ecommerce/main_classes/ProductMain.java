package com.hsbc.ecommerce.main_classes;

import com.hsbc.ecommerce.dao.ProductDAO;
import com.hsbc.ecommerce.dao.ProductDAOImpl;
import com.hsbc.ecommerce.dao.exceptions.ProductNotFoundException;
import com.hsbc.ecommerce.model.Product;
import com.hsbc.ecommerce.service.ProductService;
import com.hsbc.ecommerce.service.ProductServiceImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ProductMain {
    public static void main(String[] args) {
        // Database connection setup
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerceDatabase", "root", "tejas@123")) {

            // Create DAO and Service instances
            ProductDAO productDAO = new ProductDAOImpl(connection);
            ProductService productService = new ProductServiceImpl(productDAO);

            // Create a new product
            Product newProduct = new Product();
            newProduct.setName("Sample Product");
            newProduct.setDescription("This is a sample product.");
            newProduct.setCategory("Snacks");
            newProduct.setPrice(19.99);
            newProduct.setImageUrl("http://example.com/product.jpg");
            newProduct.setActive(true);
            newProduct.setStock(100);  // Set initial stock value

            productService.addProduct(newProduct);
            System.out.println("Product added with ID: " + newProduct.getProductId());

            // View the product by ID
            try {
                Product product = productService.viewProduct(newProduct.getProductId());
                System.out.println("Product Retrieved: " + product);
            } catch (ProductNotFoundException e) {
                System.err.println(e.getMessage());
            }

            // List all products
            List<Product> products = productService.listAllProducts();
            System.out.println("All Products: " + products);

            // Update the product
            newProduct.setPrice(24.99);
            newProduct.setStock(80);  // Update stock value
            try {
                productService.updateProduct(newProduct);
                System.out.println("Product price updated to 24.99 and stock updated to 80.");
            } catch (ProductNotFoundException e) {
                System.err.println(e.getMessage());
            }

            // Remove the product
//            try {
//                productService.removeProduct(newProduct.getProductId());
//                System.out.println("Product removed.");
//            } catch (ProductNotFoundException e) {
//                System.err.println(e.getMessage());
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}