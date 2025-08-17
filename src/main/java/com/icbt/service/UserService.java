package com.icbt.service;

import com.icbt.dao.UserDAO;
import com.icbt.model.User;

public class UserService {
    private final UserDAO userDAO;
    
    public static class LoginException extends Exception {
        public LoginException(String message) {
            super(message);
        }
        
        public LoginException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    public User login(String username, String password) throws LoginException {
        try {
            // Input validation
            if (username == null || username.trim().isEmpty()) {
                throw new LoginException("Username is required");
            }
            
            if (password == null || password.trim().isEmpty()) {
                throw new LoginException("Password is required");
            }
            
            // Attempt to authenticate user
            User user = userDAO.getUser(username, password);
            
            if (user == null) {
                throw new LoginException("Invalid username or password");
            }
            
            return user;
            
        } catch (UserDAO.DatabaseException e) {
            throw new LoginException("Authentication failed: " + e.getMessage(), e);
        }
    }
}