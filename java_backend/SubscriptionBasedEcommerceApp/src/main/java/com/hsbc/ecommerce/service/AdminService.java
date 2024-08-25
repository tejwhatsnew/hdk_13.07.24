package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.model.*;

import java.util.List;

public interface AdminService {
    void updateProduct(Product product);
    void deleteProduct(int productId);

    void addSubscription(Subscription subscription);
    void deactivateSubscription(int subscriptionId);
    void logAdminAction(int adminId, String actionType, String actionDetails);

    List<AdminAction> getAdminActions();

    List<Order> getPendingOrders();
    void updateOrderStatus(int orderId, String status);

    List<DeliverySchedule> getPendingDeliveries();
    void updateDeliveryStatus(int scheduleId, String status);

    List<Product> getAllProducts();

    List<Subscription> getAllSubscriptions();

    void activateSubscription(int subscriptionId);

    List<Product> searchProducts(String keyword);
}
