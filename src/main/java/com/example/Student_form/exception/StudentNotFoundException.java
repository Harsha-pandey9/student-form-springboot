package com.example.Student_form.exception;

/**
 * Custom exception thrown when a student is not found
 */
public class StudentNotFoundException extends RuntimeException {
    
    private final Long studentId;
    
    public StudentNotFoundException(Long studentId) {
        super("Student not found with ID: " + studentId);
        this.studentId = studentId;
    }
    
    public StudentNotFoundException(String message) {
        super(message);
        this.studentId = null;
    }
    
    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.studentId = null;
    }
    
    public Long getStudentId() {
        return studentId;
    }
}