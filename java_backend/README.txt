**ApplicationUI2** is the main entry point for the console-based E-commerce platform. It provides a simple command-line interface (CLI) for admins and customers to interact with the e-commerce system. Users can perform various tasks, such as managing products, subscriptions, and customer accounts.

**Features**
  **Admin Features**
      -Add Product: Allows the admin to add new products to the catalog.
      -Update Product: Enables the admin to modify existing product details.
      -Delete Product: Permits the admin to remove a product from the catalog.
      -Add Subscription: Allows the admin to create new subscriptions for products.
      -Update Subscription: Enables the admin to update the details of existing subscriptions.
      -Deactivate Subscription: Allows the admin to deactivate a subscription.
      -View All Products: Displays all products available in the system.
      -View All Subscriptions: Lists all the subscriptions currently in the system.
      -View Pending Orders: Shows all orders that are pending.
      -Update Order Status: Allows the admin to update the status of an order.
      -Update Delivery Status: Enables the admin to update the delivery status of an order.
  **Customer Features**
      -View Products: Displays all available products in the system.
      -Subscribe to Product: Allows customers to subscribe to a product for regular deliveries.
      -View My Subscriptions: Lists all subscriptions for the logged-in customer.
      -Top Up Wallet: Lets customers add funds to their wallet.
      -Deduct From Wallet: Allows customers to deduct funds from their wallet.
      -Update Profile: Enables customers to update their profile information, including username, email, and wallet balance.
      -Delete Account: Permits customers to delete their account from the system.
      -Log Out: Logs the customer out of the system.
      -View Wallet Balance: Displays the current wallet balance of the logged-in customer.

**Getting Started**
    Prerequisites
      -Java Development Kit (JDK) 8 or higher
      -JDBC-compatible database (e.g., MySQL, PostgreSQL)
      -The DatabaseConnection class should be correctly configured to establish a connection with your database.

**Running the Application**
  1. Compile the Code: Ensure that all the necessary classes and dependencies are compiled.
        javac com/hsbc/ecommerce/presentation/ApplicationUI2.java

  2. Run the Application: Execute the ApplicationUI2 class.
        java com.hsbc.ecommerce.presentation.ApplicationUI2
  
  3. Follow the On-Screen Prompts: Follow the on-screen prompts to navigate the various features once the application starts.

  **Admin Login**
      -To log in as an admin, select the "Login as Admin" option and enter your Admin ID.

  **Customer Login & Registration**
      -Existing customers can log in by selecting the "Login as Customer" option and providing their Customer ID.
      -The "Register as New Customer" option allows new customers to register.

**Class Structure**

ApplicationUI2 Class
  Attributes:
      -Scanner scanner: Used for reading user inputs.
      -AdminService adminService: Handles admin-related operations.
      -ProductService productService: Manages product-related tasks.
      -CustomerService customerService: Manages customer-related operations.
      -OrderService orderService: Manages order-related tasks.
  Methods:
      -main(String[] args): The main method that starts the application.
      -loginAsAdmin(): Handles admin login and navigation.
      -loginAsCustomer(): Manages customer login and navigation.
      -registerNewCustomer(): Facilitates new customer registration.
      -adminMenu(int adminId): Displays and handles admin options.
      -customerMenu(int customerId): Displays and handles customer options.
      -addProduct(), updateProduct(), deleteProduct(): Admin product management functions.
      -addSubscription(), updateSubscription(), deactivateSubscription(): Admin subscription management functions.
      -viewAllProducts(), viewAllSubscriptions(), viewPendingOrders(): Viewing functions for products, subscriptions, and orders.
      -updateOrderStatus(), updateDeliveryStatus(): Order and delivery management functions.
      -subscribeToProduct(int customerId), viewCustomerSubscriptions(int customerId): Customer-specific subscription management.
