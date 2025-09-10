# User Management API - Postman Testing Guide

This guide provides comprehensive instructions for testing the User Management API using Postman.

## Base URL
```
http://localhost:8080
```

## API Endpoints Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get a specific user by ID |
| POST | `/api/users` | Create a new user |
| PATCH | `/api/users/{id}` | Update a specific user (partial) |
| DELETE | `/api/users/{id}` | Delete a specific user |

---

## 1. GET - Get All Users

**Endpoint:** `GET /api/users`

**Description:** Retrieve all users from the database

**Headers:**
```
Content-Type: application/json
```

**Request Body:** None

**Example Response (200 OK):**
```json
{
    "success": true,
    "message": "Students retrieved successfully",
    "data": [
        {
            "id": 1,
            "name": "John Doe",
            "rollNo": 12345,
            "branch": "Computer Science",
            "course": "B.Tech"
        },
        {
            "id": 2,
            "name": "Jane Smith",
            "rollNo": 12346,
            "branch": "Electrical Engineering",
            "course": "B.Tech"
        }
    ],
    "count": 2
}
```

**Postman Setup:**
1. Method: GET
2. URL: `http://localhost:8080/api/users`
3. Headers: Content-Type: application/json
4. Body: None

---

## 2. GET - Get Specific User by ID

**Endpoint:** `GET /api/users/{id}`

**Description:** Retrieve a specific user by their ID

**Headers:**
```
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): User ID

**Request Body:** None

**Example Response (200 OK):**
```json
{
    "success": true,
    "message": "Student found successfully",
    "data": {
        "id": 1,
        "name": "John Doe",
        "rollNo": 12345,
        "branch": "Computer Science",
        "course": "B.Tech"
    }
}
```

**Example Response (404 Not Found):**
```json
{
    "success": false,
    "message": "Student not found",
    "error": "No student exists with ID: 999"
}
```

**Postman Setup:**
1. Method: GET
2. URL: `http://localhost:8080/api/users/1`
3. Headers: Content-Type: application/json
4. Body: None

---

## 3. POST - Create a New User

**Endpoint:** `POST /api/users`

**Description:** Create a new user record

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
    "name": "Alice Johnson",
    "rollNo": 12347,
    "branch": "Mechanical Engineering",
    "course": "B.Tech"
}
```

**Example Response (201 Created):**
```json
{
    "success": true,
    "message": "Student created successfully",
    "data": {
        "id": 3,
        "name": "Alice Johnson",
        "rollNo": 12347,
        "branch": "Mechanical Engineering",
        "course": "B.Tech"
    }
}
```

**Example Response (400 Bad Request - Validation Error):**
```json
{
    "success": false,
    "message": "Validation failed",
    "errors": {
        "name": "Name is required",
        "rollNo": "Roll number must be positive"
    }
}
```

**Example Response (400 Bad Request - Duplicate Roll Number):**
```json
{
    "success": false,
    "message": "Error creating student",
    "error": "Student with roll number 12345 already exists"
}
```

**Postman Setup:**
1. Method: POST
2. URL: `http://localhost:8080/api/users`
3. Headers: Content-Type: application/json
4. Body: raw (JSON)

---

## 4. PATCH - Update Specific User (Partial Update)

**Endpoint:** `PATCH /api/users/{id}`

**Description:** Update specific fields of an existing user

**Headers:**
```
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): User ID

**Request Body (Partial Update - Only fields to update):**
```json
{
    "name": "John Doe Updated",
    "branch": "Information Technology"
}
```

**Alternative Request Body (Single Field Update):**
```json
{
    "course": "M.Tech"
}
```

**Example Response (200 OK):**
```json
{
    "success": true,
    "message": "Student updated successfully",
    "data": {
        "id": 1,
        "name": "John Doe Updated",
        "rollNo": 12345,
        "branch": "Information Technology",
        "course": "B.Tech"
    }
}
```

**Example Response (404 Not Found):**
```json
{
    "success": false,
    "message": "Error updating student",
    "error": "Student not found with id: 999"
}
```

**Postman Setup:**
1. Method: PATCH
2. URL: `http://localhost:8080/api/users/1`
3. Headers: Content-Type: application/json
4. Body: raw (JSON)

---

## 5. DELETE - Delete Specific User

**Endpoint:** `DELETE /api/users/{id}`

**Description:** Delete a specific user by their ID

**Headers:**
```
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): User ID

**Request Body:** None

**Example Response (200 OK):**
```json
{
    "success": true,
    "message": "Student deleted successfully",
    "deletedStudent": {
        "id": 1,
        "name": "John Doe",
        "rollNo": 12345,
        "branch": "Computer Science",
        "course": "B.Tech"
    }
}
```

**Example Response (404 Not Found):**
```json
{
    "success": false,
    "message": "Student not found",
    "error": "No student exists with ID: 999"
}
```

**Postman Setup:**
1. Method: DELETE
2. URL: `http://localhost:8080/api/users/1`
3. Headers: Content-Type: application/json
4. Body: None

---

## Additional Utility Endpoints

### Search Users by Name
**Endpoint:** `GET /api/users/search?name={name}`

**Example:** `GET /api/users/search?name=John`

### Get User by Roll Number
**Endpoint:** `GET /api/users/rollno/{rollNo}`

**Example:** `GET /api/users/rollno/12345`

### Get Users by Branch
**Endpoint:** `GET /api/users/branch/{branch}`

**Example:** `GET /api/users/branch/Computer Science`

### Get Users by Course
**Endpoint:** `GET /api/users/course/{course}`

**Example:** `GET /api/users/course/B.Tech`

---

## Complete Testing Workflow

### 1. Test Complete CRUD Operations

1. **Create a User (POST)**
   ```
   POST /api/users
   Body: {"name": "Test User", "rollNo": 99999, "branch": "Test Branch", "course": "Test Course"}
   ```

2. **Get All Users (GET)**
   ```
   GET /api/users
   ```

3. **Get Specific User (GET)**
   ```
   GET /api/users/{id}
   ```

4. **Update User (PATCH)**
   ```
   PATCH /api/users/{id}
   Body: {"name": "Updated Test User"}
   ```

5. **Delete User (DELETE)**
   ```
   DELETE /api/users/{id}
   ```

### 2. Test Error Scenarios

1. **Get Non-existent User**
   ```
   GET /api/users/999999
   ```

2. **Create User with Invalid Data**
   ```
   POST /api/users
   Body: {"name": "", "rollNo": -1}
   ```

3. **Create User with Duplicate Roll Number**
   ```
   POST /api/users
   Body: {"name": "Duplicate", "rollNo": 12345, "branch": "Test", "course": "Test"}
   ```

4. **Update Non-existent User**
   ```
   PATCH /api/users/999999
   Body: {"name": "Test"}
   ```

5. **Delete Non-existent User**
   ```
   DELETE /api/users/999999
   ```

---

## Postman Collection Import

You can create a Postman collection with the following structure:

```
User Management API
├── Core CRUD Operations
│   ├── GET - Get All Users
│   ├── GET - Get User by ID
│   ├── POST - Create User
│   ├── PATCH - Update User
│   └── DELETE - Delete User
├── Utility Endpoints
│   ├── GET - Search by Name
│   ├── GET - Get by Roll Number
│   ├── GET - Get by Branch
│   └── GET - Get by Course
└── Error Testing
    ├── GET - Non-existent User
    ├── POST - Invalid Data
    ├── POST - Duplicate Roll Number
    ├── PATCH - Non-existent User
    └── DELETE - Non-existent User
```

---

## Environment Variables (Optional)

Set up environment variables in Postman:

- `baseUrl`: `http://localhost:8080`
- `apiPath`: `/api/users`

Then use: `{{baseUrl}}{{apiPath}}`

---

## Response Status Codes

| Status Code | Description |
|-------------|-------------|
| 200 OK | Request successful |
| 201 Created | Resource created successfully |
| 400 Bad Request | Invalid request data |
| 404 Not Found | Resource not found |
| 500 Internal Server Error | Server error |

---

## Testing Tips

1. **Start your Spring Boot application** before testing
2. **Test in order**: Create → Read → Update → Delete
3. **Use variables** for dynamic IDs in Postman
4. **Check response status codes** and response body structure
5. **Test both success and error scenarios**
6. **Verify data persistence** by checking database or using GET requests

---

## Sample Test Data

```json
[
    {
        "name": "John Doe",
        "rollNo": 12345,
        "branch": "Computer Science",
        "course": "B.Tech"
    },
    {
        "name": "Jane Smith",
        "rollNo": 12346,
        "branch": "Electrical Engineering",
        "course": "B.Tech"
    },
    {
        "name": "Alice Johnson",
        "rollNo": 12347,
        "branch": "Mechanical Engineering",
        "course": "M.Tech"
    }
]
```

---

## Quick Start Commands

### Using cURL:

**Get All Users:**
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Content-Type: application/json"
```

**Create User:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "Test User", "rollNo": 99999, "branch": "Test Branch", "course": "Test Course"}'
```

**Update User:**
```bash
curl -X PATCH http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Updated User"}'
```

**Delete User:**
```bash
curl -X DELETE http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json"
```