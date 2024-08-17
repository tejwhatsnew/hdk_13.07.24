package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.ProductNotFoundException;
import com.hsbc.ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    // Mock database
    private List<Product> products = new ArrayList<>();

    @Override
    public void addProduct(Product product) {
        products.add(product);
    }

    @Override
    public void updateProduct(Product product) {
        int index = findProductIndexById(product.getId());
        if (index == -1) {
            throw new ProductNotFoundException("Product with ID " + product.getId() + " not found.");
        }
        products.set(index, product);
    }

    @Override
    public void deleteProduct(int productId) {
        int index = findProductIndexById(productId);
        if (index == -1) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
        products.remove(index);
    }

    @Override
    public Product getProductById(int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Product with ID " + productId + " not found."));
    }

    @Override
    public List<Product> getAllProducts() {
        if (products.isEmpty()) {
            throw new ProductNotFoundException("No products found.");
        }
        return products;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        List<Product> categoryProducts = new ArrayList<>();
        for (Product product : products) {
            if (product.getCategory().equalsIgnoreCase(category)) {
                categoryProducts.add(product);
            }
        }
        if (categoryProducts.isEmpty()) {
            throw new ProductNotFoundException("No products found in category: " + category);
        }
        return categoryProducts;
    }

    private int findProductIndexById(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == productId) {
                return i;
            }
        }
        return -1;
    }
}
