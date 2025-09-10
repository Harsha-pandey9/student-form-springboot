package com.example.Student_form.controller;

import com.example.Student_form.model.Student;
import com.example.Student_form.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * RESTful API Controller for User Management
 * 
 * API Endpoints:
 * GET    /api/users        - Get all users
 * GET    /api/users/{id}   - Get a specific user by ID
 * POST   /api/users        - Create a new user
 * PATCH  /api/users/{id}   - Update a specific user (partial update)
 * DELETE /api/users/{id}   - Delete a specific user
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Allow CORS for testing with Postman/frontend
public class StudentApiController {
    
    @Autowired
    private StudentService studentService;
    
    // ==================== CORE CRUD OPERATIONS ====================
    
    /**
     * GET /api/users
     * Get all users
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Students retrieved successfully");
            response.put("data", students);
            response.put("count", students.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error retrieving students");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * GET /api/users/{id}
     * Get a specific user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable Long id) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            Map<String, Object> response = new HashMap<>();
            
            if (student.isPresent()) {
                response.put("success", true);
                response.put("message", "Student found successfully");
                response.put("data", student.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Student not found");
                response.put("error", "No student exists with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error retrieving student");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * POST /api/users
     * Create a new user
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createStudent(@Valid @RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Student created successfully");
            response.put("data", savedStudent);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error creating student");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Internal server error");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * PATCH /api/users/{id}
     * Update a specific user (partial update)
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        try {
            Student updatedStudent = studentService.partialUpdateStudent(id, studentDetails);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Student updated successfully");
            response.put("data", updatedStudent);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error updating student");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Internal server error");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * DELETE /api/users/{id}
     * Delete a specific user
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable Long id) {
        try {
            // First check if student exists to provide better response
            Optional<Student> existingStudent = studentService.getStudentById(id);
            if (!existingStudent.isPresent()) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("message", "Student not found");
                error.put("error", "No student exists with ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
            
            studentService.deleteStudent(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Student deleted successfully");
            response.put("deletedStudent", existingStudent.get());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error deleting student");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Internal server error");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // ==================== ADDITIONAL UTILITY ENDPOINTS ====================
    
    /**
     * GET /api/users/search?name={name}
     * Search users by name
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchStudentsByName(@RequestParam String name) {
        try {
            List<Student> students = studentService.searchStudentsByName(name);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Search completed successfully");
            response.put("data", students);
            response.put("count", students.size());
            response.put("searchTerm", name);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error searching students");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * GET /api/users/rollno/{rollNo}
     * Get user by roll number
     */
    @GetMapping("/rollno/{rollNo}")
    public ResponseEntity<Map<String, Object>> getStudentByRollNo(@PathVariable Integer rollNo) {
        try {
            Optional<Student> student = studentService.getStudentByRollNo(rollNo);
            Map<String, Object> response = new HashMap<>();
            
            if (student.isPresent()) {
                response.put("success", true);
                response.put("message", "Student found successfully");
                response.put("data", student.get());
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "Student not found");
                response.put("error", "No student exists with roll number: " + rollNo);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error retrieving student");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * GET /api/users/branch/{branch}
     * Get users by branch
     */
    @GetMapping("/branch/{branch}")
    public ResponseEntity<Map<String, Object>> getStudentsByBranch(@PathVariable String branch) {
        try {
            List<Student> students = studentService.getStudentsByBranch(branch);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Students retrieved successfully");
            response.put("data", students);
            response.put("count", students.size());
            response.put("branch", branch);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error retrieving students");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    /**
     * GET /api/users/course/{course}
     * Get users by course
     */
    @GetMapping("/course/{course}")
    public ResponseEntity<Map<String, Object>> getStudentsByCourse(@PathVariable String course) {
        try {
            List<Student> students = studentService.getStudentsByCourse(course);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Students retrieved successfully");
            response.put("data", students);
            response.put("count", students.size());
            response.put("course", course);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Error retrieving students");
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    // Exception handling is now managed by GlobalExceptionHandler
}