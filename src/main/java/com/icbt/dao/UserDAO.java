package com.icbt.dao;

import com.icbt.model.User;
import com.icbt.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    public static class DatabaseException extends Exception {
        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
        
        public DatabaseException(String message) {
            super(message);
        }
    }
    
    public User getUser(String username, String password) throws DatabaseException {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            throw new DatabaseException("Username and password cannot be empty");
        }
        
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        
        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                throw new DatabaseException("Unable to establish database connection");
            }
            
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, username.trim());
                ps.setString(2, password.trim());
                
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return new User(
                                rs.getInt("id"),
                                rs.getString("username"),
                                rs.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred during login: " + e.getMessage(), e);
        }
        return null;
    }
}