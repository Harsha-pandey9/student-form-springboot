package com.example.Student_form.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Simple redirect controller for legacy URLs
 */
@Controller
public class StudentFormController {
    
    /**
     * Redirect home page and old student form URLs to new user interface
     */
    @GetMapping({"/", "/studentForm", "/students"})
    public String redirectToUsers() {
        return "redirect:/web/users";
    }
}