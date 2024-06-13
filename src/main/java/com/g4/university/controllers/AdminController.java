package com.g4.university.controllers;


import com.g4.university.dto.StudentDto;
import com.g4.university.services.admin.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping("/student")
    public ResponseEntity<?> addStudent (@RequestBody StudentDto studentDto){
      StudentDto createdStudentDto =  adminService.postStudent(studentDto);
      if (createdStudentDto == null)
          return new ResponseEntity<>("probleme!", HttpStatus.BAD_REQUEST);
      return ResponseEntity.status(HttpStatus.CREATED).body(createdStudentDto);
    }
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>> getAllStudents(){
        List<StudentDto> allStudents = adminService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    @DeleteMapping("/student/{studentId}")
    public ResponseEntity<Void> deletestudent(@PathVariable Long studentId  ){
        adminService.deleteStudent(studentId);
        return ResponseEntity.noContent().build();
    }
}
