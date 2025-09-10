# Troubleshooting Guide

## Issue: Handler Method Mapping Conflict

### Problem Description
The application was failing to start with the following error:
```
java.lang.IllegalStateException: Ambiguous mapping. Cannot map 'studentRestController' method
```

### Root Cause
The error occurred because there were two REST controllers with conflicting request mappings:
1. `StudentRestController` - Original controller
2. `StudentApiController` - New RESTful controller

Both controllers were using the same base mapping `/api/students`, which caused Spring Boot to be unable to determine which controller should handle incoming requests.

### Solution Applied
1. **Removed the conflicting controller**: Deleted `StudentRestController.java` to eliminate the mapping conflict
2. **Kept the new RESTful controller**: `StudentApiController.java` now handles all API requests
3. **Cleaned build artifacts**: Removed the `target` directory to ensure clean compilation

### Current Controller Structure

#### API Controller (`StudentApiController.java`)
- **Base Path**: `/api/students`
- **Purpose**: RESTful API endpoints for JSON responses
- **Endpoints**:
  - `GET /api/students` - Get all students
  - `GET /api/students/{id}` - Get specific student
  - `POST /api/students` - Create student
  - `PATCH /api/students/{id}` - Update student
  - `DELETE /api/students/{id}` - Delete student

#### Web Controller (`StudentFormController.java`)
- **Base Path**: `/` (root and various web paths)
- **Purpose**: Web form handling for Thymeleaf templates
- **Endpoints**:
  - `GET /` - Home page
  - `GET /students` - Student list page
  - `POST /studentForm` - Form submission
  - etc.

### Verification Steps
1. Start the Spring Boot application
2. Check that no mapping conflicts occur during startup
3. Test API endpoints using Postman or curl
4. Verify web interface still works

### Prevention
To avoid similar issues in the future:
1. **Unique mappings**: Ensure each controller has unique request mappings
2. **Clear naming**: Use descriptive controller names that indicate their purpose
3. **Separation of concerns**: Keep API controllers separate from web controllers
4. **Documentation**: Maintain clear documentation of all endpoints

### Testing the Fix
After applying this fix, you should be able to:
1. Start the application without errors
2. Access the web interface at `http://localhost:8080`
3. Use the API endpoints at `http://localhost:8080/api/students`
4. Test with Postman using the provided collection

### Additional Notes
- The web interface functionality remains unchanged
- All API functionality is now consolidated in `StudentApiController`
- Response format is now consistent across all API endpoints
- CORS is enabled for API testing with external tools

## Common Spring Boot Mapping Errors

### 1. Ambiguous Mapping
**Cause**: Multiple controllers with same request mapping
**Solution**: Ensure unique mappings or use different base paths

### 2. Method Not Allowed
**Cause**: Wrong HTTP method used for endpoint
**Solution**: Check HTTP method (GET, POST, PUT, PATCH, DELETE)

### 3. 404 Not Found
**Cause**: Endpoint path doesn't match controller mapping
**Solution**: Verify URL path matches controller mapping exactly

### 4. 400 Bad Request
**Cause**: Invalid request body or missing required parameters
**Solution**: Check request format and required fields

### 5. 500 Internal Server Error
**Cause**: Exception in controller method
**Solution**: Check application logs for stack trace