package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.model.Product;

import java.util.List;

public interface ProductDAO {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
    Product getProductById(int productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
}