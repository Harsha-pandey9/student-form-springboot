package com.example.Student_form.exception;

/**
 * Custom exception thrown when student data is invalid
 */
public class InvalidStudentDataException extends RuntimeException {
    
    private final String field;
    private final Object value;
    
    public InvalidStudentDataException(String message) {
        super(message);
        this.field = null;
        this.value = null;
    }
    
    public InvalidStudentDataException(String field, Object value, String message) {
        super(message);
        this.field = field;
        this.value = value;
    }
    
    public InvalidStudentDataException(String message, Throwable cause) {
        super(message, cause);
        this.field = null;
        this.value = null;
    }
    
    public String getField() {
        return field;
    }
    
    public Object getValue() {
        return value;
    }
}