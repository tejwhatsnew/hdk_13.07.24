package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    void addProduct(Product product);
    void updateProduct(Product product);
    void deleteProduct(int productId);
    List<Product> listProducts();
    List<Product> listProductsByCategory(String category);
}