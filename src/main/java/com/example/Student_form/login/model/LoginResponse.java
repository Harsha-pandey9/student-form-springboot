package com.example.Student_form.login.model;

/**
 * DTO for login responses
 */
public class LoginResponse {
    
    private boolean success;
    private String message;
    private String studentName;
    private Integer rollNo;
    
    // Default constructor
    public LoginResponse() {}
    
    // Constructor for success response
    public LoginResponse(boolean success, String message, String studentName, Integer rollNo) {
        this.success = success;
        this.message = message;
        this.studentName = studentName;
        this.rollNo = rollNo;
    }
    
    // Constructor for error response
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    
    public Integer getRollNo() { return rollNo; }
    public void setRollNo(Integer rollNo) { this.rollNo = rollNo; }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", studentName='" + studentName + '\'' +
                ", rollNo=" + rollNo +
                '}';
    }
}