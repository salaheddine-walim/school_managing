package com.example.demo.controllers;

import com.example.demo.entities.Student;
import com.example.demo.entities.dto.StudentDTO;
import com.example.demo.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;


    @GetMapping
    public List<StudentDTO> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                    @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<StudentDTO> studentDTOPage = studentService.findAll(PageRequest.of(page, size));
        return studentDTOPage.getContent();
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<StudentDTO>> findBy(@RequestParam(required = false, defaultValue = "") String cne,
                                                   @RequestParam(required = false, defaultValue = "") String fullName,
                                                   @RequestParam(required = false, defaultValue = "") String gender,
                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam(required = false, defaultValue = "5") int size) {
        Page<StudentDTO> studentsList = studentService.findBy(cne, fullName, gender, PageRequest.of(page, size));
        return ResponseEntity.ok(studentsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Long id) {
        StudentDTO studentDTO = studentService.findById(id);
        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> save(@RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.save(Student.convertToEntity(studentDTO));
        return ResponseEntity.ok(updatedStudent);
    }

    @PutMapping
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.update(Student.convertToEntity(studentDTO));
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable long id) {
        studentService.deleteById(id);
        return ResponseEntity.ok(id);
    }

}
