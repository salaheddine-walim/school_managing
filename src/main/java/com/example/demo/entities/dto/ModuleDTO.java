package com.example.demo.entities.dto;

import com.example.demo.entities.Module;
import com.example.demo.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleDTO {

    private String name;
    private List<Student> students;

    public static ModuleDTO convertToDTO(Module module) {
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setName(module.getName());
        moduleDTO.setStudents(module.getStudents());

        return moduleDTO;
    }

    public static Page<ModuleDTO> convertToDTO(Page<Module> page) {
        return page.map(ModuleDTO::convertToDTO);
    }

}
