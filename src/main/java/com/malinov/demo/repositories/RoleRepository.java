package com.malinov.demo.repositories;

import com.malinov.demo.enums.State;
import com.malinov.demo.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Role findByIdAndState(Long id, State state);
    Role findByNameAndState(String superAdmin, State state);
    List<Role> findByState(State state);
    List<Role> findByStateNot(State state);
}