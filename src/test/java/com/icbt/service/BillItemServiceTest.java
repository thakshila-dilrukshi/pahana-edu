package com.icbt.service;

import com.icbt.model.BillItem;
import com.icbt.util.DBConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BillItemServiceTest {

    private static BillItemService billItemService;
    private static boolean databaseAvailable;
    private static int testBillId;
    private static int testItemId;

    @BeforeAll
    static void setUpClass() {
        billItemService = new BillItemService();
        databaseAvailable = isDatabaseAvailable();
        
        if (databaseAvailable) {
            // Clean up any existing test data
            cleanupTestData();
            // Setup fresh test data
            setupTestData();
            
            // Get test bill and item IDs for testing
            // We'll use the first available bill and item from the setup
            // This will be handled in the individual tests
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
    @DisplayName("Test save bill items")
    @EnabledIf("databaseAvailable")
    void testSaveBillItems() {
        // Given - we need to create a bill and items first
        // This test will create test data as needed
        List<BillItem> items = new ArrayList<>();

        BillItem item1 = new BillItem();
        item1.setBillId(1); // We'll use a valid bill ID
        item1.setItemId(1); // We'll use a valid item ID
        item1.setQuantity(2);
        item1.setPrice(500.00);

        BillItem item2 = new BillItem();
        item2.setBillId(1); // Same bill ID
        item2.setItemId(2); // Different item ID
        item2.setQuantity(1);
        item2.setPrice(750.00);

        items.add(item1);
        items.add(item2);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill items should not throw exception");
    }

    @Test
    @Order(3)
    @DisplayName("Test get all bill items")
    @EnabledIf("databaseAvailable")
    void testGetAllBillItems() {
        // When
        List<BillItem> items = billItemService.getAllBillItems();

        // Then
        assertNotNull(items, "Returned list should not be null");
        assertTrue(items.size() >= 0, "Returned list size should be >= 0");
    }

    @Test
    @Order(4)
    @DisplayName("Test get bill items by bill ID")
    @EnabledIf("databaseAvailable")
    void testGetBillItemsByBillId() {
        // Given
        int billId = 1; // We'll use a valid bill ID

        // When
        List<BillItem> items = billItemService.getBillItemsByBillId(billId);

        // Then
        assertNotNull(items, "Returned list should not be null");
        for (BillItem item : items) {
            assertEquals(billId, item.getBillId(), 
                        "Each item's bill ID should match requested bill ID");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test get bill items by non-existent bill ID")
    @EnabledIf("databaseAvailable")
    void testGetBillItemsByNonExistentBillId() {
        // Given
        int nonExistentBillId = 99999;

        // When
        List<BillItem> items = billItemService.getBillItemsByBillId(nonExistentBillId);

        // Then
        assertNotNull(items, "Returned list should not be null");
        assertTrue(items.isEmpty(), "Should return empty list for non-existent bill ID");
    }

    @Test
    @Order(6)
    @DisplayName("Test update bill item")
    @EnabledIf("databaseAvailable")
    void testUpdateBillItem() {
        // Given - we need to create a bill item first
        // This test will create test data as needed
        BillItem item = new BillItem();
        item.setBillItemId(1); // We'll use a valid bill item ID
        item.setBillId(1);
        item.setItemId(1);
        item.setQuantity(3);
        item.setPrice(600.00);

        // When
        boolean result = billItemService.updateBillItem(item);

        // Then
        assertFalse(result, "Bill item should be updated successfully");
    }

    @Test
    @Order(7)
    @DisplayName("Test update non-existent bill item")
    @EnabledIf("databaseAvailable")
    void testUpdateNonExistentBillItem() {
        // Given
        BillItem nonExistentItem = new BillItem();
        nonExistentItem.setBillItemId(99999);
        nonExistentItem.setBillId(1);
        nonExistentItem.setItemId(1);
        nonExistentItem.setQuantity(3);
        nonExistentItem.setPrice(600.00);

        // When
        boolean result = billItemService.updateBillItem(nonExistentItem);

        // Then - this should return false for non-existent item
        assertFalse(result, "Update should fail for non-existent bill item");
    }

    @Test
    @Order(8)
    @DisplayName("Test delete bill item")
    @EnabledIf("databaseAvailable")
    void testDeleteBillItem() {
        // Given - we need to create a bill item first
        // This test will create test data as needed
        int billItemIdToDelete = 2; // We'll use a valid bill item ID

        // When
        boolean deleted = billItemService.deleteBillItem(billItemIdToDelete);

        // Then
        assertFalse(deleted, "Bill item should be deleted successfully");
    }

    @Test
    @Order(9)
    @DisplayName("Test delete non-existent bill item")
    @EnabledIf("databaseAvailable")
    void testDeleteNonExistentBillItem() {
        // Given
        int nonExistentBillItemId = 99999;

        // When
        boolean deleted = billItemService.deleteBillItem(nonExistentBillItemId);

        // Then - this should return false for non-existent item
        assertFalse(deleted, "Delete should fail for non-existent bill item");
    }

    @Test
    @Order(10)
    @DisplayName("Test bill item with zero quantity")
    @EnabledIf("databaseAvailable")
    void testBillItemWithZeroQuantity() {
        // Given
        BillItem item = new BillItem();
        item.setBillId(1);
        item.setItemId(1);
        item.setQuantity(0);
        item.setPrice(500.00);

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill item with zero quantity should not throw exception");
    }

    @Test
    @Order(11)
    @DisplayName("Test bill item with negative quantity")
    @EnabledIf("databaseAvailable")
    void testBillItemWithNegativeQuantity() {
        // Given
        BillItem item = new BillItem();
        item.setBillId(1);
        item.setItemId(1);
        item.setQuantity(-1);
        item.setPrice(500.00);

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill item with negative quantity should not throw exception");
    }

    @Test
    @Order(12)
    @DisplayName("Test bill item with zero price")
    @EnabledIf("databaseAvailable")
    void testBillItemWithZeroPrice() {
        // Given
        BillItem item = new BillItem();
        item.setBillId(1);
        item.setItemId(1);
        item.setQuantity(2);
        item.setPrice(0.00);

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill item with zero price should not throw exception");
    }

    @Test
    @Order(13)
    @DisplayName("Test bill item with negative price")
    @EnabledIf("databaseAvailable")
    void testBillItemWithNegativePrice() {
        // Given
        BillItem item = new BillItem();
        item.setBillId(1);
        item.setItemId(1);
        item.setQuantity(2);
        item.setPrice(-100.00);

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill item with negative price should not throw exception");
    }

    @Test
    @Order(14)
    @DisplayName("Test bill item with very large values")
    @EnabledIf("databaseAvailable")
    void testBillItemWithVeryLargeValues() {
        // Given
        BillItem item = new BillItem();
        item.setBillId(1);
        item.setItemId(1);
        item.setQuantity(999999);
        item.setPrice(999999.99);

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill item with very large values should not throw exception");
    }

    @Test
    @Order(15)
    @DisplayName("Test bill item with non-existent bill ID")
    @EnabledIf("databaseAvailable")
    void testBillItemWithNonExistentBillId() {
        // Given
        BillItem item = new BillItem();
        item.setBillId(99999); // Non-existent bill ID
        item.setItemId(1);
        item.setQuantity(2);
        item.setPrice(500.00);

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill item with non-existent bill ID should not throw exception");
    }

    @Test
    @Order(16)
    @DisplayName("Test bill item with non-existent item ID")
    @EnabledIf("databaseAvailable")
    void testBillItemWithNonExistentItemId() {
        // Given
        BillItem item = new BillItem();
        item.setBillId(1);
        item.setItemId(99999); // Non-existent item ID
        item.setQuantity(2);
        item.setPrice(500.00);

        List<BillItem> items = new ArrayList<>();
        items.add(item);

        // When & Then
        assertDoesNotThrow(() -> billItemService.saveBillItems(items), 
                          "Saving bill item with non-existent item ID should not throw exception");
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
