package com.example.demo.services.filters;

import com.example.demo.entities.Module;
import org.springframework.data.jpa.domain.Specification;

public class ModuleSpec {
    public static Specification<Module> findBy(String property, String value) {
        return (root, query, cb) ->
                cb.like(root.get(property), "%" + value + "%");
    }
}
