package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.entities.dto.StudentDTO;
import com.example.demo.repos.StudentRepo;
import com.example.demo.services.exceptions.ObjectNotFoundException;
import com.example.demo.services.filters.StudentSpec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StudentService {
    @Autowired
    StudentRepo studentRepo;

    public Page<StudentDTO> findAll(Pageable page) {
        try {
            return StudentDTO.convertToDTO(studentRepo.findAll(page));

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Page and size must be greater than 0");
        }
    }

    public Boolean findByAge(int age) {
        return studentRepo.findByAge(age);
    }

    public StudentDTO findById(long id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (!optionalStudent.isPresent()) {
            log.error("Student with id " + id + " not found");
            throw new ObjectNotFoundException("Student with id " + id + " not found");
        }
        return StudentDTO.convertToDTO(optionalStudent.get());
    }

    public StudentDTO save(Student student) {
        // TODO: replace with cne
        Boolean exists = findByAge(student.getAge());
        if (Boolean.TRUE.equals(exists))
            throw new EntityExistsException("Student with age : " + student.getAge() + " not added");
        return StudentDTO.convertToDTO(studentRepo.save(student));
    }


    //update Student
    public StudentDTO update(Student student) {
        log.info("Student " + student + "updated");
        return StudentDTO.convertToDTO(studentRepo.save(student));
    }

    public Page<StudentDTO> findBy(String cne, String fullName, String gender, Pageable page) {
        return StudentDTO.convertToDTO(studentRepo.findAll(StudentSpec.findByProperty("cne", cne)
                .and(StudentSpec.findByProperty("fullName", fullName))
                .and(StudentSpec.findByProperty("gender", gender)), page));

    }

    public long deleteById(long id) {
        try {
            studentRepo.deleteById(id);
            log.info("Student with id : " + id + " deleted");
            return id;
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException("Student with id: "+id+" doesn't exist");
        }
    }
}
