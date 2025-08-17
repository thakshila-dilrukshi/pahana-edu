package com.icbt.service;

import com.icbt.model.User;
import com.icbt.util.DBConnection;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

    private static UserService userService;
    private static boolean databaseAvailable;

    @BeforeAll
    static void setUpClass() {
        userService = new UserService();
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
    @DisplayName("Test login with empty username")
    void testLoginWithEmptyUsername() {
        // Given
        String username = "";
        String password = "testpass";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    @Order(3)
    @DisplayName("Test login with null username")
    void testLoginWithNullUsername() {
        // Given
        String username = null;
        String password = "testpass";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    @Order(4)
    @DisplayName("Test login with empty password")
    void testLoginWithEmptyPassword() {
        // Given
        String username = "testuser";
        String password = "";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Password is required", exception.getMessage());
    }

    @Test
    @Order(5)
    @DisplayName("Test login with null password")
    void testLoginWithNullPassword() {
        // Given
        String username = "testuser";
        String password = null;

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Password is required", exception.getMessage());
    }

    @Test
    @Order(6)
    @DisplayName("Test login with whitespace-only username")
    void testLoginWithWhitespaceOnlyUsername() {
        // Given
        String username = "   ";
        String password = "testpass";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Username is required", exception.getMessage());
    }

    @Test
    @Order(7)
    @DisplayName("Test login with whitespace-only password")
    void testLoginWithWhitespaceOnlyPassword() {
        // Given
        String username = "testuser";
        String password = "   ";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Password is required", exception.getMessage());
    }

    @Test
    @Order(8)
    @DisplayName("Test login with valid credentials from database")
    @EnabledIf("databaseAvailable")
    void testLoginWithValidCredentials() throws UserService.LoginException {
        // Given - using test data from database
        String username = "testadmin";
        String password = "testpass123";

        // When
        User user = userService.login(username, password);

        // Then
        assertNotNull(user, "User should not be null for valid credentials");
        assertEquals(username, user.getUsername(), "Username should match input");
        assertTrue(user.getId() > 0, "User ID should be positive");
    }

    @Test
    @Order(9)
    @DisplayName("Test login with another valid user from database")
    @EnabledIf("databaseAvailable")
    void testLoginWithAnotherValidUser() throws UserService.LoginException {
        // Given - using test data from database
        String username = "testuser1";
        String password = "password123";

        // When
        User user = userService.login(username, password);

        // Then
        assertNotNull(user, "User should not be null for valid credentials");
        assertEquals(username, user.getUsername(), "Username should match input");
        assertTrue(user.getId() > 0, "User ID should be positive");
    }

    @Test
    @Order(10)
    @DisplayName("Test login with invalid credentials")
    @EnabledIf("databaseAvailable")
    void testLoginWithInvalidCredentials() {
        // Given
        String username = "nonexistentuser";
        String password = "wrongpassword";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    @Order(11)
    @DisplayName("Test login with wrong password for existing user")
    @EnabledIf("databaseAvailable")
    void testLoginWithWrongPassword() {
        // Given
        String username = "testadmin";
        String password = "wrongpassword";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    @Order(12)
    @DisplayName("Test login with special characters in username")
    @EnabledIf("databaseAvailable")
    void testLoginWithSpecialCharacters() {
        // Given
        String username = "test@user#123";
        String password = "password123";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    @Order(13)
    @DisplayName("Test login with very long username")
    void testLoginWithVeryLongUsername() {
        // Given
        String username = "a".repeat(1000); // Very long username
        String password = "password123";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    @Order(14)
    @DisplayName("Test login with SQL injection attempt")
    void testLoginWithSqlInjectionAttempt() {
        // Given
        String username = "admin' OR '1'='1";
        String password = "password123";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Invalid username or password", exception.getMessage());
    }

    @Test
    @Order(15)
    @DisplayName("Test login with XSS attempt")
    void testLoginWithXssAttempt() {
        // Given
        String username = "<script>alert('xss')</script>";
        String password = "password123";

        // When & Then
        UserService.LoginException exception = assertThrows(UserService.LoginException.class, () -> {
            userService.login(username, password);
        });
        assertEquals("Invalid username or password", exception.getMessage());
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
     * Condition method for @EnabledIf annotation
     */
    static boolean databaseAvailable() {
        return databaseAvailable;
    }
}
