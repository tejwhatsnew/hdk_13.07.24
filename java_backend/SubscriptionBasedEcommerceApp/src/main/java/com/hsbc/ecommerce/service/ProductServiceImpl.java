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
        productDAO.addProduct(product);
    }

    @Override
    public Product viewProduct(int productId) {
        Product product = productDAO.getProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found.");
        }
        return product;
    }

    @Override
    public List<Product> listAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public void updateProduct(Product product) {
        try {
            productDAO.updateProduct(product);
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + product.getProductId() + " not found for update.");
        }
    }

    @Override
    public void removeProduct(int productId) {
        try {
            productDAO.deleteProduct(productId);
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found for deletion.");
        }
    }
}