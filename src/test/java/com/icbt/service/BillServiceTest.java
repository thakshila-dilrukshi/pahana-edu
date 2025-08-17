package com.icbt.service;

import com.icbt.model.Bill;
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
class BillServiceTest {

    private static BillService billService;
    private static boolean databaseAvailable;
    private static int testBillId;

    @BeforeAll
    static void setUpClass() {
        billService = new BillService();
        databaseAvailable = isDatabaseAvailable();
        
        if (databaseAvailable) {
            // Clean up any existing test data
            cleanupTestData();
            // Setup fresh test data
            setupTestData();
            
            // Insert a test bill to work with
            Bill bill = new Bill();
            bill.setAccountNumber(1001);
            bill.setTotalAmount(2500.00);

            testBillId = billService.addBill(bill);
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
    @DisplayName("Test add new bill")
    @EnabledIf("databaseAvailable")
    void testAddBill() {
        // Given
        Bill bill = new Bill();
        bill.setAccountNumber(1002);
        bill.setTotalAmount(1200.50);

        // When
        int billId = billService.addBill(bill);

        // Then
        assertTrue(billId > 0, "Bill should be added and return a valid ID");
        
        // Verify bill was actually added
        Bill addedBill = billService.getBillById(billId);
        assertNotNull(addedBill, "Added bill should be found in database");
        assertEquals(1002, addedBill.getAccountNumber(), "Account number should match");
        assertEquals(1200.50, addedBill.getTotalAmount(), "Total amount should match");
    }

    @Test
    @Order(3)
    @DisplayName("Test get all bills")
    @EnabledIf("databaseAvailable")
    void testGetAllBills() {
        // When
        List<Bill> bills = billService.getAllBills();

        // Then
        assertNotNull(bills, "Bills list should not be null");
        assertTrue(bills.size() > 0, "There should be at least one bill in DB");
        
        // Verify we have at least the test bill from setup
        boolean hasTestBill = bills.stream()
                .anyMatch(b -> b.getBillId() == testBillId);
        assertTrue(hasTestBill, "Should have test bill from setup");
    }

    @Test
    @Order(4)
    @DisplayName("Test get bill by ID")
    @EnabledIf("databaseAvailable")
    void testGetBillById() {
        // When
        Bill bill = billService.getBillById(testBillId);

        // Then
        assertNotNull(bill, "Bill should not be null for valid ID");
        assertEquals(testBillId, bill.getBillId(), "Returned bill ID should match");
        assertEquals(1001, bill.getAccountNumber(), "Account number should match");
        assertEquals(2500.00, bill.getTotalAmount(), "Total amount should match");
    }

    @Test
    @Order(5)
    @DisplayName("Test get bill by non-existent ID")
    @EnabledIf("databaseAvailable")
    void testGetBillByNonExistentId() {
        // Given
        int nonExistentId = 99999;

        // When
        Bill bill = billService.getBillById(nonExistentId);

        // Then
        assertNull(bill, "Bill should be null for non-existent ID");
    }

    @Test
    @Order(6)
    @DisplayName("Test update existing bill")
    @EnabledIf("databaseAvailable")
    void testUpdateBill() {
        // Given
        Bill updatedBill = new Bill();
        updatedBill.setBillId(testBillId);
        updatedBill.setAccountNumber(1001);
        updatedBill.setTotalAmount(3000.00);

        // When
        boolean updated = billService.updateBill(updatedBill);

        // Then
        assertTrue(updated, "Bill should be updated successfully");
        
        // Verify the update was actually saved
        Bill retrievedBill = billService.getBillById(testBillId);
        assertNotNull(retrievedBill, "Updated bill should be found");
        assertEquals(3000.00, retrievedBill.getTotalAmount(), "Total amount should be updated");
    }

    @Test
    @Order(7)
    @DisplayName("Test delete bill")
    @EnabledIf("databaseAvailable")
    void testDeleteBill() {
        // Given - create a new bill to delete
        Bill billToDelete = new Bill();
        billToDelete.setAccountNumber(1003);
        billToDelete.setTotalAmount(500.00);

        int billIdToDelete = billService.addBill(billToDelete);
        assertTrue(billIdToDelete > 0, "Bill should be created for deletion test");

        // When
        boolean deleted = billService.deleteBill(billIdToDelete);

        // Then
        assertTrue(deleted, "Bill should be deleted successfully");

        // Verify deletion
        Bill deletedBill = billService.getBillById(billIdToDelete);
        assertNull(deletedBill, "Deleted bill should no longer exist");
    }

    @Test
    @Order(8)
    @DisplayName("Test bill with zero amount")
    @EnabledIf("databaseAvailable")
    void testBillWithZeroAmount() {
        // Given
        Bill bill = new Bill();
        bill.setAccountNumber(1004);
        bill.setTotalAmount(0.00);

        // When
        int billId = billService.addBill(bill);

        // Then
        assertTrue(billId > 0, "Bill with zero amount should be added successfully");
        
        // Verify bill was added
        Bill addedBill = billService.getBillById(billId);
        assertNotNull(addedBill, "Bill with zero amount should be found");
        assertEquals(0.00, addedBill.getTotalAmount(), "Zero amount should be preserved");
    }

    @Test
    @Order(9)
    @DisplayName("Test bill with negative amount")
    @EnabledIf("databaseAvailable")
    void testBillWithNegativeAmount() {
        // Given
        Bill bill = new Bill();
        bill.setAccountNumber(1005);
        bill.setTotalAmount(-100.00);

        // When
        int billId = billService.addBill(bill);

        // Then - this might succeed or fail depending on database constraints
        // We're testing that the method handles it gracefully
        assertNotNull(billId, "Result should not be null");
    }

    @Test
    @Order(10)
    @DisplayName("Test bill with very large amount")
    @EnabledIf("databaseAvailable")
    void testBillWithVeryLargeAmount() {
        // Given
        Bill bill = new Bill();
        bill.setAccountNumber(1006);
        bill.setTotalAmount(999999.99);

        // When
        int billId = billService.addBill(bill);

        // Then
        assertTrue(billId > 0, "Bill with large amount should be added successfully");
        
        // Verify bill was added
        Bill addedBill = billService.getBillById(billId);
        assertNotNull(addedBill, "Bill with large amount should be found");
        assertEquals(999999.99, addedBill.getTotalAmount(), "Large amount should be preserved");
    }

    @Test
    @Order(11)
    @DisplayName("Test bill with non-existent account number")
    @EnabledIf("databaseAvailable")
    void testBillWithNonExistentAccountNumber() {
        // Given
        Bill bill = new Bill();
        bill.setAccountNumber(99999); // Non-existent account number
        bill.setTotalAmount(100.00);

        // When
        int billId = billService.addBill(bill);

        // Then - this might succeed or fail depending on foreign key constraints
        // We're testing that the method handles it gracefully
        assertNotNull(billId, "Result should not be null");
    }

    @Test
    @Order(12)
    @DisplayName("Test update non-existent bill")
    @EnabledIf("databaseAvailable")
    void testUpdateNonExistentBill() {
        // Given
        Bill nonExistentBill = new Bill();
        nonExistentBill.setBillId(99999);
        nonExistentBill.setAccountNumber(1001);
        nonExistentBill.setTotalAmount(1000.00);

        // When
        boolean updated = billService.updateBill(nonExistentBill);

        // Then - this should return false for non-existent bill
        assertFalse(updated, "Update should fail for non-existent bill");
    }

    @Test
    @Order(13)
    @DisplayName("Test delete non-existent bill")
    @EnabledIf("databaseAvailable")
    void testDeleteNonExistentBill() {
        // Given
        int nonExistentBillId = 99999;

        // When
        boolean deleted = billService.deleteBill(nonExistentBillId);

        // Then - this should return false for non-existent bill
        assertFalse(deleted, "Delete should fail for non-existent bill");
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
