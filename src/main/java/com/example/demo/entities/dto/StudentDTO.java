package com.example.demo.entities.dto;

import com.example.demo.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    private String cne;
    private String fullName;
    private String sexe;
    private int age;


    public static StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setCne(student.getCne());
        studentDTO.setFullName(student.getFullName());
        studentDTO.setAge(student.getAge());
        studentDTO.setSexe(student.getGender());
        return studentDTO;
    }

    public static Page<StudentDTO> convertToDTO(Page<Student> studentsList) {
        return studentsList.map(StudentDTO::convertToDTO);
    }
}
