# Login Module

This is a separate login module for the Student Form application. It's designed to work independently without modifying the existing codebase.

## Structure

```
login/
├── controller/
│   └── LoginController.java       # Handles login/logout requests
├── model/
│   ├── LoginRequest.java          # DTO for login requests
│   └── LoginResponse.java         # DTO for login responses
├── service/
│   └── LoginService.java          # Business logic for authentication
└── README.md                      # This file
```

## Templates

```
templates/login/
├── login-form.html                # Login page
└── dashboard.html                 # Student dashboard after login
```

## Features

- **Independent Module**: Doesn't modify existing Student model or services
- **Session Management**: Uses HTTP sessions to track logged-in users
- **Simple Authentication**: Demo authentication (any password except "wrong")
- **Dashboard**: Shows student information after successful login
- **Integration**: Works with existing StudentRepository

## URLs

- `/login` - Login form
- `/login/dashboard` - Student dashboard (requires login)
- `/login/logout` - Logout (POST)
- `/login/status` - Check login status (AJAX endpoint)

## How it Works

1. Student enters roll number and password
2. System checks if student exists in database
3. Simple password validation (demo mode)
4. Creates session for authenticated user
5. Redirects to dashboard with student information

## Demo Mode

Currently configured for demonstration:
- Any password works except "wrong"
- No actual password storage/verification
- Easy to extend for real authentication

## Future Enhancements

- Add password field to Student model
- Implement proper password hashing
- Add remember me functionality
- Add password reset feature
- Add role-based access control