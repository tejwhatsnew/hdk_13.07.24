package com.hsbc.ecommerce.service;

import com.hsbc.ecommerce.dao.CustomerDAO;
import com.hsbc.ecommerce.dao.SubscriptionDAO;
import com.hsbc.ecommerce.dao.exceptions.CustomerNotFoundException;
import com.hsbc.ecommerce.dao.exceptions.SubscriptionNotFoundException;
import com.hsbc.ecommerce.model.Customer;
import com.hsbc.ecommerce.model.Subscription;

import java.util.List;

public class CustomerServiceImpl implements CustomerService {
    private final CustomerDAO customerDAO;
    private final SubscriptionDAO subscriptionDAO;

    public CustomerServiceImpl(CustomerDAO customerDAO, SubscriptionDAO subscriptionDAO) {
        this.customerDAO = customerDAO;
        this.subscriptionDAO = subscriptionDAO;
    }

    @Override
    public void addCustomer(Customer customer) {
        customerDAO.addCustomer(customer);
    }

    @Override
    public Customer getCustomerById(int customerId) throws CustomerNotFoundException {
        return customerDAO.getCustomerById(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    @Override
    public void updateCustomer(Customer customer) throws CustomerNotFoundException {
        customerDAO.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(int customerId) throws CustomerNotFoundException {
        customerDAO.deleteCustomer(customerId);
    }

    @Override
    public void topUpWallet(int customerId, double amount) throws CustomerNotFoundException {
        customerDAO.topUpWallet(customerId, amount);
    }

    @Override
    public void deductFromWallet(int customerId, double amount) throws CustomerNotFoundException {
        customerDAO.deductFromWallet(customerId, amount);
    }

    @Override
    public List<Subscription> getSubscriptionsByCustomerId(int customerId) throws CustomerNotFoundException {
        // Check if customer exists and then fetch subscriptions
        if (customerDAO.getCustomerById(customerId) != null) {
            return subscriptionDAO.getSubscriptionsByCustomerId(customerId);
        } else {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " does not exist.");
        }
    }
}
