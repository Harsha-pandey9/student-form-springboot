package com.example.Student_form.login.controller;

import com.example.Student_form.login.model.LoginRequest;
import com.example.Student_form.login.model.LoginResponse;
import com.example.Student_form.login.service.LoginService;
import com.example.Student_form.model.Student;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for login operations
 * This controller is completely separate from existing user management
 */
@Controller
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    /**
     * Show login form
     */
    @GetMapping
    public String showLoginForm(Model model, HttpSession session) {
        // Check if user is already logged in
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        if (loggedInStudent != null) {
            return "redirect:/login/dashboard";
        }
        
        model.addAttribute("loginRequest", new LoginRequest());
        return "login/login-form";
    }
    
    /**
     * Process login
     */
    @PostMapping
    public String processLogin(@Valid @ModelAttribute("loginRequest") LoginRequest loginRequest,
                             BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "login/login-form";
        }
        
        LoginResponse response = loginService.authenticateStudent(loginRequest);
        
        if (response.isSuccess()) {
            // Store student information in session
            Optional<Student> student = loginService.getStudentByRollNo(loginRequest.getRollNo());
            if (student.isPresent()) {
                session.setAttribute("loggedInStudent", student.get());
                session.setAttribute("loginTime", System.currentTimeMillis());
            }
            
            return "redirect:/login/dashboard";
        } else {
            model.addAttribute("error", response.getMessage());
            return "login/login-form";
        }
    }
    
    /**
     * Show dashboard for logged-in student
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        
        if (loggedInStudent == null) {
            return "redirect:/login";
        }
        
        Long loginTime = (Long) session.getAttribute("loginTime");
        model.addAttribute("student", loggedInStudent);
        model.addAttribute("loginTime", loginTime);
        
        return "login/dashboard";
    }
    
    /**
     * Logout
     */
    @PostMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.removeAttribute("loggedInStudent");
        session.removeAttribute("loginTime");
        session.invalidate();
        
        model.addAttribute("message", "You have been logged out successfully.");
        model.addAttribute("loginRequest", new LoginRequest());
        return "login/login-form";
    }
    
    /**
     * Check login status (AJAX endpoint)
     */
    @GetMapping("/status")
    @ResponseBody
    public LoginResponse checkLoginStatus(HttpSession session) {
        Student loggedInStudent = (Student) session.getAttribute("loggedInStudent");
        
        if (loggedInStudent != null) {
            return new LoginResponse(true, "User is logged in", 
                                   loggedInStudent.getName(), loggedInStudent.getRollNo());
        } else {
            return new LoginResponse(false, "User is not logged in");
        }
    }
}