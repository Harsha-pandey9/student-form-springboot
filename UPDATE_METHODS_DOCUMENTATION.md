# Student Update Methods Documentation

This document explains how to use the PUT and PATCH methods to update existing student information in the Student Form application.

## Overview

The application now supports two types of update operations:

1. **PUT** - Full update (replaces entire resource)
2. **PATCH** - Partial update (updates only specified fields)

## API Endpoints

### 1. PUT Method - Full Update

**Endpoint:** `PUT /api/students/{id}`

**Description:** Updates all fields of an existing student. All required fields must be provided.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body Example:**
```json
{
    "name": "John Doe Updated",
    "rollNo": 12345,
    "branch": "Computer Science",
    "course": "B.Tech"
}
```

**Response Example (Success - 200 OK):**
```json
{
    "id": 1,
    "name": "John Doe Updated",
    "rollNo": 12345,
    "branch": "Computer Science",
    "course": "B.Tech"
}
```

**Response Example (Error - 400 Bad Request):**
```json
{
    "error": "Student with roll number 12345 already exists"
}
```

### 2. PATCH Method - Partial Update

**Endpoint:** `PATCH /api/students/{id}`

**Description:** Updates only the specified fields of an existing student. You can send only the fields you want to update.

**Request Headers:**
```
Content-Type: application/json
```

**Request Body Examples:**

**Update only name:**
```json
{
    "name": "Jane Smith"
}
```

**Update only branch and course:**
```json
{
    "branch": "Electrical Engineering",
    "course": "M.Tech"
}
```

**Update multiple fields:**
```json
{
    "name": "Updated Name",
    "rollNo": 54321,
    "branch": "Mechanical Engineering"
}
```

**Response Example (Success - 200 OK):**
```json
{
    "id": 1,
    "name": "Jane Smith",
    "rollNo": 12345,
    "branch": "Computer Science",
    "course": "B.Tech"
}
```

## Key Differences

| Aspect | PUT | PATCH |
|--------|-----|-------|
| **Purpose** | Full resource replacement | Partial resource update |
| **Required Fields** | All fields must be provided | Only fields to be updated |
| **Validation** | Full validation applied | Validation only on provided fields |
| **Use Case** | When you want to replace entire record | When you want to update specific fields |

## Validation Rules

### PUT Method Validation:
- All fields are validated using `@Valid` annotation
- `name`: Cannot be blank
- `rollNo`: Cannot be null, must be positive
- `branch`: Cannot be blank
- `course`: Cannot be blank
- `rollNo`: Must be unique (if changed)

### PATCH Method Validation:
- Only provided fields are validated
- Empty or null fields are ignored (except rollNo which can be null but if provided must be positive)
- `rollNo`: Must be unique (if provided and changed)

## Usage Examples

### Using cURL

**PUT Request:**
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe Updated",
    "rollNo": 12345,
    "branch": "Computer Science",
    "course": "B.Tech"
  }'
```

**PATCH Request:**
```bash
curl -X PATCH http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith"
  }'
```

### Using JavaScript (Fetch API)

**PUT Request:**
```javascript
fetch('/api/students/1', {
    method: 'PUT',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({
        name: 'John Doe Updated',
        rollNo: 12345,
        branch: 'Computer Science',
        course: 'B.Tech'
    })
})
.then(response => response.json())
.then(data => console.log(data));
```

**PATCH Request:**
```javascript
fetch('/api/students/1', {
    method: 'PATCH',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify({
        name: 'Jane Smith'
    })
})
.then(response => response.json())
.then(data => console.log(data));
```

## Error Handling

Both methods return appropriate HTTP status codes:

- **200 OK**: Update successful
- **400 Bad Request**: Validation error or business logic error
- **404 Not Found**: Student with given ID not found

Common error scenarios:
1. Student not found
2. Roll number already exists (when updating rollNo)
3. Validation errors (for PUT method)
4. Invalid data format

## Best Practices

1. **Use PUT when:**
   - You want to replace the entire student record
   - You have all the required information
   - You want full validation

2. **Use PATCH when:**
   - You want to update only specific fields
   - You're implementing incremental updates
   - You want to minimize data transfer

3. **Always handle errors appropriately in your client application**

4. **Validate data on the client side before sending requests**

## Testing the Endpoints

You can test these endpoints using:
- Postman
- cURL commands
- Browser developer tools
- Any REST client

Make sure your Spring Boot application is running on the configured port (default: 8080) before testing.