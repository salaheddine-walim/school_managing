package com.example.demo.repos;

import com.example.demo.entities.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepo extends JpaRepository<Module, Long>, JpaSpecificationExecutor<Module> {


    Page<Module> getModulesByStudentsId(long id, Pageable pageable);
}
