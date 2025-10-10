package com.example;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 * Handles student login authentication
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Hardcoded credentials for demo purposes
    // In a real application, these would be stored in a database
    private static final String VALID_USERNAME = "student";
    private static final String VALID_PASSWORD = "password123";
    
    /**
     * Default constructor
     */
    public LoginServlet() {
        super();
    }

    /**
     * Handles GET requests - redirects to login page
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("index.jsp");
    }

    /**
     * Handles POST requests - processes login form submission
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Get form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // Validate credentials
        if (isValidCredentials(username, password)) {
            // Create session for authenticated user
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("isLoggedIn", true);
            
            // Redirect to welcome page
            response.sendRedirect("welcome.jsp");
        } else {
            // Invalid credentials - redirect back to login with error
            request.setAttribute("errorMessage", "Invalid username or password!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    
    /**
     * Validates user credentials
     * @param username the username to validate
     * @param password the password to validate
     * @return true if credentials are valid, false otherwise
     */
    private boolean isValidCredentials(String username, String password) {
        return VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password);
    }
}