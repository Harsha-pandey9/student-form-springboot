# Student API - Postman Testing Guide

This guide provides comprehensive instructions for testing the Student Management API using Postman.

## Base URL
```
http://localhost:8080
```

## API Endpoints Overview

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/students` | Get all students |
| GET | `/api/students/{id}` | Get a specific student by ID |
| POST | `/api/students` | Create a new student |
| PATCH | `/api/students/{id}` | Update a specific student (partial) |
| DELETE | `/api/students/{id}` | Delete a specific student |

---

## 1. GET - Get All Students

**Endpoint:** `GET /api/students`

**Description:** Retrieve all students from the database

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
2. URL: `http://localhost:8080/api/students`
3. Headers: Content-Type: application/json
4. Body: None

---

## 2. GET - Get Specific Student by ID

**Endpoint:** `GET /api/students/{id}`

**Description:** Retrieve a specific student by their ID

**Headers:**
```
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): Student ID

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
2. URL: `http://localhost:8080/api/students/1`
3. Headers: Content-Type: application/json
4. Body: None

---

## 3. POST - Create a New Student

**Endpoint:** `POST /api/students`

**Description:** Create a new student record

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
2. URL: `http://localhost:8080/api/students`
3. Headers: Content-Type: application/json
4. Body: raw (JSON)

---

## 4. PATCH - Update Specific Student (Partial Update)

**Endpoint:** `PATCH /api/students/{id}`

**Description:** Update specific fields of an existing student

**Headers:**
```
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): Student ID

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
2. URL: `http://localhost:8080/api/students/1`
3. Headers: Content-Type: application/json
4. Body: raw (JSON)

---

## 5. DELETE - Delete Specific Student

**Endpoint:** `DELETE /api/students/{id}`

**Description:** Delete a specific student by their ID

**Headers:**
```
Content-Type: application/json
```

**Path Parameters:**
- `id` (Long): Student ID

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
2. URL: `http://localhost:8080/api/students/1`
3. Headers: Content-Type: application/json
4. Body: None

---

## Additional Utility Endpoints

### Search Students by Name
**Endpoint:** `GET /api/students/search?name={name}`

**Example:** `GET /api/students/search?name=John`

### Get Student by Roll Number
**Endpoint:** `GET /api/students/rollno/{rollNo}`

**Example:** `GET /api/students/rollno/12345`

### Get Students by Branch
**Endpoint:** `GET /api/students/branch/{branch}`

**Example:** `GET /api/students/branch/Computer Science`

### Get Students by Course
**Endpoint:** `GET /api/students/course/{course}`

**Example:** `GET /api/students/course/B.Tech`

---

## Complete Testing Workflow

### 1. Test Complete CRUD Operations

1. **Create a Student (POST)**
   ```
   POST /api/students
   Body: {"name": "Test Student", "rollNo": 99999, "branch": "Test Branch", "course": "Test Course"}
   ```

2. **Get All Students (GET)**
   ```
   GET /api/students
   ```

3. **Get Specific Student (GET)**
   ```
   GET /api/students/{id}
   ```

4. **Update Student (PATCH)**
   ```
   PATCH /api/students/{id}
   Body: {"name": "Updated Test Student"}
   ```

5. **Delete Student (DELETE)**
   ```
   DELETE /api/students/{id}
   ```

### 2. Test Error Scenarios

1. **Get Non-existent Student**
   ```
   GET /api/students/999999
   ```

2. **Create Student with Invalid Data**
   ```
   POST /api/students
   Body: {"name": "", "rollNo": -1}
   ```

3. **Create Student with Duplicate Roll Number**
   ```
   POST /api/students
   Body: {"name": "Duplicate", "rollNo": 12345, "branch": "Test", "course": "Test"}
   ```

4. **Update Non-existent Student**
   ```
   PATCH /api/students/999999
   Body: {"name": "Test"}
   ```

5. **Delete Non-existent Student**
   ```
   DELETE /api/students/999999
   ```

---

## Postman Collection Import

You can create a Postman collection with the following structure:

```
Student Management API
├── Core CRUD Operations
│   ├── GET - Get All Students
│   ├── GET - Get Student by ID
│   ├── POST - Create Student
│   ├── PATCH - Update Student
│   └── DELETE - Delete Student
├── Utility Endpoints
│   ├── GET - Search by Name
│   ├── GET - Get by Roll Number
│   ├── GET - Get by Branch
│   └── GET - Get by Course
└── Error Testing
    ├── GET - Non-existent Student
    ├── POST - Invalid Data
    ├── POST - Duplicate Roll Number
    ├── PATCH - Non-existent Student
    └── DELETE - Non-existent Student
```

---

## Environment Variables (Optional)

Set up environment variables in Postman:

- `baseUrl`: `http://localhost:8080`
- `apiPath`: `/api/students`

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