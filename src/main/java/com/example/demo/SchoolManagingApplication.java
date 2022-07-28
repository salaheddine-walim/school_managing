package com.example.demo;


import com.example.demo.entities.Module;
import com.example.demo.entities.Student;
import com.example.demo.repos.ModuleRepo;
import com.example.demo.repos.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class SchoolManagingApplication implements CommandLineRunner {
    @Autowired
    StudentRepo studentRepo;

    @Autowired
    ModuleRepo moduleRepo;

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagingApplication.class, args);
    }

    @Override
    public void run(String... args) {

        for (int i = 10; i < 20; i++) {
            studentRepo.save(new Student(null, "BH" + i, "Student" + i, i % 2 == 0 ? "Man" : "Woman", i));
        }

        moduleRepo.save(Module.builder().id(1L).build());
    }

}
