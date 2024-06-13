package com.g4.university.services.admin;


import com.g4.university.dto.StudentDto;

import java.util.List;


public interface AdminService {
    StudentDto postStudent(StudentDto studentDto);

    List<StudentDto> getAllStudents();

    void deleteStudent(Long studentId);
}
