package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.dao.ProductDAO;
import com.hsbc.ecommerce.dao.exceptions.ProductNotFoundException;
import com.hsbc.ecommerce.model.Product;

import java.util.List;

public class ProductServiceImpl implements ProductService {

    private ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @Override
    public void addProduct(Product product) {
        try {
            productDAO.addProduct(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add product: " + product.getName(), e);
        }
    }

    @Override
    public void updateProduct(Product product) {
        try {
            productDAO.updateProduct(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update product: " + product.getName(), e);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            productDAO.deleteProduct(productId);
        } catch (Exception e) {
            throw new ProductNotFoundException("Failed to delete product with ID: " + productId);
        }
    }

    @Override
    public List<Product> listProducts() {
        List<Product> products = productDAO.getAllProducts();
        if (products == null || products.isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }
        return products;
    }

    @Override
    public List<Product> listProductsByCategory(String category) {
        List<Product> products = productDAO.getProductsByCategory(category);
        if (products == null || products.isEmpty()) {
            throw new ProductNotFoundException("No products found in category: " + category);
        }
        return products;
    }
}