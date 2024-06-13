package com.g4.university.services.admin;

import com.g4.university.dto.StudentDto;
import com.g4.university.entities.User;
import com.g4.university.enums.UserRole;
import com.g4.university.repositories.UserRepository;
import com.g4.university.services.admin.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;
    public AdminServiceImpl(UserRepository userRepository){
        this.userRepository= userRepository;
    }


    @PostConstruct
    public void createAdminAccount() {
        User adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if (adminAccount == null) {

            User  admin= new User();
            admin.setEmail("admin@esprit.tn");
            admin.setName("admin");
            admin.setPassword("admin");
            admin.setRole(UserRole.ADMIN);
            userRepository.save(admin);
        }
    }

    @Override
    public StudentDto postStudent(StudentDto studentDto) {
        Optional<User> optionalUser = userRepository.findFirstByEmail(studentDto.getEmail());
        if (optionalUser.isEmpty()){
            User user = new User();
            BeanUtils.copyProperties(studentDto, user);
            user.setPassword(studentDto.getPassword());
            user.setRole(UserRole.STUDENT);
            User createdUser = userRepository.save(user);
            StudentDto createdStudentDto = new StudentDto();
            createdStudentDto.setId(createdUser.getId());
            createdStudentDto.setEmail(createdUser.getEmail());
            return createdStudentDto;

        }
        return null;
    }

    @Override
    public List<StudentDto> getAllStudents() {
        return userRepository.findAllByRole(UserRole.STUDENT).stream().map(User::getStudentDto).collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long studentId) {
        userRepository.deleteById(studentId);
    }
}
