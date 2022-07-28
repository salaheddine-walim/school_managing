package com.example.demo.entities;

import com.example.demo.entities.dto.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cne;

    @NotNull
    @Column(unique = true)
    private String fullName;

    @NotNull
    private String gender;

    @Min(7)
    @Max(90)
    private int age;


    public static Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setCne(studentDTO.getCne());
        student.setFullName(studentDTO.getFullName());
        student.setGender(studentDTO.getSexe());
        student.setAge(studentDTO.getAge());
        return student;
    }
}
