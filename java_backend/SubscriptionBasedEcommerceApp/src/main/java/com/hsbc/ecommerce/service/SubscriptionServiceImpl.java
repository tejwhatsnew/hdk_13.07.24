package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.dao.SubscriptionDAO;
import com.hsbc.ecommerce.dao.exceptions.SubscriptionNotFoundException;
import com.hsbc.ecommerce.model.Subscription;
import com.hsbc.ecommerce.service.SubscriptionService;

import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDAO subscriptionDAO;

    public SubscriptionServiceImpl(SubscriptionDAO subscriptionDAO) {
        this.subscriptionDAO = subscriptionDAO;
    }

    @Override
    public void createSubscription(Subscription subscription) {
        subscriptionDAO.addSubscription(subscription);
    }

    @Override
    public Subscription getSubscriptionById(int subscriptionId) throws SubscriptionNotFoundException {
        return subscriptionDAO.getSubscriptionById(subscriptionId);
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomerId(int customerId) {
        return subscriptionDAO.getSubscriptionsByCustomerId(customerId);
    }

    @Override
    public void updateSubscription(Subscription subscription) throws SubscriptionNotFoundException {
        subscriptionDAO.updateSubscription(subscription);
    }

    @Override
    public void cancelSubscription(int subscriptionId) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionDAO.getSubscriptionById(subscriptionId);
        if (subscription != null && subscription.isActive()) {
            subscription.setActive(false);
            subscriptionDAO.updateSubscription(subscription);
        } else {
            throw new SubscriptionNotFoundException("Subscription with ID " + subscriptionId + " is either not found or already inactive.");
        }
    }
}
