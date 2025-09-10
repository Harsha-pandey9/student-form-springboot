package com.example.Student_form.login.service;

import com.example.Student_form.login.model.LoginRequest;
import com.example.Student_form.login.model.LoginResponse;
import com.example.Student_form.model.Student;
import com.example.Student_form.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for handling login operations
 * This service works with existing Student model without modifications
 */
@Service
public class LoginService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    /**
     * Authenticate student using roll number
     * For now, we'll use a simple authentication based on roll number existence
     * In a real application, you would check against a password
     */
    public LoginResponse authenticateStudent(LoginRequest loginRequest) {
        try {
            // Find student by roll number
            Optional<Student> studentOpt = studentRepository.findByRollNo(loginRequest.getRollNo());
            
            if (studentOpt.isEmpty()) {
                return new LoginResponse(false, "Student not found with roll number: " + loginRequest.getRollNo());
            }
            
            Student student = studentOpt.get();
            
            // For demonstration, we'll accept any password for existing students
            // In a real application, you would verify the password against a stored hash
            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return new LoginResponse(false, "Password is required");
            }
            
            // Simple password validation (you can enhance this)
            if (loginRequest.getPassword().length() < 4) {
                return new LoginResponse(false, "Password must be at least 4 characters long");
            }
            
            // For demo purposes, accept any password that's not "wrong"
            if ("wrong".equals(loginRequest.getPassword().toLowerCase())) {
                return new LoginResponse(false, "Invalid password");
            }
            
            // Successful authentication
            return new LoginResponse(true, "Login successful", student.getName(), student.getRollNo());
            
        } catch (Exception e) {
            return new LoginResponse(false, "Login failed: " + e.getMessage());
        }
    }
    
    /**
     * Check if student exists by roll number
     */
    public boolean studentExists(Integer rollNo) {
        return studentRepository.existsByRollNo(rollNo);
    }
    
    /**
     * Get student information by roll number
     */
    public Optional<Student> getStudentByRollNo(Integer rollNo) {
        return studentRepository.findByRollNo(rollNo);
    }
}