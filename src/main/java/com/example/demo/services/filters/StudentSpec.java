package com.example.demo.services.filters;

import com.example.demo.entities.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpec {

    //TODO: Specification Generic -- A faire
    public static Specification<Student> findBy(String cne, String fullName, String sexe, int age) {
        return (
                (root, query, cb) -> {
                    if (cne != null && !cne.isEmpty())
                        return cb.and(cb.like(root.get("cne"), "%" + cne + "%"));
                    else if (fullName != null && !fullName.isEmpty())
                        return cb.and(cb.like(root.get("fullName"), "%" + fullName + "%"));
                    else if (sexe != null && !sexe.isEmpty())
                        return cb.and(cb.like(root.get("sexe"), "%" + sexe + "%"));
                    else if (age > 0)
                        return cb.and(cb.equal(root.get("sexe"), age));

                    return null;
                });
    }

    public static Specification<Student> findByProperty(String property, String value) {
        return
                (root, query, cb) -> cb.like(root.get(property), "%" + value + "%");
    }

}
