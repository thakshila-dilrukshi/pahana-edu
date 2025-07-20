package com.icbt.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {
    @Test
    void testConnectionIsValid() {
        try {
            Connection conn = DBConnection.getConnection();
            assertNotNull(conn, "Connection should not be null");
            assertFalse(conn.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("Failed to connect to DB: " + e.getMessage());
        }
    }
}