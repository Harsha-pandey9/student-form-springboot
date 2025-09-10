# Student Management System

A simple Spring Boot application for managing student information with both web interface and REST API.

## 🚀 Quick Start

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🌐 Access Points

### Web Interface (HTML)
- **Home Page**: `http://localhost:8080/web/users`
- **Create User**: `http://localhost:8080/web/users/new`
- **View All Users**: `http://localhost:8080/web/users`

### REST API (JSON)
- **Base URL**: `http://localhost:8080/api/users`
- **Get All Users**: `GET /api/users`
- **Get User by ID**: `GET /api/users/{id}`
- **Create User**: `POST /api/users`
- **Update User**: `PATCH /api/users/{id}`
- **Delete User**: `DELETE /api/users/{id}`

## 📋 Features

- ✅ **CRUD Operations**: Create, Read, Update, Delete users
- ✅ **Web Interface**: User-friendly HTML forms
- ✅ **REST API**: JSON endpoints for external integration
- ✅ **Data Validation**: Input validation and error handling
- ✅ **Exception Handling**: Global error management
- ✅ **Database**: H2 in-memory database (auto-configured)

## 🧪 Testing

### Web Interface
1. Open browser: `http://localhost:8080/web/users`
2. Use the web forms to manage users

### API Testing (Postman)
Import the provided Postman collection: `User_Management_API.postman_collection.json`

## 📁 Project Structure

```
src/main/java/com/example/Student_form/
├── controller/          # REST controllers
├── model/              # Entity classes
├── repository/         # Data access layer
├── service/           # Business logic
└── exception/         # Custom exceptions

src/main/resources/
├── templates/users/   # HTML templates
└── application.properties
```

## 🛠️ Technology Stack

- **Spring Boot 3.x**
- **Spring Data JPA**
- **H2 Database**
- **Thymeleaf** (for web templates)
- **Maven** (build tool)

## 📝 API Examples

### Create User
```bash
POST /api/users
Content-Type: application/json

{
    "name": "John Doe",
    "rollNo": 12345,
    "branch": "Computer Science",
    "course": "B.Tech"
}
```

### Update User
```bash
PATCH /api/users/1
Content-Type: application/json

{
    "name": "John Doe Updated"
}
```

That's it! Your Student Management System is ready to use. 🎉