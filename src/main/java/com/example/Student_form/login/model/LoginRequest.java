package com.example.Student_form.login.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for login requests
 */
public class LoginRequest {
    
    @NotNull(message = "Roll number is required")
    @Positive(message = "Roll number must be positive")
    private Integer rollNo;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    // Default constructor
    public LoginRequest() {}
    
    // Constructor with parameters
    public LoginRequest(Integer rollNo, String password) {
        this.rollNo = rollNo;
        this.password = password;
    }
    
    // Getters and Setters
    public Integer getRollNo() { return rollNo; }
    public void setRollNo(Integer rollNo) { this.rollNo = rollNo; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    @Override
    public String toString() {
        return "LoginRequest{" +
                "rollNo=" + rollNo +
                ", password='[PROTECTED]'" +
                '}';
    }
}