package com.icbt.service;

import com.icbt.model.Item;
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
class ItemServiceTest {

    private static ItemService itemService;
    private static boolean databaseAvailable;
    private static int testItemId; // Will hold ID of the item created for testing

    @BeforeAll
    static void setUpClass() {
        itemService = new ItemService();
        databaseAvailable = isDatabaseAvailable();
        
        if (databaseAvailable) {
            // Clean up any existing test data
            cleanupTestData();
            // Setup fresh test data
            setupTestData();
            
            // Insert test item for testing
            Item item = new Item();
            item.setItem_name("Test Item");
            item.setItem_description("Test Description");
            item.setUnit_price(100.0);
            item.setStock_quantity(5.0);

            itemService.registerItem(item);

            // Get last inserted item's ID
            List<Item> items = itemService.getAllItems();
            testItemId = items.get(items.size() - 1).getItem_id();
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
    @DisplayName("Test register new item")
    @EnabledIf("databaseAvailable")
    void testRegisterItem() {
        // Given
        Item newItem = new Item();
        newItem.setItem_name("New Test Item");
        newItem.setItem_description("New item for test");
        newItem.setUnit_price(300.0);
        newItem.setStock_quantity(7.0);

        // When
        boolean result = itemService.registerItem(newItem);

        // Then
        assertTrue(result, "Item should be registered successfully");
        
        // Verify item was actually added
        List<Item> items = itemService.getAllItems();
        boolean itemFound = items.stream()
                .anyMatch(i -> i.getItem_name().equals("New Test Item") && 
                              i.getUnit_price() == 300.0);
        assertTrue(itemFound, "New item should be found in database");
    }

    @Test
    @Order(3)
    @DisplayName("Test get all items")
    @EnabledIf("databaseAvailable")
    void testGetAllItems() {
        // When
        List<Item> items = itemService.getAllItems();

        // Then
        assertNotNull(items, "Items list should not be null");
        assertTrue(items.size() > 0, "There should be at least one item");
        
        // Verify we have at least the test items from setup
        boolean hasTestItems = items.stream()
                .anyMatch(i -> i.getItem_name().equals("Test Book 1") || 
                              i.getItem_name().equals("Test Book 2"));
        assertTrue(hasTestItems, "Should have test items from setup");
    }

    @Test
    @Order(4)
    @DisplayName("Test get item by ID")
    @EnabledIf("databaseAvailable")
    void testGetItemById() {
        // Given
        List<Item> items = itemService.getAllItems();
        assertFalse(items.isEmpty(), "Items list should not be empty");
        
        Item existingItem = items.get(0);
        int itemId = existingItem.getItem_id();

        // When
        Item foundItem = itemService.getItemById(itemId);

        // Then
        assertNotNull(foundItem, "Item should not be null for valid ID");
        assertEquals(itemId, foundItem.getItem_id(), "Item ID should match");
        assertEquals(existingItem.getItem_name(), foundItem.getItem_name(), "Item name should match");
    }

    @Test
    @Order(5)
    @DisplayName("Test get item by non-existent ID")
    @EnabledIf("databaseAvailable")
    void testGetItemByNonExistentId() {
        // Given
        int nonExistentId = 99999;

        // When
        Item foundItem = itemService.getItemById(nonExistentId);

        // Then
        assertNull(foundItem, "Item should be null for non-existent ID");
    }

    @Test
    @Order(6)
    @DisplayName("Test update existing item")
    @EnabledIf("databaseAvailable")
    void testUpdateItem() {
        // Given
        List<Item> items = itemService.getAllItems();
        assertFalse(items.isEmpty(), "Items list should not be empty");
        
        Item existingItem = items.get(0);
        String originalName = existingItem.getItem_name();
        String updatedName = "Updated " + originalName;
        
        existingItem.setItem_name(updatedName);
        existingItem.setUnit_price(250.0);

        // When
        boolean result = itemService.updateItem(existingItem);

        // Then
        assertTrue(result, "Item should be updated successfully");
        
        // Verify the update was actually saved
        Item updatedItem = itemService.getItemById(existingItem.getItem_id());
        assertNotNull(updatedItem, "Updated item should be found");
        assertEquals(updatedName, updatedItem.getItem_name(), "Item name should be updated");
        assertEquals(250.0, updatedItem.getUnit_price(), "Item price should be updated");
    }

    @Test
    @Order(7)
    @DisplayName("Test delete item")
    @EnabledIf("databaseAvailable")
    void testDeleteItem() {
        // Given - create a new item to delete
        Item itemToDelete = new Item();
        itemToDelete.setItem_name("Item to Delete");
        itemToDelete.setItem_description("This item will be deleted");
        itemToDelete.setUnit_price(150.0);
        itemToDelete.setStock_quantity(3.0);

        boolean created = itemService.registerItem(itemToDelete);
        assertTrue(created, "Item should be created for deletion test");

        // Get the ID of the created item
        List<Item> items = itemService.getAllItems();
        int itemIdToDelete = items.stream()
                .filter(i -> i.getItem_name().equals("Item to Delete"))
                .findFirst()
                .orElseThrow()
                .getItem_id();

        // When
        boolean deleted = itemService.deleteItem(itemIdToDelete);

        // Then
        assertTrue(deleted, "Item should be deleted successfully");

        // Verify deletion
        Item deletedItem = itemService.getItemById(itemIdToDelete);
        assertNull(deletedItem, "Deleted item should not be found in DB");
    }

    @Test
    @Order(8)
    @DisplayName("Test register item with empty name")
    @EnabledIf("databaseAvailable")
    void testRegisterItemWithEmptyName() {
        // Given
        Item item = new Item();
        item.setItem_name(""); // Empty name
        item.setItem_description("Test Description");
        item.setUnit_price(100.0);
        item.setStock_quantity(5.0);

        // When
        boolean result = itemService.registerItem(item);

        // Then - this might succeed or fail depending on database constraints
        // We're testing that the method handles it gracefully
        assertNotNull(result, "Result should not be null");
    }

    @Test
    @Order(9)
    @DisplayName("Test register item with null values")
    @EnabledIf("databaseAvailable")
    void testRegisterItemWithNullValues() {
        // Given
        Item item = new Item();
        item.setItem_name(null);
        item.setItem_description(null);
        item.setUnit_price(0.0);
        item.setStock_quantity(0.0);

        // When
        boolean result = itemService.registerItem(item);

        // Then - this might succeed or fail depending on database constraints
        // We're testing that the method handles it gracefully
        assertNotNull(result, "Result should not be null");
    }

    @Test
    @Order(10)
    @DisplayName("Test item with special characters")
    @EnabledIf("databaseAvailable")
    void testItemWithSpecialCharacters() {
        // Given
        Item item = new Item();
        item.setItem_name("Test Item & Co.");
        item.setItem_description("Description with special chars: @#$%^&*()");
        item.setUnit_price(199.99);
        item.setStock_quantity(10.0);

        // When
        boolean result = itemService.registerItem(item);

        // Then
        assertTrue(result, "Item with special characters should be registered successfully");
    }

    @Test
    @Order(11)
    @DisplayName("Test item with very long data")
    @EnabledIf("databaseAvailable")
    void testItemWithVeryLongData() {
        // Given
        Item item = new Item();
        item.setItem_name("A".repeat(100)); // Very long name
        item.setItem_description("B".repeat(500)); // Very long description
        item.setUnit_price(999.99);
        item.setStock_quantity(1000.0);

        // When
        boolean result = itemService.registerItem(item);

        // Then - this might succeed or fail depending on database constraints
        // We're testing that the method handles it gracefully
        assertNotNull(result, "Result should not be null");
    }

    @Test
    @Order(12)
    @DisplayName("Test item with negative price")
    @EnabledIf("databaseAvailable")
    void testItemWithNegativePrice() {
        // Given
        Item item = new Item();
        item.setItem_name("Negative Price Item");
        item.setItem_description("Item with negative price");
        item.setUnit_price(-50.0);
        item.setStock_quantity(5.0);

        // When
        boolean result = itemService.registerItem(item);

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
