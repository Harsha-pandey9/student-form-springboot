package com.example.Student_form.service;

import com.example.Student_form.exception.DuplicateRollNumberException;
import com.example.Student_form.exception.InvalidStudentDataException;
import com.example.Student_form.exception.StudentNotFoundException;
import com.example.Student_form.model.Student;
import com.example.Student_form.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    // Save a new student
    public Student saveStudent(Student student) {
        // Validate student data
        validateStudentData(student);
        
        // Check if roll number already exists
        if (studentRepository.existsByRollNo(student.getRollNo())) {
            throw new DuplicateRollNumberException(student.getRollNo());
        }
        return studentRepository.save(student);
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    // Get student by roll number
    public Optional<Student> getStudentByRollNo(Integer rollNo) {
        return studentRepository.findByRollNo(rollNo);
    }
    
    // Update student
    public Student updateStudent(Long id, Student studentDetails) {
        // Validate student data
        validateStudentData(studentDetails);
        
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        
        // Check if roll number is being changed and if new roll number already exists
        if (!student.getRollNo().equals(studentDetails.getRollNo()) && 
            studentRepository.existsByRollNo(studentDetails.getRollNo())) {
            throw new DuplicateRollNumberException(studentDetails.getRollNo());
        }
        
        student.setName(studentDetails.getName());
        student.setRollNo(studentDetails.getRollNo());
        student.setBranch(studentDetails.getBranch());
        student.setCourse(studentDetails.getCourse());
        
        return studentRepository.save(student);
    }
    
    // Delete student by ID
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(student);
    }
    
    // Get students by branch
    public List<Student> getStudentsByBranch(String branch) {
        return studentRepository.findByBranch(branch);
    }
    
    // Get students by course
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course);
    }
    
    // Search students by name
    public List<Student> searchStudentsByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
    
    // Partial update student (PATCH)
    public Student partialUpdateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        
        // Only update fields that are not null
        if (studentDetails.getName() != null && !studentDetails.getName().trim().isEmpty()) {
            student.setName(studentDetails.getName());
        }
        
        if (studentDetails.getRollNo() != null) {
            // Validate roll number
            if (studentDetails.getRollNo() <= 0) {
                throw new InvalidStudentDataException("rollNo", studentDetails.getRollNo(), "Roll number must be positive");
            }
            
            // Check if roll number is being changed and if new roll number already exists
            if (!student.getRollNo().equals(studentDetails.getRollNo()) && 
                studentRepository.existsByRollNo(studentDetails.getRollNo())) {
                throw new DuplicateRollNumberException(studentDetails.getRollNo());
            }
            student.setRollNo(studentDetails.getRollNo());
        }
        
        if (studentDetails.getBranch() != null && !studentDetails.getBranch().trim().isEmpty()) {
            student.setBranch(studentDetails.getBranch());
        }
        
        if (studentDetails.getCourse() != null && !studentDetails.getCourse().trim().isEmpty()) {
            student.setCourse(studentDetails.getCourse());
        }
        
        return studentRepository.save(student);
    }
    
    // Validate student data
    private void validateStudentData(Student student) {
        if (student == null) {
            throw new InvalidStudentDataException("Student data cannot be null");
        }
        
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            throw new InvalidStudentDataException("name", student.getName(), "Name cannot be empty");
        }
        
        if (student.getRollNo() == null || student.getRollNo() <= 0) {
            throw new InvalidStudentDataException("rollNo", student.getRollNo(), "Roll number must be positive");
        }
        
        if (student.getBranch() == null || student.getBranch().trim().isEmpty()) {
            throw new InvalidStudentDataException("branch", student.getBranch(), "Branch cannot be empty");
        }
        
        if (student.getCourse() == null || student.getCourse().trim().isEmpty()) {
            throw new InvalidStudentDataException("course", student.getCourse(), "Course cannot be empty");
        }
    }
}