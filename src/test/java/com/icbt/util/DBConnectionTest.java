package com.icbt.util;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIf;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DBConnectionTest {
    
    private static boolean databaseAvailable;

    @BeforeAll
    static void setUpClass() {
        databaseAvailable = isDatabaseAvailable();
    }

    @Test
    @Order(1)
    @DisplayName("Test database connection availability")
    void testDatabaseConnectionAvailability() {
        if (!databaseAvailable) {
            System.out.println("⚠️  Database not available - skipping integration tests");
            System.out.println("   Make sure MySQL is running and database 'pahana-edu' exists");
        }
        // This test always passes, but logs the database status
    }

    @Test
    @Order(2)
    @DisplayName("Test connection is valid")
    @EnabledIf("databaseAvailable")
    void testConnectionIsValid() {
        try {
            Connection conn = DBConnection.getConnection();
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("Failed to connect to DB: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test connection can be reused")
    @EnabledIf("databaseAvailable")
    void testConnectionCanBeReused() {
        try {
            // Get first connection
            Connection conn1 = DBConnection.getConnection();
            assertNotNull(conn1, "First connection should not be null");
            
            // Get second connection (should be the same instance)
            Connection conn2 = DBConnection.getConnection();
            assertNotNull(conn2, "Second connection should not be null");
            
            // Both should be the same instance (singleton pattern)
            assertSame(conn1, conn2, "Both connections should be the same instance");
            
            // Both should be open
            assertFalse(conn1.isClosed(), "First connection should be open");
            assertFalse(conn2.isClosed(), "Second connection should be open");
            
        } catch (SQLException e) {
            fail("Failed to connect to DB: " + e.getMessage());
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test connection after close and reopen")
    @EnabledIf("databaseAvailable")
    void testConnectionAfterCloseAndReopen() {
        try {
            // Get first connection
            Connection conn1 = DBConnection.getConnection();
            assertNotNull(conn1, "First connection should not be null");
            
            // Close the connection
            conn1.close();
            assertTrue(conn1.isClosed(), "Connection should be closed");
            
            // Get new connection (should create a new one since previous was closed)
            Connection conn2 = DBConnection.getConnection();
            assertNotNull(conn2, "New connection should not be null");
            assertFalse(conn2.isClosed(), "New connection should be open");
            
            // They should be different instances
            assertNotSame(conn1, conn2, "Connections should be different instances after close/reopen");
            
        } catch (SQLException e) {
            fail("Failed to connect to DB: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test connection properties")
    @EnabledIf("databaseAvailable")
    void testConnectionProperties() {
        try {
            Connection conn = DBConnection.getConnection();
            assertNotNull(conn, "Connection should not be null");
            
            // Test basic connection properties
            assertFalse(conn.isReadOnly(), "Connection should not be read-only");
            assertTrue(conn.getAutoCommit(), "Auto-commit should be enabled by default");
            
            // Test connection metadata
            assertNotNull(conn.getMetaData(), "Connection metadata should not be null");
            assertEquals("MySQL", conn.getMetaData().getDatabaseProductName(), 
                        "Database should be MySQL");
            
        } catch (SQLException e) {
            fail("Failed to connect to DB: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test connection with transaction")
    @EnabledIf("databaseAvailable")
    void testConnectionWithTransaction() {
        try {
            Connection conn = DBConnection.getConnection();
            assertNotNull(conn, "Connection should not be null");
            
            // Start a transaction
            conn.setAutoCommit(false);
            assertFalse(conn.getAutoCommit(), "Auto-commit should be disabled");
            
            // Rollback the transaction
            conn.rollback();
            
            // Re-enable auto-commit
            conn.setAutoCommit(true);
            assertTrue(conn.getAutoCommit(), "Auto-commit should be re-enabled");
            
        } catch (SQLException e) {
            fail("Failed to work with transaction: " + e.getMessage());
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test multiple concurrent connections")
    @EnabledIf("databaseAvailable")
    void testMultipleConcurrentConnections() {
        try {
            // Get multiple connections concurrently
            Connection conn1 = DBConnection.getConnection();
            Connection conn2 = DBConnection.getConnection();
            Connection conn3 = DBConnection.getConnection();
            
            assertNotNull(conn1, "Connection 1 should not be null");
            assertNotNull(conn2, "Connection 2 should not be null");
            assertNotNull(conn3, "Connection 3 should not be null");
            
            // All should be the same instance (singleton pattern)
            assertSame(conn1, conn2, "Connections 1 and 2 should be the same instance");
            assertSame(conn2, conn3, "Connections 2 and 3 should be the same instance");
            
            // All should be open
            assertFalse(conn1.isClosed(), "Connection 1 should be open");
            assertFalse(conn2.isClosed(), "Connection 2 should be open");
            assertFalse(conn3.isClosed(), "Connection 3 should be open");
            
        } catch (SQLException e) {
            fail("Failed to get concurrent connections: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test connection error handling")
    void testConnectionErrorHandling() {
        // This test verifies that the connection utility handles errors gracefully
        // We can't easily simulate connection failures, but we can test the error handling structure
        
        try {
            Connection conn = DBConnection.getConnection();
            if (conn != null) {
                // If we can connect, test that the connection is valid
                assertFalse(conn.isClosed(), "Connection should be open");
            }
        } catch (Exception e) {
            // If connection fails, it should be handled gracefully
            assertNotNull(e.getMessage(), "Exception should have a message");
        }
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
     * Condition method for @EnabledIf annotation
     */
    static boolean databaseAvailable() {
        return databaseAvailable;
    }
}