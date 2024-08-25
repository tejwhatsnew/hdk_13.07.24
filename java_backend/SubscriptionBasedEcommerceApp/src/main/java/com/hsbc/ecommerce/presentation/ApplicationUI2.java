package com.hsbc.ecommerce.presentation;

import com.hsbc.ecommerce.dao.*;
import com.hsbc.ecommerce.dao.exceptions.CustomerNotFoundException;
import com.hsbc.ecommerce.model.*;
import com.hsbc.ecommerce.service.*;
import com.hsbc.ecommerce.util.DatabaseConnection;

import java.util.List;
import java.util.Scanner;

public class ApplicationUI2 {

    private static final Scanner scanner = new Scanner(System.in);
    private static AdminService adminService = new AdminServiceImpl(new AdminDaoImpl());
    private static ProductService productService = new ProductServiceImpl(new ProductDAOImpl(DatabaseConnection.getConnection()));
    private static CustomerService customerService = new CustomerServiceImpl(new CustomerDAOImpl(), new SubscriptionDAOImpl());
    private static OrderService orderService = new OrderServiceImpl(new OrderDAOImpl(DatabaseConnection.getConnection()));

    public static void main(String[] args) {
        while (true) {
            System.out.println("Welcome to the E-commerce Platform!");
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Customer");
            System.out.println("3. Register as New Customer");
            System.out.println("4. Exit");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    loginAsAdmin();
                    break;
                case 2:
                    try {
                        loginAsCustomer();
                    }
                    catch (CustomerNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    registerNewCustomer();
                    break;
                case 4:
                    System.out.println("Thank you for using the E-commerce Platform!");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void loginAsAdmin() {
        System.out.print("Enter Admin ID: ");
        int adminId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Admin login successful.");
        adminMenu(adminId);
    }

    private static void loginAsCustomer() throws CustomerNotFoundException {
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine();

        if (customerService.isValidCustomer(customerId)) {
            System.out.println("Customer login successful.");
            customerMenu(customerId);
        } else {
            System.out.println("Invalid Customer ID.");
        }
    }

    private static void registerNewCustomer() {
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your wallet balance: ");
        Double balance = scanner.nextDouble();

        Customer newCustomer = new Customer(101, name, email, balance, List.of());
        customerService.addCustomer(newCustomer);

        System.out.println("Registration successful. Your Customer ID is: " + newCustomer.getId());
    }

    private static void adminMenu(int adminId) {
        while (true) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Add Subscription");
            System.out.println("5. Update Subscription");
            System.out.println("6. Deactivate Subscription");
            System.out.println("7. View All Products");
            System.out.println("8. View All Subscriptions");
            System.out.println("9. View Pending Orders");
            System.out.println("10. Update Order Status");
            System.out.println("11. Update Delivery Status");
            System.out.println("12. Log Out");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    addSubscription();
                    break;
                case 5:
                    updateSubscription();
                    break;
                case 6:
                    deactivateSubscription();
                    break;
                case 7:
                    viewAllProducts();
                    break;
                case 8:
                    viewAllSubscriptions();
                    break;
                case 9:
                    viewPendingOrders();
                    break;
                case 10:
                    updateOrderStatus();
                    break;
                case 11:
                    updateDeliveryStatus();
                    break;
                case 12:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void customerMenu(int customerId) throws CustomerNotFoundException {
        while (true) {
            System.out.println("Customer Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Subscribe to Product");
            System.out.println("3. View My Subscriptions");
            System.out.println("5. Log Out");
            System.out.print("Please select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewAllProducts();
                    break;
                case 2:
                    subscribeToProduct(customerId);
                    break;
                case 3:
                    viewCustomerSubscriptions(customerId);
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product category: ('Fruits', 'Vegetables', 'Snacks', 'Meals', 'Others') ");
        String category = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter product image URL: ");
        String imageUrl = scanner.nextLine();
        System.out.print("Is the product active? (true/false): ");
        boolean isActive = scanner.nextBoolean();
        System.out.print("Available Stock?:");
        int stock = scanner.nextInt();

        Product product = new Product(name, description, category, price, imageUrl, isActive, stock);
        productService.addProduct(product);

        System.out.println("Product added successfully.");
    }

    private static void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter updated product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter updated product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter updated product category: ");
        String category = scanner.nextLine();
        System.out.print("Enter updated product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter updated product image URL: ");
        String imageUrl = scanner.nextLine();
        System.out.print("Is the product active? (true/false): ");
        boolean isActive = scanner.nextBoolean();
        System.out.print("Available Stock?:");
        int stock = scanner.nextInt();

        Product product = new Product(productId, name, description, category, price, imageUrl, isActive, stock);
        adminService.updateProduct(product);

        System.out.println("Product updated successfully.");
    }

    private static void deleteProduct() {
        System.out.print("Enter product ID to delete: ");
        int productId = scanner.nextInt();
        adminService.deleteProduct(productId);
        System.out.println("Product deleted successfully.");
    }

    private static void addSubscription() {
        System.out.print("Enter product ID for subscription: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter subscription frequency (e.g., weekly, monthly): ");
        String frequency = scanner.nextLine();

        System.out.print("Enter custom start date (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();
        java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);

        System.out.print("Enter custom end date (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();
        java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

        System.out.print("Subscription price is Rs.1000!");

        Subscription subscription = new Subscription(productId, 101, frequency, startDate, endDate, true);
        adminService.addSubscription(subscription);

        System.out.println("Subscription added successfully.");
    }

    private static void updateSubscription() {
        System.out.print("Enter subscription ID to update: ");
        int subscriptionId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter updated subscription frequency: ");
        String frequency = scanner.nextLine();
        System.out.print("Enter updated start date (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();
        java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);
        System.out.print("Enter updated end date (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();
        java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

        Subscription subscription = new Subscription(subscriptionId, 101, frequency, startDate, endDate, true);
        //adminService.updateSubscription(subscription);

        System.out.println("Subscription updated successfully.");
    }

    private static void deactivateSubscription() {
        System.out.print("Enter subscription ID to deactivate: ");
        int subscriptionId = scanner.nextInt();
        adminService.deactivateSubscription(subscriptionId);
        System.out.println("Subscription deactivated successfully.");
    }

    private static void viewAllProducts() {
        List<Product> products = adminService.getAllProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static void viewAllSubscriptions() {
        List<Subscription> subscriptions = adminService.getAllSubscriptions();
        for (Subscription subscription : subscriptions) {
            System.out.println(subscription);
        }
    }

    private static void viewPendingOrders() {
        List<Order> pendingOrders = adminService.getPendingOrders();
        for (Order order : pendingOrders) {
            System.out.println(order);
        }
    }

    private static void updateOrderStatus() {
        System.out.print("Enter order ID to update status: ");
        int orderId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        adminService.updateOrderStatus(orderId, status);
        System.out.println("Order status updated successfully.");
    }

    private static void updateDeliveryStatus() {
        System.out.print("Enter delivery ID to update status: ");
        int deliveryId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        adminService.updateDeliveryStatus(deliveryId, status);
        System.out.println("Delivery status updated successfully.");
    }

    private static void subscribeToProduct(int customerId) {
        System.out.print("Enter Product ID to subscribe: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Subscription type (e.g., Weekly, Monthly): ");
        String type = scanner.nextLine();


        System.out.print("Enter custom start date (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();
        java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);

        System.out.print("Enter custom end date (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();
        java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

        System.out.print("Enter subscription price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();

        Subscription subscription = new Subscription(productId, 101, type, startDate, endDate, true);
        adminService.addSubscription(subscription);

        System.out.println("Subscription added successfully.");

        System.out.println("Subscription successful.");
    }

    private static void viewCustomerSubscriptions(int customerId) throws CustomerNotFoundException {
        List<Subscription> subscriptions = customerService.getSubscriptionsByCustomerId(customerId);
        for (Subscription subscription : subscriptions) {
            System.out.println(subscription);
        }
    }


}
