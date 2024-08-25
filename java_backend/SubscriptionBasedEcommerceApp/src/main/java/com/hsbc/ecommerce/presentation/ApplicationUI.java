package com.hsbc.ecommerce.presentation;

import com.hsbc.ecommerce.dao.*;
import com.hsbc.ecommerce.model.*;
import com.hsbc.ecommerce.service.*;
import com.hsbc.ecommerce.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationUI {

    private static final Scanner scanner = new Scanner(System.in);
    private static AdminService adminService = new AdminServiceImpl(new AdminDaoImpl());
    private static ProductService productService = new ProductServiceImpl(new ProductDAOImpl(DatabaseConnection.getConnection()));
    private static CustomerService customerService = new CustomerServiceImpl(new CustomerDAOImpl(), new SubscriptionDAOImpl());
    //private static subscriptionService subscriptionService = new SubscriptionServiceImpl();


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
                    loginAsCustomer();
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
        scanner.nextLine(); // Consume newline

        // For simplicity, we'll assume any admin ID is valid
        System.out.println("Admin login successful.");
        adminMenu(adminId);
    }

    private static void loginAsCustomer() {
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

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
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

   //register new customer..

        System.out.println("Registration successful. Your Customer ID is: " + 101);
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
            System.out.println("10. View Pending Deliveries");
            System.out.println("11. Update Order Status");
            System.out.println("12. Update Delivery Status");
            System.out.println("13. Log Out");
            System.out.print("Please select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
                    viewPendingDeliveries();
                    break;
                case 11:
                    updateOrderStatus();
                    break;
                case 12:
                    updateDeliveryStatus();
                    break;
                case 13:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void customerMenu(int customerId) {
        while (true) {
            System.out.println("Customer Menu:");
            System.out.println("1. View Products");
            System.out.println("2. Subscribe to Product");
            System.out.println("3. View My Subscriptions");
            System.out.println("4. Log Out");
            System.out.print("Please select an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

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
        System.out.print("Enter product category: ");
        String category = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter product image URL: ");
        String imageUrl = scanner.nextLine();
        System.out.print("Is the product active? (true/false): ");
        boolean isActive = scanner.nextBoolean();
        System.out.println("Available Stock?:");
        int stock= scanner.nextInt();

        Product product = new Product(name, description, category, price, imageUrl, isActive, stock);
        productService.addProduct(product);

        System.out.println("Product added successfully.");
    }

    private static void updateProduct() {
        System.out.print("Enter product ID to update: ");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter updated product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter updated product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter updated product category: ");
        String category = scanner.nextLine();
        System.out.print("Enter updated product price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter updated product image URL: ");
        String imageUrl = scanner.nextLine();
        System.out.print("Is the product active? (true/false): ");
        boolean isActive = scanner.nextBoolean();
        System.out.println("Available Stock?:");
        int stock= scanner.nextInt();

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
        scanner.nextLine(); // Consume newline

        System.out.print("Enter subscription frequency (e.g., weekly, monthly): ");
        String frequency = scanner.nextLine();

        System.out.print("Enter custom start date (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();
        java.sql.Date startDate = java.sql.Date.valueOf(startDateStr);

        System.out.print("Enter custom end date (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();
        java.sql.Date endDate = java.sql.Date.valueOf(endDateStr);

        System.out.print("Enter subscription price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Subscription subscription = new Subscription(productId, 101, frequency, startDate, endDate, true);
        adminService.addSubscription(subscription);

        System.out.println("Subscription added successfully.");
    }

    private static void updateSubscription() {
        // Similar to updateProduct, with subscription-specific fields
    }

    private static void deactivateSubscription() {
        System.out.print("Enter subscription ID to deactivate: ");
        int subscriptionId = scanner.nextInt();
        adminService.deactivateSubscription(subscriptionId);
        System.out.println("Subscription deactivated successfully.");
    }

    private static void viewAllProducts() {
        System.out.println("Product List:");
        for (Product product : adminService.getAllProducts()) {
            System.out.println(product);
        }
    }

    private static void viewAllSubscriptions() {
        System.out.println("Subscription List:");
        for (Subscription subscription : adminService.getAllSubscriptions()) {
            System.out.println(subscription);
        }
    }

    private static void viewPendingOrders() {
        System.out.println("Pending Orders:");
        for (Order order : adminService.getPendingOrders()) {
            System.out.println(order);
        }
    }

    private static void viewPendingDeliveries() {
        System.out.println("Pending Deliveries:");
        for (DeliverySchedule schedule : adminService.getPendingDeliveries()) {
            System.out.println(schedule);
        }
    }

    private static void updateOrderStatus() {
        System.out.print("Enter order ID to update: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();

        adminService.updateOrderStatus(orderId, status);
        System.out.println("Order status updated successfully.");
    }

    private static void updateDeliveryStatus() {
        System.out.print("Enter delivery schedule ID to update: ");
        int scheduleId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();

        adminService.updateDeliveryStatus(scheduleId, status);
        System.out.println("Delivery status updated successfully.");
    }

    private static void subscribeToProduct(int customerId) {
        // Functionality to allow customers to subscribe to products
    }

    private static void viewCustomerSubscriptions(int customerId) {
        // Functionality to allow customers to view their subscriptions
    }
}
