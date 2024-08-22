package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    Product viewProduct(int productId);
    List<Product> listAllProducts();
    void updateProduct(Product product);
    void removeProduct(int productId);
}