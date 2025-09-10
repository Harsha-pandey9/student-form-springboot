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

// Web form controller for Thymeleaf templates
@Controller
public class StudentFormController {
    
    @Autowired
    private StudentService studentService;
    
    // Redirect to new REST API structure
    @GetMapping({"/", "/studentForm"})
    public String redirectToUsers() {
        return "redirect:/web/users";
    }
    
    @PostMapping("/studentForm")
    public String processForm(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "studentForm";
        }
        
        try {
            Student savedStudent = studentService.saveStudent(student);
            model.addAttribute("student", savedStudent);
            model.addAttribute("message", "Student saved successfully!");
            return "studentResult"; // template name
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "studentForm";
        }
    }
    
    // Redirect to new REST API structure
    @GetMapping("/students")
    public String redirectStudentsToUsers() {
        return "redirect:/web/users";
    }
    
    // Show find student by ID form
    @GetMapping("/findStudent")
    public String showFindStudentForm() {
        return "findStudent";
    }
    
    // Find specific student by ID
    @PostMapping("/findStudent")
    public String findStudentById(@RequestParam Long id, Model model) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                model.addAttribute("student", student.get());
                model.addAttribute("message", "Student found successfully!");
                return "studentDetails";
            } else {
                model.addAttribute("error", "Student not found with ID: " + id);
                return "findStudent";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error finding student: " + e.getMessage());
            return "findStudent";
        }
    }
    
    // Show delete student form
    @GetMapping("/deleteStudent")
    public String showDeleteStudentForm() {
        return "deleteStudent";
    }
    
    // Delete student by ID
    @PostMapping("/deleteStudent")
    public String deleteStudentById(@RequestParam Long id, Model model) {
        try {
            // First check if student exists
            Optional<Student> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                Student studentToDelete = student.get();
                studentService.deleteStudent(id);
                model.addAttribute("message", "Student '" + studentToDelete.getName() + "' (ID: " + id + ") deleted successfully!");
                model.addAttribute("deletedStudent", studentToDelete);
                return "deleteSuccess";
            } else {
                model.addAttribute("error", "Student not found with ID: " + id);
                return "deleteStudent";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error deleting student: " + e.getMessage());
            return "deleteStudent";
        }
    }
    
    // Show student details with edit/delete options
    @GetMapping("/student/{id}")
    public String showStudentDetails(@PathVariable Long id, Model model) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                model.addAttribute("student", student.get());
                return "studentDetails";
            } else {
                model.addAttribute("error", "Student not found with ID: " + id);
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading student: " + e.getMessage());
            return "error";
        }
    }
    
    // Show update student form
    @GetMapping("/updateStudent/{id}")
    public String showUpdateStudentForm(@PathVariable Long id, Model model) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                model.addAttribute("student", student.get());
                model.addAttribute("isUpdate", true);
                return "updateStudent";
            } else {
                model.addAttribute("error", "Student not found with ID: " + id);
                return "error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error loading student: " + e.getMessage());
            return "error";
        }
    }
    
    // Process update student form
    @PostMapping("/updateStudent/{id}")
    public String processUpdateStudent(@PathVariable Long id, 
                                     @Valid @ModelAttribute("student") Student student, 
                                     BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isUpdate", true);
            return "updateStudent";
        }
        
        try {
            // Set the ID to ensure we're updating the correct student
            student.setId(id);
            Student updatedStudent = studentService.updateStudent(id, student);
            model.addAttribute("student", updatedStudent);
            model.addAttribute("message", "Student updated successfully!");
            return "updateSuccess";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("isUpdate", true);
            return "updateStudent";
        }
    }
    
    // Show find student for update form
    @GetMapping("/findStudentToUpdate")
    public String showFindStudentToUpdateForm() {
        return "findStudentToUpdate";
    }
    
    // Find student for update by ID
    @PostMapping("/findStudentToUpdate")
    public String findStudentToUpdate(@RequestParam Long id, Model model) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                return "redirect:/updateStudent/" + id;
            } else {
                model.addAttribute("error", "Student not found with ID: " + id);
                return "findStudentToUpdate";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Error finding student: " + e.getMessage());
            return "findStudentToUpdate";
        }
    }
}