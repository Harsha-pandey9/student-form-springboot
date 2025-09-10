package com.example.Student_form.controller;

import com.example.Student_form.model.Student;
import com.example.Student_form.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Web Controller for User Management following REST API structure
 * 
 * Web Endpoints (HTML responses):
 * GET    /web/users           - Get all users (HTML page)
 * GET    /web/users/{id}      - Get a specific user (HTML page)
 * GET    /web/users/new       - Show create user form (HTML page)
 * POST   /web/users           - Create a user (form submission)
 * GET    /web/users/{id}/edit - Show update user form (HTML page)
 * PATCH  /web/users/{id}      - Update a specific user (form submission)
 * DELETE /web/users/{id}      - Delete a specific user (form submission)
 */
@Controller
@RequestMapping("/web/users")
public class UserWebController {
    
    @Autowired
    private StudentService studentService;
    
    // ==================== CORE CRUD OPERATIONS ====================
    
    /**
     * GET /web/users
     * Get all users (HTML page)
     */
    @GetMapping
    public String getAllUsers(Model model) {
        try {
            List<Student> users = studentService.getAllStudents();
            model.addAttribute("users", users);
            model.addAttribute("message", "Users retrieved successfully");
            return "users/list"; // users/list.html template
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving users: " + e.getMessage());
            return "users/list";
        }
    }

    /**
     * GET /web/users/{id}
     * Get a specific user (HTML page)
     */
    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        try {
            Optional<Student> user = studentService.getStudentById(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                model.addAttribute("message", "User found successfully");
                return "users/details"; // users/details.html template
            } else {
                model.addAttribute("error", "User not found with ID: " + id);
                return "users/error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving user: " + e.getMessage());
            return "users/error";
        }
    }

    /**
     * GET /web/users/new
     * Show create user form (HTML page)
     */
    @GetMapping("/new")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new Student());
        model.addAttribute("isEdit", false);
        return "users/form"; // users/form.html template
    }

    @PostMapping
    public String createUser(@Valid @ModelAttribute("user") Student user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "users/form";
        }
        
        try {
            Student savedUser = studentService.saveStudent(user);
            model.addAttribute("user", savedUser);
            model.addAttribute("message", "User created successfully!");
            return "users/success"; // users/success.html template
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("isEdit", false);
            return "users/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showUpdateUserForm(@PathVariable Long id, Model model) {
        try {
            Optional<Student> user = studentService.getStudentById(id);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                model.addAttribute("isEdit", true);
                return "users/form"; // users/form.html template
            } else {
                model.addAttribute("error", "User not found with ID: " + id);
                return "users/error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading user: " + e.getMessage());
            return "users/error";
        }
    }

    @PatchMapping("/{id}")
    public String updateUser(@PathVariable Long id, 
                           @Valid @ModelAttribute("user") Student user, 
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "users/form";
        }
        
        try {
            // Set the ID to ensure we're updating the correct user
            user.setId(id);
            Student updatedUser = studentService.updateStudent(id, user);
            model.addAttribute("user", updatedUser);
            model.addAttribute("message", "User updated successfully!");
            return "users/success"; // users/success.html template
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("isEdit", true);
            return "users/form";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        try {
            // First check if user exists
            Optional<Student> user = studentService.getStudentById(id);
            if (user.isPresent()) {
                Student userToDelete = user.get();
                studentService.deleteStudent(id);
                model.addAttribute("message", "User '" + userToDelete.getName() + "' (ID: " + id + ") deleted successfully!");
                model.addAttribute("deletedUser", userToDelete);
                return "users/deleted"; // users/deleted.html template
            } else {
                model.addAttribute("error", "User not found with ID: " + id);
                return "users/error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting user: " + e.getMessage());
            return "users/error";
        }
    }

    @GetMapping("/search")
    public String showSearchForm(@RequestParam(required = false) String name, Model model) {
        if (name != null && !name.trim().isEmpty()) {
            try {
                List<Student> users = studentService.searchStudentsByName(name);
                model.addAttribute("users", users);
                model.addAttribute("searchTerm", name);
                model.addAttribute("message", "Search completed for: " + name);
                return "users/search-results"; // users/search-results.html template
            } catch (Exception e) {
                model.addAttribute("error", "Error searching users: " + e.getMessage());
                return "users/search";
            }
        }
        return "users/search"; // users/search.html template
    }
    
    /**
     * GET /users/rollno/{rollNo}
     * Get user by roll number (HTML page)
     */
    @GetMapping("/rollno/{rollNo}")
    public String getUserByRollNo(@PathVariable Integer rollNo, Model model) {
        try {
            Optional<Student> user = studentService.getStudentByRollNo(rollNo);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                model.addAttribute("message", "User found with roll number: " + rollNo);
                return "users/details"; // users/details.html template
            } else {
                model.addAttribute("error", "User not found with roll number: " + rollNo);
                return "users/error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving user: " + e.getMessage());
            return "users/error";
        }
    }
    
    /**
     * GET /users/branch/{branch}
     * Get users by branch (HTML page)
     */
    @GetMapping("/branch/{branch}")
    public String getUsersByBranch(@PathVariable String branch, Model model) {
        try {
            List<Student> users = studentService.getStudentsByBranch(branch);
            model.addAttribute("users", users);
            model.addAttribute("branch", branch);
            model.addAttribute("message", "Users retrieved for branch: " + branch);
            return "users/list"; // users/list.html template
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving users: " + e.getMessage());
            return "users/list";
        }
    }
    
    /**
     * GET /users/course/{course}
     * Get users by course (HTML page)
     */
    @GetMapping("/course/{course}")
    public String getUsersByCourse(@PathVariable String course, Model model) {
        try {
            List<Student> users = studentService.getStudentsByCourse(course);
            model.addAttribute("users", users);
            model.addAttribute("course", course);
            model.addAttribute("message", "Users retrieved for course: " + course);
            return "users/list"; // users/list.html template
        } catch (Exception e) {
            model.addAttribute("error", "Error retrieving users: " + e.getMessage());
            return "users/list";
        }
    }
}