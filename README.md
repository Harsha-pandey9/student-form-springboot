# Student Management System - Spring Boot Application

This is a Spring Boot application that provides a complete student management system with both web interface and REST API endpoints. It includes 5 main routes for CRUD operations on student data stored in MySQL database.

## Features

- **Web Interface**: HTML forms for student registration and viewing
- **REST API**: JSON endpoints for all CRUD operations
- **MySQL Integration**: Persistent data storage
- **Validation**: Input validation with error handling
- **Responsive Design**: Clean and user-friendly interface

## Prerequisites

Before running this application, make sure you have:

1. **Java 17 or higher** installed
2. **Maven** installed
3. **MySQL Server** installed and running
4. **MySQL Workbench** or any MySQL client (optional, for database management)

## MySQL Database Setup

### Step 1: Install MySQL
1. Download and install MySQL Server from [MySQL Official Website](https://dev.mysql.com/downloads/mysql/)
2. During installation, set a root password (remember this password)
3. Start MySQL service

### Step 2: Create Database
1. Open MySQL Command Line Client or MySQL Workbench
2. Login with root user and password
3. Create a new database:
```sql
CREATE DATABASE student_db;
```

### Step 3: Configure Database Connection
The application is configured to connect to MySQL with these default settings:
- **Host**: localhost
- **Port**: 3306
- **Database**: student_db
- **Username**: root
- **Password**: password

If your MySQL setup is different, update the `src/main/resources/application.properties` file:

```properties
# Update these values according to your MySQL setup
spring.datasource.url=jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

## Running the Application

### Step 1: Clone and Navigate
```bash
cd "C:\Users\harsh\OneDrive\Desktop\SpringBoot\student form\Student-form\Student-form"
```

### Step 2: Build the Application
```bash
mvn clean install
```

### Step 3: Run the Application
```bash
mvn spring-boot:run
```

Or run the JAR file:
```bash
java -jar target/Student-form-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## 5 Main Routes (REST API)

### 1. Create Student (POST)
**Endpoint**: `POST /api/students`
**Description**: Store student data directly to MySQL database

**Request Body** (JSON):
```json
{
    "name": "John Doe",
    "rollNo": 12345,
    "branch": "Computer Science",
    "course": "B.Tech"
}
```

**cURL Example**:
```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "rollNo": 12345,
    "branch": "Computer Science",
    "course": "B.Tech"
  }'
```

### 2. Get All Students (GET)
**Endpoint**: `GET /api/students`
**Description**: Retrieve all student information

**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/students
```

### 3. Get Specific Student (GET)
**Endpoint**: `GET /api/students/{id}`
**Description**: Get specific student info by ID

**cURL Example**:
```bash
curl -X GET http://localhost:8080/api/students/1
```

### 4. Delete Student (DELETE)
**Endpoint**: `DELETE /api/students/{id}`
**Description**: Delete student information

**cURL Example**:
```bash
curl -X DELETE http://localhost:8080/api/students/1
```

### 5. Update Student (PUT)
**Endpoint**: `PUT /api/students/{id}`
**Description**: Update student information

**Request Body** (JSON):
```json
{
    "name": "John Smith",
    "rollNo": 12345,
    "branch": "Information Technology",
    "course": "B.Tech"
}
```

**cURL Example**:
```bash
curl -X PUT http://localhost:8080/api/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Smith",
    "rollNo": 12345,
    "branch": "Information Technology",
    "course": "B.Tech"
  }'
```

## Additional API Endpoints

### Get Student by Roll Number
```bash
curl -X GET http://localhost:8080/api/students/rollno/12345
```

### Get Students by Branch
```bash
curl -X GET http://localhost:8080/api/students/branch/Computer%20Science
```

### Get Students by Course
```bash
curl -X GET http://localhost:8080/api/students/course/B.Tech
```

### Search Students by Name
```bash
curl -X GET "http://localhost:8080/api/students/search?name=John"
```

## Web Interface Routes

### Student Registration Form
- **URL**: `http://localhost:8080/` or `http://localhost:8080/studentForm`
- **Description**: HTML form to register new students

### View All Students
- **URL**: `http://localhost:8080/students`
- **Description**: Display all students in a table format

## Testing the Application

### Using Web Interface
1. Open browser and go to `http://localhost:8080`
2. Fill out the student registration form
3. Submit the form to save data to MySQL
4. Click "View All Students" to see all registered students

### Using REST API (Postman/cURL)
1. **Create a student**: Use POST request with JSON data
2. **View all students**: Use GET request to `/api/students`
3. **View specific student**: Use GET request to `/api/students/{id}`
4. **Update student**: Use PUT request with updated JSON data
5. **Delete student**: Use DELETE request to `/api/students/{id}`

## Database Schema

The application automatically creates a `students` table with the following structure:

```sql
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    roll_no INT NOT NULL UNIQUE,
    branch VARCHAR(255) NOT NULL,
    course VARCHAR(255) NOT NULL
);
```

## Error Handling

The application includes comprehensive error handling:
- **Validation errors**: Returns field-specific error messages
- **Duplicate roll numbers**: Prevents duplicate entries
- **Not found errors**: Returns appropriate 404 responses
- **Database errors**: Handles connection and constraint violations

## Project Structure

```
src/
├── main/
│   ├── java/com/example/Student_form/
│   │   ├── controller/
│   │   │   └── StudentController.java      # Web and REST controllers
│   │   ├── model/
│   │   │   └── Student.java                # JPA Entity
│   │   ├── repository/
│   │   │   └── StudentRepository.java      # Data access layer
│   │   ├── service/
│   │   │   └── StudentService.java         # Business logic
│   │   └── StudentFormApplication.java     # Main application class
│   └── resources/
│       ├── templates/                      # Thymeleaf templates
│       │   ├── studentForm.html
│       │   ├── studentResult.html
│       │   └── studentList.html
│       └── application.properties          # Configuration
└── pom.xml                                # Maven dependencies
```

## Troubleshooting

### Common Issues

1. **MySQL Connection Error**:
   - Ensure MySQL server is running
   - Check username/password in application.properties
   - Verify database `student_db` exists

2. **Port Already in Use**:
   - Change server port in application.properties: `server.port=8081`

3. **Build Errors**:
   - Ensure Java 17+ is installed
   - Run `mvn clean install` to resolve dependencies

4. **Database Table Not Created**:
   - Check `spring.jpa.hibernate.ddl-auto=update` in application.properties
   - Verify MySQL user has CREATE privileges

### Logs
Check application logs for detailed error information. The application logs SQL queries when `spring.jpa.show-sql=true` is enabled.

## Next Steps

You can extend this application by adding:
- Authentication and authorization
- File upload for student photos
- Advanced search and filtering
- Pagination for large datasets
- Email notifications
- Export functionality (PDF, Excel)

## Support

If you encounter any issues:
1. Check the console logs for error messages
2. Verify MySQL connection and database setup
3. Ensure all dependencies are properly installed
4. Check that the correct Java version is being used