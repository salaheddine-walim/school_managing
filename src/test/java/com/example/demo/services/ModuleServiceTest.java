package com.example.demo.services;

import com.example.demo.entities.Module;
import com.example.demo.entities.dto.ModuleDTO;
import com.example.demo.repos.ModuleRepo;
import com.example.demo.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ModuleServiceTest {
    Module module = Module.builder().id(1L).name("J2EE").build();
    ModuleDTO moduleDTO = ModuleDTO.convertToDTO(module);
    @Autowired
    ModuleRepo moduleRepo;
    ModuleService moduleService;
    PageRequest pageRequest = PageRequest.of(0, 5);


    @BeforeEach
    void setUp() {
        moduleService = new ModuleService(moduleRepo);
        moduleService.save(module);
    }

    @DisplayName("should return list of 1 element")
    @Test
    void findAllNotEmptyPageTest_Ok() {
        Page<ModuleDTO> returnedList = moduleService.findAll("J2EE", pageRequest);
        assertThat(returnedList.getTotalElements()).isEqualTo(1);
    }

    @Test
    void saveTest_Ok() {
        ModuleDTO savedModule = moduleService.save(module);
        assertThat(savedModule).hasFieldOrPropertyWithValue("name", "J2EE");
    }

    @Test
    void saveTest_KO() {
        assertThatThrownBy(
                () -> moduleService.save(new Module())).isInstanceOf(RuntimeException.class);
    }

    @Test
    void findById_Ok() {
        long id = 1L;
        moduleService.save(module);
        ModuleDTO returnedModule = moduleService.findById(id);
        assertNotNull(returnedModule);
        System.out.println("returnedModule : " + returnedModule);
        System.out.println("moduleDTO : " + moduleDTO);
        assertEquals(returnedModule.getName(), moduleDTO.getName());
    }

    @Test
    void findById_Ko() {
        assertThatThrownBy(
                () -> moduleService.findById(22L)
        ).isInstanceOf(ObjectNotFoundException.class);
    }

}