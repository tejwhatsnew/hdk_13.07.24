package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.SubscriptionNotFoundException;
import com.hsbc.ecommerce.model.Subscription;

import java.util.List;

public interface SubscriptionDAO {
    void addSubscription(Subscription subscription);
    Subscription getSubscriptionById(int subscriptionId) throws SubscriptionNotFoundException;
    List<Subscription> getSubscriptionsByCustomerId(int customerId);
    void updateSubscription(Subscription subscription) throws SubscriptionNotFoundException;
    void deleteSubscription(int subscriptionId) throws SubscriptionNotFoundException;
}
