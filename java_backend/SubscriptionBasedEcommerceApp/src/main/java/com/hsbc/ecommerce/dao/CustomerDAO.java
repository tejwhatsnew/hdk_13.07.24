package com.hsbc.ecommerce.dao;

import com.hsbc.ecommerce.dao.exceptions.CustomerNotFoundException;
import com.hsbc.ecommerce.model.Customer;
import com.hsbc.ecommerce.model.Subscription;

import java.util.List;

public interface CustomerDAO {
    void addCustomer(Customer customer);
    Customer getCustomerById (int customerId) throws CustomerNotFoundException;
    List<Customer> getAllCustomers();
    void updateCustomer(Customer customer) throws CustomerNotFoundException;
    void deleteCustomer(int customerId) throws CustomerNotFoundException;
    void topUpWallet(int customerId, double amount) throws CustomerNotFoundException;
    void deductFromWallet(int customerId, double amount) throws CustomerNotFoundException;
    public List<Subscription> getSubscriptionsByCustomerId(int customerId) throws CustomerNotFoundException;
}
