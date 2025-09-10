package com.example.Student_form.exception;

/**
 * Custom exception thrown when attempting to create/update a student with a duplicate roll number
 */
public class DuplicateRollNumberException extends RuntimeException {
    
    private final Integer rollNumber;
    
    public DuplicateRollNumberException(Integer rollNumber) {
        super("Student with roll number " + rollNumber + " already exists");
        this.rollNumber = rollNumber;
    }
    
    public DuplicateRollNumberException(String message) {
        super(message);
        this.rollNumber = null;
    }
    
    public DuplicateRollNumberException(String message, Throwable cause) {
        super(message, cause);
        this.rollNumber = null;
    }
    
    public Integer getRollNumber() {
        return rollNumber;
    }
}