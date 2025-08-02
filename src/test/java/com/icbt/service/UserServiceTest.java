package com.icbt.service;

import com.icbt.dao.UserDAO;
import com.icbt.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
        userDAO = new UserDAO(); // initialize before each test
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Replace these with actual values from your database
        String uname = "admin";
        String pass = "admin@123";

        User user = userDAO.getUser(uname, pass);

        assertNotNull(user, "User should not be null for valid credentials");
        assertEquals(uname, user.getUsername(), "Username should match input");
    }

    @Test
    public void testLoginWithInvalidCredentials() {
        String uname = "invalidUser";
        String pass = "wrongPass";

        User user = userDAO.getUser(uname, pass);

        assertNull(user, "User should be null for invalid credentials");
    }
}
