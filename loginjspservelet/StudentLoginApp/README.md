# Student Login Application

A simple JSP and Servlet-based web application demonstrating user authentication and session management for a student portal.

## Project Structure

```
StudentLoginApp/
├── src/
│   └── com/example/
│       └── LoginServlet.java          # Main servlet handling login logic
├── WebContent/
│   ├── index.jsp                      # Login page
│   ├── welcome.jsp                    # Dashboard after successful login
│   ├── logout.jsp                     # Logout functionality
│   ├── error.jsp                      # Error handling page
│   └── WEB-INF/
│       └── web.xml                    # Web application configuration
└── README.md                          # This file
```

## Features

- **User Authentication**: Simple username/password validation
- **Session Management**: Secure session handling with timeout
- **Responsive Design**: Mobile-friendly interface
- **Error Handling**: Comprehensive error pages
- **Security**: Session protection and logout functionality

## Demo Credentials

- **Username**: `student`
- **Password**: `password123`

## Technologies Used

- **Java Servlets**: Backend logic and request handling
- **JSP (JavaServer Pages)**: Dynamic web page generation
- **HTML5/CSS3**: Frontend styling and layout
- **HTTP Sessions**: User state management

## Setup Instructions

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Tomcat 9.0 or higher
- IDE with Java web development support (Eclipse, IntelliJ IDEA, etc.)

### Deployment Steps

1. **Clone or Download** the project to your local machine

2. **Import into IDE**:
   - Open your Java IDE
   - Import as a Dynamic Web Project
   - Ensure the project structure matches the layout above

3. **Configure Build Path**:
   - Add Servlet API to the build path
   - Ensure proper Java version is selected

4. **Deploy to Tomcat**:
   - Right-click project → Run As → Run on Server
   - Select Apache Tomcat server
   - Choose "Always use this server when running this project"

5. **Access the Application**:
   - Open browser and navigate to: `http://localhost:8080/StudentLoginApp/`
   - Use the demo credentials to log in

## Application Flow

1. **Login Page** (`index.jsp`):
   - User enters username and password
   - Form submits to `LoginServlet`

2. **Authentication** (`LoginServlet.java`):
   - Validates credentials against hardcoded values
   - Creates session for valid users
   - Redirects to welcome page or back to login with error

3. **Welcome Page** (`welcome.jsp`):
   - Displays personalized dashboard
   - Shows various student portal features
   - Provides logout functionality

4. **Session Management**:
   - Sessions expire after 30 minutes of inactivity
   - Logout properly invalidates sessions
   - Protected pages check for valid sessions

## Security Features

- **Session Validation**: All protected pages verify user authentication
- **Session Timeout**: Automatic logout after inactivity
- **HTTP-Only Cookies**: Enhanced security for session cookies
- **Error Handling**: Graceful error pages without exposing system details

## Customization

### Adding New Users

Currently uses hardcoded credentials in `LoginServlet.java`. To add database support:

1. Add database dependency (MySQL, PostgreSQL, etc.)
2. Create user table with username/password fields
3. Modify `isValidCredentials()` method to query database
4. Add password hashing for security

### Styling Changes

- Modify CSS in JSP files for different themes
- Add external CSS files in `WebContent` directory
- Update responsive breakpoints as needed

### Additional Features

- Password reset functionality
- User registration
- Role-based access control
- Remember me functionality
- CAPTCHA integration

## Common Issues

### Deployment Problems

- **404 Error**: Check servlet mapping and URL patterns
- **500 Error**: Verify servlet API is in classpath
- **Session Issues**: Ensure cookies are enabled in browser

### Development Tips

- Use browser developer tools to debug session issues
- Check Tomcat logs for detailed error information
- Test with different browsers for compatibility

## License

This project is created for educational purposes and is free to use and modify.

## Contributing

Feel free to fork this project and submit pull requests for improvements:

- Enhanced security features
- Database integration
- Additional validation
- UI/UX improvements
- Mobile responsiveness enhancements

## Contact

For questions or support, please refer to the documentation or create an issue in the project repository.