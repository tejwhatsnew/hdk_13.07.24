package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.model.Product;

import java.util.List;

public interface ProductDAO {
    void addProduct(Product product);
    Product getProductById(int productId);
    List<Product> getAllProducts();
    void updateProduct(Product product);
    void deleteProduct(int productId);
}
