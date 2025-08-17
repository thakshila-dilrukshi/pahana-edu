package com.icbt.util;

import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * Utility class for centralized error handling
 */
public class ErrorHandler {
    
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());
    
    public enum ErrorType {
        VALIDATION_ERROR("validation_error"),
        AUTH_ERROR("auth_error"), 
        SYSTEM_ERROR("system_error"),
        DATABASE_ERROR("database_error"),
        GENERAL_ERROR("error");
        
        private final String value;
        
        ErrorType(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
    }
    
    /**
     * Log an error with appropriate level
     */
    public static void logError(String message, Throwable throwable, Level level) {
        if (level == Level.SEVERE) {
            LOGGER.log(Level.SEVERE, message, throwable);
        } else if (level == Level.WARNING) {
            LOGGER.log(Level.WARNING, message, throwable);
        } else {
            LOGGER.log(Level.INFO, message, throwable);
        }
    }
    
    /**
     * Log an error without throwable
     */
    public static void logError(String message, Level level) {
        if (level == Level.SEVERE) {
            LOGGER.severe(message);
        } else if (level == Level.WARNING) {
            LOGGER.warning(message);
        } else {
            LOGGER.info(message);
        }
    }
    
    /**
     * Get user-friendly error message based on error type
     */
    public static String getUserFriendlyMessage(String technicalMessage, ErrorType errorType) {
        switch (errorType) {
            case VALIDATION_ERROR:
                return technicalMessage; // Keep validation messages as-is
            case AUTH_ERROR:
                return "Invalid username or password. Please try again.";
            case SYSTEM_ERROR:
                return "System temporarily unavailable. Please try again later.";
            case DATABASE_ERROR:
                return "Database connection error. Please try again later.";
            case GENERAL_ERROR:
            default:
                return "An error occurred. Please try again.";
        }
    }
    
    /**
     * Sanitize user input to prevent XSS
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        
        return input.replaceAll("<", "&lt;")
                   .replaceAll(">", "&gt;")
                   .replaceAll("\"", "&quot;")
                   .replaceAll("'", "&#x27;")
                   .replaceAll("&", "&amp;");
    }
}
