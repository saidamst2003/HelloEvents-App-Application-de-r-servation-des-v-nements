package com.AppH.HelloEvents.repository;

import com.AppH.HelloEvents.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);
}


