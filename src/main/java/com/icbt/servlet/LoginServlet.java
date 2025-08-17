package com.icbt.servlet;
import com.icbt.model.User;
import com.icbt.service.UserService;
import com.icbt.util.ErrorHandler;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private UserService userService;
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

    @Override
    public void init() {
        userService = new UserService();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect GET requests to login page
        response.sendRedirect("login.jsp");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        try {
            // Validate input parameters
            if (username == null || password == null) {
                handleError(request, response, ErrorHandler.ErrorType.VALIDATION_ERROR, 
                           "Please provide both username and password");
                return;
            }
            
            // Sanitize inputs
            username = ErrorHandler.sanitizeInput(username.trim());
            password = password.trim(); // Don't sanitize password as it might contain special chars
            
            // Attempt login
            User user = userService.login(username, password);
            
            if (user != null) {
                // Successful login
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("username", user.getUsername());
                
                // Log successful login
                ErrorHandler.logError("User '" + username + "' logged in successfully", Level.INFO);
                
                // Redirect to main menu
                response.sendRedirect("main-menu.jsp");
            } else {
                // This should not happen due to exception handling, but just in case
                handleError(request, response, ErrorHandler.ErrorType.AUTH_ERROR, 
                           "Invalid username or password");
            }
            
        } catch (UserService.LoginException e) {
            // Handle login-specific errors
            ErrorHandler.logError("Login failed for user '" + username + "': " + e.getMessage(), Level.WARNING);
            
            ErrorHandler.ErrorType errorType = ErrorHandler.ErrorType.GENERAL_ERROR;
            String errorMessage = e.getMessage();
            
            // Categorize errors for better user experience
            if (e.getMessage().contains("Username is required") || 
                e.getMessage().contains("Password is required")) {
                errorType = ErrorHandler.ErrorType.VALIDATION_ERROR;
            } else if (e.getMessage().contains("Invalid username or password")) {
                errorType = ErrorHandler.ErrorType.AUTH_ERROR;
                errorMessage = ErrorHandler.getUserFriendlyMessage(e.getMessage(), errorType);
            } else if (e.getMessage().contains("Database") || 
                       e.getMessage().contains("connection")) {
                errorType = ErrorHandler.ErrorType.SYSTEM_ERROR;
                errorMessage = ErrorHandler.getUserFriendlyMessage(e.getMessage(), errorType);
            }
            
            handleError(request, response, errorType, errorMessage);
            
        } catch (Exception e) {
            // Handle unexpected errors
            ErrorHandler.logError("Unexpected error during login for user '" + username + "'", e, Level.SEVERE);
            handleError(request, response, ErrorHandler.ErrorType.SYSTEM_ERROR, 
                       ErrorHandler.getUserFriendlyMessage("An unexpected error occurred", ErrorHandler.ErrorType.SYSTEM_ERROR));
        }
    }
    
    private void handleError(HttpServletRequest request, HttpServletResponse response, 
                           ErrorHandler.ErrorType errorType, String errorMessage) 
            throws ServletException, IOException {
        
        request.setAttribute("error_type", errorType.getValue());
        request.setAttribute("error_message", errorMessage);
        
        // Preserve username for better UX (don't preserve password for security)
        String username = request.getParameter("username");
        if (username != null && !username.trim().isEmpty()) {
            request.setAttribute("preserved_username", username.trim());
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
        rd.forward(request, response);
    }
}