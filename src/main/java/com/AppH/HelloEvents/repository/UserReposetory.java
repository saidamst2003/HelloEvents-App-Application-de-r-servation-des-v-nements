package com.AppH.HelloEvents.repository;

import com.AppH.HelloEvents.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserReposetory extends JpaRepository<User, Integer> {
User findByUsername(String username) ;


  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);

  @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = :roleName")
  Page<User> findByRoleName(String roleName, Pageable pageable);

  Page<User> findByRolesName(String roleName, Pageable pageable);

//  @Query(value = " select count(users.id) from users where role=:role",nativeQuery = true)
//  int  countUserByrole(String role);
}
