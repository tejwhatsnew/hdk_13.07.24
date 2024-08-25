package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.dao.AdminDAO;
import com.hsbc.ecommerce.model.*;

import java.util.List;

public class AdminServiceImpl implements AdminService {
    
    private AdminDAO adminDAO;

    public AdminServiceImpl(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public void updateProduct(Product product) {
        adminDAO.updateProduct(product);
    }

    @Override
    public void deleteProduct(int productId) {
        adminDAO.deleteProduct(productId);
    }

    @Override
    public void addSubscription(Subscription subscription) {
        adminDAO.addSubscription(subscription);
    }

    @Override
    public void deactivateSubscription(int subscriptionId) {
        adminDAO.deactivateSubscription(subscriptionId);
    }

    @Override
    public void logAdminAction(int adminId, String actionType, String actionDetails) {
        adminDAO.logAdminAction(adminId, actionType, actionDetails);
    }

    @Override
    public List<AdminAction> getAdminActions() {
        return adminDAO.getAdminActions();
    }

    @Override
    public List<Product> getAllProducts() {
        return adminDAO.getAllProducts();
    }

    @Override
    public List<Subscription> getAllSubscriptions() {
        return adminDAO.getAllSubscriptions();
    }

    @Override
    public void activateSubscription(int subscriptionId) {
        adminDAO.activateSubscription(subscriptionId);
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return adminDAO.searchProducts(keyword);
    }

    @Override
    public List<Order> getPendingOrders() {
        return adminDAO.getPendingOrders();
    }

    @Override
    public List<DeliverySchedule> getPendingDeliveries() {
        return adminDAO.getPendingDeliveries();
    }

    @Override
    public void updateOrderStatus(int orderId, String status) {
        adminDAO.updateOrderStatus(orderId, status);
    }

    @Override
    public void updateDeliveryStatus(int scheduleId, String status) {
        adminDAO.updateDeliveryStatus(scheduleId, status);
    }
}
