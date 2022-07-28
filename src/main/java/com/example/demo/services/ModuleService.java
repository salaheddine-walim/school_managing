package com.example.demo.services;

import com.example.demo.entities.Module;
import com.example.demo.entities.dto.ModuleDTO;
import com.example.demo.repos.ModuleRepo;
import com.example.demo.services.exceptions.NullValueParameter;
import com.example.demo.services.exceptions.ObjectNotFoundException;
import com.example.demo.services.filters.ModuleSpec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class ModuleService {
    @Autowired
    ModuleRepo moduleRepo;

    // list of modules
    public Page<ModuleDTO> findAll(String name, Pageable page) {
        return ModuleDTO.convertToDTO(moduleRepo.findAll(ModuleSpec.findBy("name", name), page));
    }

    //find by id
    public ModuleDTO findById(Long id) {
        Optional<Module> moduleOptional = moduleRepo.findById(id);
        if (!moduleOptional.isPresent()) {
            log.info("Can't find Module with id :" + id);
            throw new ObjectNotFoundException("Module with id: " + id + " not found");
        }
        return ModuleDTO.convertToDTO(moduleOptional.get());
    }

    //save
    public ModuleDTO save(Module module) {
        if (module.getName().isEmpty() || module.getName() == null) {
            log.error("Module not saved, Empty name");
            throw new NullValueParameter("Module not saved, Empty name");
        }
        log.info("Module Saved");
        return ModuleDTO.convertToDTO(moduleRepo.save(module));
    }

    //update
    public ModuleDTO updateModule(Module module) {
        return ModuleDTO.convertToDTO(moduleRepo.save(module));
    }

    //remove
    public long deleteById(long id) {
        try {
            moduleRepo.deleteById(id);
            return id;
        } catch (ObjectNotFoundException e) {
            throw new ObjectNotFoundException("Can't delete Module with id : " + id);
        }
    }

    //get Modules By students ID
    public Page<ModuleDTO> getModulesByStudentsId(long id, Pageable page) {
        return ModuleDTO.convertToDTO(moduleRepo.getModulesByStudentsId(id, page));
    }
}
