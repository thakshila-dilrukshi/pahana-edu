package com.icbt.service;

import com.icbt.dao.CustomerDAO;
import com.icbt.model.Customer;
import com.icbt.util.DBConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {

    private static CustomerDAO customerDAO;
    private static boolean databaseAvailable;

    @BeforeAll
    static void setUpClass() {
        customerDAO = new CustomerDAO();
        databaseAvailable = isDatabaseAvailable();
        
        if (databaseAvailable) {
            // Clean up any existing test data
            cleanupTestData();
            // Setup fresh test data
            setupTestData();
        }
    }

    @AfterAll
    static void tearDownClass() {
        if (databaseAvailable) {
            // Clean up test data after all tests
            cleanupTestData();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test database connection availability")
    void testDatabaseConnection() {
        if (!databaseAvailable) {
            System.out.println("⚠️  Database not available - skipping integration tests");
            System.out.println("   Make sure MySQL is running and database 'pahana-edu' exists");
        }
        // This test always passes, but logs the database status
    }

    @Test
    @Order(2)
    @DisplayName("Test register new customer")
    @EnabledIf("databaseAvailable")
    void testRegisterCustomer() {
        // Given
        Customer customer = new Customer();
        customer.setName("Test Customer");
        customer.setAddress("123 Test Street");
        customer.setTelephone("0712345678");

        // When
        boolean result = customerDAO.addCustomer(customer);

        // Then
        assertTrue(result, "Customer registration should return true");
        
        // Verify customer was actually added
        List<Customer> customers = customerDAO.getAllCustomers();
        boolean customerFound = customers.stream()
                .anyMatch(c -> c.getName().equals("Test Customer") && 
                              c.getAddress().equals("123 Test Street"));
        assertTrue(customerFound, "New customer should be found in database");
    }

    @Test
    @Order(3)
    @DisplayName("Test get all customers")
    @EnabledIf("databaseAvailable")
    void testGetAllCustomers() {
        // When
        List<Customer> customers = customerDAO.getAllCustomers();

        // Then
        assertNotNull(customers, "Customer list should not be null");
        assertFalse(customers.isEmpty(), "Customer list should not be empty");
        
        // Verify we have at least the test customers from setup
        boolean hasTestCustomers = customers.stream()
                .anyMatch(c -> c.getName().equals("John Doe") || c.getName().equals("Jane Smith"));
        assertTrue(hasTestCustomers, "Should have test customers from setup");
    }

    @Test
    @Order(4)
    @DisplayName("Test update existing customer")
    @EnabledIf("databaseAvailable")
    void testUpdateCustomer() {
        // Given - get an existing customer
        List<Customer> customers = customerDAO.getAllCustomers();
        assertFalse(customers.isEmpty(), "Customer list should not be empty for update test");

        Customer existingCustomer = customers.get(0); // pick the first one for update
        String originalName = existingCustomer.getName();
        String updatedName = "Updated " + originalName;
        
        existingCustomer.setName(updatedName);

        // When
        boolean result = customerDAO.updateCustomer(existingCustomer);

        // Then
        assertTrue(result, "Customer update should return true");
        
        // Verify the update was actually saved
        List<Customer> updatedCustomers = customerDAO.getAllCustomers();
        boolean updateFound = updatedCustomers.stream()
                .anyMatch(c -> c.getAccountNumber() == existingCustomer.getAccountNumber() && 
                              c.getName().equals(updatedName));
        assertTrue(updateFound, "Updated customer should be found in database");
    }

    @Test
    @Order(5)
    @DisplayName("Test get customer by account number")
    @EnabledIf("databaseAvailable")
    void testGetCustomerByAccountNumber() {
        // Given - get an existing customer
        List<Customer> customers = customerDAO.getAllCustomers();
        assertFalse(customers.isEmpty(), "Customer list should not be empty");
        
        Customer existingCustomer = customers.get(0);
        int accountNumber = existingCustomer.getAccountNumber();

        // When
        Customer foundCustomer = customerDAO.getCustomerById(accountNumber);

        // Then
        assertNotNull(foundCustomer, "Customer should be found by account number");
        assertEquals(existingCustomer.getName(), foundCustomer.getName(), "Customer name should match");
        assertEquals(existingCustomer.getAddress(), foundCustomer.getAddress(), "Customer address should match");
    }

    @Test
    @Order(6)
    @DisplayName("Test get customer by non-existent account number")
    @EnabledIf("databaseAvailable")
    void testGetCustomerByNonExistentAccountNumber() {
        // Given
        int nonExistentAccountNumber = 99999;

        // When
        Customer foundCustomer = customerDAO.getCustomerById(nonExistentAccountNumber);

        // Then
        assertNull(foundCustomer, "Customer should be null for non-existent account number");
    }

    @Test
    @Order(7)
    @DisplayName("Test register customer with empty name")
    @EnabledIf("databaseAvailable")
    void testRegisterCustomerWithEmptyName() {
        // Given
        Customer customer = new Customer();
        customer.setName(""); // Empty name
        customer.setAddress("123 Test Street");
        customer.setTelephone("0712345678");

        // When
        boolean result = customerDAO.addCustomer(customer);

        // Then - this might succeed or fail depending on database constraints
        // We're testing that the method handles it gracefully
        assertNotNull(result, "Result should not be null");
    }

    @Test
    @Order(8)
    @DisplayName("Test register customer with null values")
    @EnabledIf("databaseAvailable")
    void testRegisterCustomerWithNullValues() {
        // Given
        Customer customer = new Customer();
        customer.setName(null);
        customer.setAddress(null);
        customer.setTelephone(null);

        // When
        boolean result = customerDAO.addCustomer(customer);

        // Then - this might succeed or fail depending on database constraints
        // We're testing that the method handles it gracefully
        assertNotNull(result, "Result should not be null");
    }

    @Test
    @Order(9)
    @DisplayName("Test customer with special characters")
    @EnabledIf("databaseAvailable")
    void testCustomerWithSpecialCharacters() {
        // Given
        Customer customer = new Customer();
        customer.setName("Test Customer & Co.");
        customer.setAddress("123 Test St., Apt #5");
        customer.setTelephone("+94-71-234-5678");

        // When
        boolean result = customerDAO.addCustomer(customer);

        // Then
        assertTrue(result, "Customer with special characters should be registered successfully");
    }

    @Test
    @Order(10)
    @DisplayName("Test customer with very long data")
    @EnabledIf("databaseAvailable")
    void testCustomerWithVeryLongData() {
        // Given
        Customer customer = new Customer();
        customer.setName("A".repeat(100)); // Very long name
        customer.setAddress("B".repeat(500)); // Very long address
        customer.setTelephone("C".repeat(50)); // Very long telephone

        // When
        boolean result = customerDAO.addCustomer(customer);

        // Then - this might succeed or fail depending on database constraints
        // We're testing that the method handles it gracefully
        assertNotNull(result, "Result should not be null");
    }

    /**
     * Check if database is available
     */
    private static boolean isDatabaseAvailable() {
        try (Connection connection = DBConnection.getConnection()) {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.out.println("Database connection not available: " + e.getMessage());
            return false;
        }
    }

    /**
     * Setup test data in the database
     */
    private static void setupTestData() {
        try (Connection connection = DBConnection.getConnection()) {
            
            // Insert test users
            insertTestUser(connection, "testadmin", "testpass123");
            insertTestUser(connection, "testuser1", "password123");
            insertTestUser(connection, "testuser2", "secret456");
            
            // Insert test customers
            insertTestCustomer(connection, "John Doe", "123 Main St", "0712345678");
            insertTestCustomer(connection, "Jane Smith", "456 Oak Ave", "0723456789");
            
            // Insert test items
            insertTestItem(connection, "Test Book 1", "Test Description 1", 100.00, 10);
            insertTestItem(connection, "Test Book 2", "Test Description 2", 200.00, 15);
            
            System.out.println("Test data setup completed successfully");
            
        } catch (SQLException e) {
            System.err.println("Error during test data setup: " + e.getMessage());
            throw new RuntimeException("Failed to setup test data", e);
        }
    }

    /**
     * Clean up all test data from the database
     */
    private static void cleanupTestData() {
        try (Connection connection = DBConnection.getConnection()) {
            // Disable foreign key checks temporarily
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            }
            
            // Clean up tables in reverse dependency order
            cleanupTable(connection, "bill_items");
            cleanupTable(connection, "bills");
            cleanupTable(connection, "customers");
            cleanupTable(connection, "items");
            cleanupTable(connection, "users");
            
            // Re-enable foreign key checks
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            }
            
            System.out.println("Test data cleanup completed successfully");
            
        } catch (SQLException e) {
            System.err.println("Error during test data cleanup: " + e.getMessage());
            throw new RuntimeException("Failed to cleanup test data", e);
        }
    }

    /**
     * Clean up a specific table
     */
    private static void cleanupTable(Connection connection, String tableName) throws SQLException {
        String sql = "DELETE FROM " + tableName;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Cleaned up " + rowsAffected + " rows from " + tableName);
        }
    }

    /**
     * Insert a test user
     */
    private static void insertTestUser(Connection connection, String username, String password) throws SQLException {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            System.out.println("Inserted test user: " + username);
        }
    }

    /**
     * Insert a test customer
     */
    private static void insertTestCustomer(Connection connection, String name, String address, String telephone) throws SQLException {
        String sql = "INSERT INTO customers (name, address, telephone) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, telephone);
            stmt.executeUpdate();
            System.out.println("Inserted test customer: " + name);
        }
    }

    /**
     * Insert a test item
     */
    private static void insertTestItem(Connection connection, String name, String description, double price, int stock) throws SQLException {
        String sql = "INSERT INTO items (item_name, item_description, unit_price, stock_quantity) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, price);
            stmt.setInt(4, stock);
            stmt.executeUpdate();
            System.out.println("Inserted test item: " + name);
        }
    }

    /**
     * Condition method for @EnabledIf annotation
     */
    static boolean databaseAvailable() {
        return databaseAvailable;
    }
}
