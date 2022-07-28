package com.example.demo.entities;

import com.example.demo.entities.dto.ModuleDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Student> students;

    public static Module convertToEntity(ModuleDTO moduleDTO) {
        Module module = new Module();
        module.setName(moduleDTO.getName());
        module.setStudents(moduleDTO.getStudents());

        return module;
    }

}
