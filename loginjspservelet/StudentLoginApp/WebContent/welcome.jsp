<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if user is logged in
    String username = (String) session.getAttribute("username");
    Boolean isLoggedIn = (Boolean) session.getAttribute("isLoggedIn");
    
    if (username == null || isLoggedIn == null || !isLoggedIn) {
        // User is not logged in, redirect to login page
        response.sendRedirect("index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome - Student Portal</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
            min-height: 100vh;
        }
        
        .header {
            background-color: #007bff;
            color: white;
            padding: 1rem 0;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .logo {
            font-size: 1.5rem;
            font-weight: bold;
        }
        
        .user-info {
            display: flex;
            align-items: center;
            gap: 1rem;
        }
        
        .logout-btn {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            font-size: 0.9rem;
            transition: background-color 0.3s;
        }
        
        .logout-btn:hover {
            background-color: #c82333;
        }
        
        .main-content {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        
        .welcome-card {
            background-color: white;
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 2rem;
        }
        
        .welcome-title {
            color: #333;
            margin-bottom: 1rem;
        }
        
        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 2rem;
        }
        
        .dashboard-card {
            background-color: white;
            padding: 1.5rem;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            border-left: 4px solid #007bff;
        }
        
        .dashboard-card h3 {
            margin-top: 0;
            color: #333;
        }
        
        .dashboard-card p {
            color: #666;
            line-height: 1.6;
        }
        
        .feature-list {
            list-style: none;
            padding: 0;
        }
        
        .feature-list li {
            padding: 0.5rem 0;
            border-bottom: 1px solid #eee;
        }
        
        .feature-list li:last-child {
            border-bottom: none;
        }
        
        .feature-list li:before {
            content: "✓";
            color: #28a745;
            font-weight: bold;
            margin-right: 0.5rem;
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="header-content">
            <div class="logo">Student Portal</div>
            <div class="user-info">
                <span>Welcome, <%= username %>!</span>
                <a href="logout.jsp" class="logout-btn">Logout</a>
            </div>
        </div>
    </header>
    
    <main class="main-content">
        <div class="welcome-card">
            <h1 class="welcome-title">Welcome to the Student Portal!</h1>
            <p>Hello <strong><%= username %></strong>, you have successfully logged into the student management system. 
               This portal provides access to various academic and administrative services.</p>
            <p><strong>Login Time:</strong> <%= new java.util.Date() %></p>
        </div>
        
        <div class="dashboard-grid">
            <div class="dashboard-card">
                <h3>Academic Information</h3>
                <p>Access your academic records, grades, and course information.</p>
                <ul class="feature-list">
                    <li>View current courses</li>
                    <li>Check grades and transcripts</li>
                    <li>Academic calendar</li>
                    <li>Course registration</li>
                </ul>
            </div>
            
            <div class="dashboard-card">
                <h3>Student Services</h3>
                <p>Manage your student account and access various services.</p>
                <ul class="feature-list">
                    <li>Update personal information</li>
                    <li>Financial aid status</li>
                    <li>Library services</li>
                    <li>Campus resources</li>
                </ul>
            </div>
            
            <div class="dashboard-card">
                <h3>Communication</h3>
                <p>Stay connected with faculty, staff, and fellow students.</p>
                <ul class="feature-list">
                    <li>Messages and announcements</li>
                    <li>Faculty contact information</li>
                    <li>Student forums</li>
                    <li>Event notifications</li>
                </ul>
            </div>
            
            <div class="dashboard-card">
                <h3>Quick Actions</h3>
                <p>Frequently used features and shortcuts.</p>
                <ul class="feature-list">
                    <li>Print student ID card</li>
                    <li>Download documents</li>
                    <li>Submit assignments</li>
                    <li>Schedule appointments</li>
                </ul>
            </div>
        </div>
    </main>
</body>
</html>