# Global Exception Handling Guide

## 🛡️ **Overview**

Your application now has a comprehensive global exception handling system that provides consistent error responses across both API endpoints and web pages.

## 📁 **Exception Handling Structure**

### **1. Global Exception Handler**
- **File**: `GlobalExceptionHandler.java`
- **Purpose**: Centralized exception handling for the entire application
- **Scope**: Handles both API (JSON) and Web (HTML) requests

### **2. Custom Exception Classes**
- **`StudentNotFoundException`**: When a student is not found
- **`DuplicateRollNumberException`**: When attempting to use an existing roll number
- **`InvalidStudentDataException`**: When student data validation fails

## 🔧 **Exception Types Handled**

### **Built-in Spring Exceptions:**
| Exception | Description | HTTP Status |
|-----------|-------------|-------------|
| `MethodArgumentNotValidException` | Validation errors (@Valid) | 400 Bad Request |
| `ConstraintViolationException` | Constraint violations | 400 Bad Request |
| `DataIntegrityViolationException` | Database integrity violations | 409 Conflict |
| `IllegalArgumentException` | Invalid arguments | 400 Bad Request |
| `RuntimeException` | General runtime errors | 400 Bad Request |
| `Exception` | Catch-all for unexpected errors | 500 Internal Server Error |

### **Custom Exceptions:**
| Exception | Description | HTTP Status |
|-----------|-------------|-------------|
| `StudentNotFoundException` | Student not found by ID | 404 Not Found |
| `DuplicateRollNumberException` | Roll number already exists | 409 Conflict |
| `InvalidStudentDataException` | Invalid student data | 400 Bad Request |

## 🌐 **Response Formats**

### **API Responses (JSON):**
```json
{
    "success": false,
    "message": "Error description",
    "error": "Detailed error message",
    "timestamp": "2024-01-01T12:00:00",
    "path": "uri=/api/users/1"
}
```

### **Validation Error Response:**
```json
{
    "success": false,
    "message": "Validation failed",
    "errors": {
        "name": "Name is required",
        "rollNo": "Roll number must be positive"
    },
    "timestamp": "2024-01-01T12:00:00",
    "path": "uri=/api/users"
}
```

### **Custom Exception Response:**
```json
{
    "success": false,
    "message": "Student not found",
    "error": "Student not found with ID: 123",
    "studentId": 123,
    "timestamp": "2024-01-01T12:00:00",
    "path": "uri=/api/users/123"
}
```

### **Web Responses (HTML):**
- Redirects to `users/error.html` template
- Error message displayed to user
- User-friendly error descriptions

## 🎯 **How It Works**

### **1. Request Detection**
The handler automatically detects whether a request is:
- **API Request**: Contains `/api/` in URL or `Accept: application/json` header
- **Web Request**: All other requests

### **2. Response Routing**
- **API Requests**: Return JSON error responses
- **Web Requests**: Return HTML error pages

### **3. Exception Hierarchy**
1. **Custom Exceptions** (most specific)
2. **Spring Framework Exceptions**
3. **General Runtime Exceptions**
4. **Catch-all Exception Handler**

## 🔍 **Usage Examples**

### **In Service Layer:**
```java
// Throw custom exceptions
throw new StudentNotFoundException(studentId);
throw new DuplicateRollNumberException(rollNumber);
throw new InvalidStudentDataException("name", value, "Name cannot be empty");
```

### **Automatic Handling:**
```java
// These are automatically caught and handled
@Valid @RequestBody Student student  // Validation errors
studentRepository.save(student)      // Data integrity violations
```

## 📊 **Error Scenarios Covered**

### **1. Validation Errors**
- **Trigger**: `@Valid` annotation failures
- **Example**: Empty name, negative roll number
- **Response**: Field-specific error messages

### **2. Business Logic Errors**
- **Trigger**: Custom exception in service layer
- **Example**: Student not found, duplicate roll number
- **Response**: Specific error with context

### **3. Database Errors**
- **Trigger**: Database constraint violations
- **Example**: Unique constraint violation
- **Response**: User-friendly database error message

### **4. Unexpected Errors**
- **Trigger**: Any unhandled exception
- **Example**: Network issues, system errors
- **Response**: Generic error message (security)

## 🛠️ **Configuration**

### **Logging**
- All exceptions are logged with appropriate levels
- Stack traces included for debugging
- Request context preserved

### **Security**
- Sensitive information filtered from responses
- Generic messages for unexpected errors
- No stack traces exposed to clients

## 🧪 **Testing Exception Handling**

### **API Testing (Postman):**

**1. Validation Error:**
```bash
POST /api/users
{
    "name": "",
    "rollNo": -1,
    "branch": "",
    "course": ""
}
```

**2. Duplicate Roll Number:**
```bash
POST /api/users
{
    "name": "Test User",
    "rollNo": 12345,  // Existing roll number
    "branch": "CS",
    "course": "B.Tech"
}
```

**3. Student Not Found:**
```bash
GET /api/users/99999
```

### **Web Testing (Browser):**

**1. Access non-existent student:**
```
http://localhost:8080/web/users/99999
```

**2. Submit invalid form data:**
- Leave required fields empty
- Enter negative roll number

## 🎉 **Benefits**

### **1. Consistency**
- ✅ Uniform error responses across the application
- ✅ Same error handling for API and web requests
- ✅ Standardized error format

### **2. Maintainability**
- ✅ Centralized exception handling
- ✅ Easy to add new exception types
- ✅ Consistent logging and monitoring

### **3. User Experience**
- ✅ Clear, actionable error messages
- ✅ Appropriate HTTP status codes
- ✅ User-friendly web error pages

### **4. Security**
- ✅ No sensitive information leaked
- ✅ Consistent error responses
- ✅ Proper logging for debugging

### **5. Development**
- ✅ Easy to debug with comprehensive logging
- ✅ Clear exception hierarchy
- ✅ Automatic handling of common scenarios

## 🚀 **Ready to Use**

Your application now has enterprise-grade exception handling that:
- ✅ **Handles all error scenarios**
- ✅ **Provides consistent responses**
- ✅ **Logs appropriately for debugging**
- ✅ **Maintains security best practices**
- ✅ **Works for both API and web requests**

The global exception handler is automatically active and will handle all exceptions thrown anywhere in your application! 🛡️