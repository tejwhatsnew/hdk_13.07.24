package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.dao.exceptions.SubscriptionNotFoundException;
import com.hsbc.ecommerce.model.Subscription;

import java.util.List;

public interface SubscriptionService {
    void createSubscription(Subscription subscription);
    Subscription getSubscriptionById(int subscriptionId) throws SubscriptionNotFoundException;
    List<Subscription> getSubscriptionsByCustomerId(int customerId);
    void updateSubscription(Subscription subscription) throws SubscriptionNotFoundException;
    void cancelSubscription(int subscriptionId) throws SubscriptionNotFoundException;
}
