package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.entities.dto.StudentDTO;
import com.example.demo.repos.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    StudentRepo studentRepo;

    StudentService studentService;

    PageRequest pageRequest = PageRequest.of(0, 5);

    Student student = new Student(null, "BH1", "salah eddine", "Homme", 22);
    StudentDTO studentDTO = new StudentDTO("BH1", "salah eddine", "M", 22);

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepo);
    }


    @DisplayName("list des etudiants : ")
    @Nested
    class listEtudiant {


        @DisplayName("should return list of 1 element")
        @Test
        void findAllNotEmptyPageTest_Ok() {

            when(studentRepo.findAll(pageRequest)).thenReturn(
                    new PageImpl<>(List.of(student)));
            Page<StudentDTO> returnedList = studentService.findAll(pageRequest);

            assertThat(returnedList.getTotalPages()).isEqualTo(1);
        }

        @DisplayName("should return empty list")
        @Test
        void findAllEmptyPageTest_Ok() {
            when(studentRepo.findAll(pageRequest)).thenReturn(
                    new PageImpl<>(List.of()));

            Page<StudentDTO> returnedList = studentService.findAll(pageRequest);

            assertThat(returnedList.getTotalElements()).isEqualTo(0);

        }

        @DisplayName("should throw exception Argument not verified")
        @Test
        void findAllTest_Ko() {
            int page = 1;
            int size = 0;
            assertThatThrownBy(() -> studentService.findAll(PageRequest.of(1, 0))).isInstanceOf(IllegalArgumentException.class);
        }
    }


    @DisplayName("delete student")
    @Nested
    class deleteEtudiant {


        @Test
        void deleteTest_Ok() {
            //given
            //Input : id 1
            //output : findById -> Empty optional
            //execution
            Long id = studentService.deleteById(1L);
            //assertions
            assertEquals(1L, id);
        }

        @Test
        void deleteTest_Ko() {
            doThrow(RuntimeException.class).doNothing().when(studentRepo).deleteById(anyLong());
            assertThatThrownBy(
                    () -> studentService.deleteById(12L)
            ).isInstanceOf(RuntimeException.class);
        }

    }

    @DisplayName("filter")
    @Nested
    class searchEtudiant {

        @Test
        void shouldGetHomme() {
            Page<Student> page = new PageImpl<>(List.of(student));
            when(studentRepo.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);
            assertThat(studentService.findBy("anyString", "anyString", "anyString", PageRequest.of(0, 5))).isInstanceOf(PageImpl.class);
        }

        @Test
        void shouldFindEtudiant() {
            long id = 1L;
            when(studentRepo.findById(id)).thenReturn(Optional.of(student));
            assertEquals(StudentDTO.convertToDTO(student), studentService.findById(id));
        }

        @Test
        void shouldThrowException() {
            assertThatThrownBy(
                    () -> studentService.findById(1L)
            ).isInstanceOf(RuntimeException.class);
        }
    }

    @DisplayName("update")
    @Nested
    class UpdateStudent {

        @Test
        void update_Ok() {

        }
    }

    @Test
    void ItShouldReturnTrue() throws Exception {
        when(studentRepo.findByAge(22)).thenReturn(true);
        studentService.findByAge(22);
        verify(studentRepo).findByAge(22);
    }


    @Test
    void shouldAddEtudiant() throws Exception {
        //given
        Student etudiant = Student.convertToEntity(studentDTO);
        //when
        when(studentRepo.findByAge(anyInt())).thenReturn(false);
        when(studentRepo.save(etudiant)).thenReturn(etudiant);
        StudentDTO returnedStudent = studentService.save(etudiant);
        //then
        verify(studentRepo).save(etudiant);
        assertEquals(etudiant, Student.convertToEntity(returnedStudent));
    }

    @Test
    void willThrowWhenAgeExists() {
        //when
        when(studentRepo.findByAge(anyInt())).thenReturn(true);

        assertThatThrownBy(
                () -> studentService.save(student)
        ).isInstanceOf(Exception.class);
    }

}