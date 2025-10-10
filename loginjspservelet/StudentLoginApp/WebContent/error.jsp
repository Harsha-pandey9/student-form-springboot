<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Student Portal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        
        .error-container {
            background-color: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 500px;
            width: 100%;
        }
        
        .error-icon {
            font-size: 4rem;
            color: #dc3545;
            margin-bottom: 1rem;
        }
        
        .error-title {
            color: #333;
            margin-bottom: 1rem;
        }
        
        .error-message {
            color: #666;
            margin-bottom: 2rem;
            line-height: 1.6;
        }
        
        .back-btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 0.75rem 1.5rem;
            border-radius: 4px;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s;
        }
        
        .back-btn:hover {
            background-color: #0056b3;
        }
        
        .error-details {
            margin-top: 2rem;
            padding: 1rem;
            background-color: #f8f9fa;
            border-radius: 4px;
            text-align: left;
            font-family: monospace;
            font-size: 0.9rem;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        <h1 class="error-title">Oops! Something went wrong</h1>
        <div class="error-message">
            <p>We're sorry, but an error occurred while processing your request.</p>
            <p>Please try again or contact the system administrator if the problem persists.</p>
        </div>
        
        <a href="index.jsp" class="back-btn">Return to Login</a>
        
        <% if (request.getAttribute("jakarta.servlet.error.status_code") != null) { %>
        <div class="error-details">
            <strong>Error Details:</strong><br>
            Status Code: <%= request.getAttribute("jakarta.servlet.error.status_code") %><br>
            <% if (request.getAttribute("jakarta.servlet.error.message") != null) { %>
                Message: <%= request.getAttribute("jakarta.servlet.error.message") %><br>
            <% } %>
            <% if (request.getAttribute("jakarta.servlet.error.request_uri") != null) { %>
                Request URI: <%= request.getAttribute("jakarta.servlet.error.request_uri") %><br>
            <% } %>
            Time: <%= new java.util.Date() %>
        </div>
        <% } %>
    </div>
</body>
</html>