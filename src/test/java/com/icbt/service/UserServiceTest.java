package com.icbt.service;

import com.icbt.dao.UserDAO;
import com.icbt.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
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
    void testLoginWithValidInputFormat() {
        // Given
        String username = "testuser";
        String password = "testpass";

        // When & Then - This will likely fail with database connection, but we're testing the validation logic
        // The actual database test would require a test database setup
        try {
            userService.login(username, password);
        } catch (UserService.LoginException e) {
            // This is expected if the database is not available or credentials are invalid
            // The important thing is that validation passed
            assertTrue(e.getMessage().contains("Invalid username or password") || 
                      e.getMessage().contains("Database") ||
                      e.getMessage().contains("connection"));
        }
    }
}
