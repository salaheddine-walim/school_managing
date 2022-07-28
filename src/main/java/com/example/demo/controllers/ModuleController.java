package com.example.demo.controllers;

import com.example.demo.entities.Module;
import com.example.demo.entities.dto.ModuleDTO;
import com.example.demo.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/module")
public class ModuleController {

    @Autowired
    ModuleService moduleService;

    //Get List Of Modules
    @GetMapping
    public Page<ModuleDTO> findAll(@RequestParam(name = "name", required = false) String name,
                                   @RequestParam(name = "page", value = "0") int page,
                                   @RequestParam(name = "size", value = "5") int size) {
        return moduleService.findAll(name, PageRequest.of(page, size));
    }

    //Get Module By Id
    @GetMapping("/{id}")
    public ModuleDTO getModuleById(@PathVariable(name = "id") Long id) {
        return moduleService.findById(id);
    }

    //Add Module
    @PostMapping
    public ModuleDTO saveModule(ModuleDTO moduleDTO) {
        return moduleService.save(Module.convertToEntity(moduleDTO));
    }

    //Update Module
    @PutMapping
    public ModuleDTO updateModule(ModuleDTO moduleDTO) {
        return moduleService.updateModule(Module.convertToEntity(moduleDTO));
    }

    //Delete Module
    @DeleteMapping
    public long deleteModule(Long id) {
        return moduleService.deleteById(id);
    }
}
