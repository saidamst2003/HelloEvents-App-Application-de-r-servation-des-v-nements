package com.AppH.HelloEvents.repository;

import com.AppH.HelloEvents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserReposetory extends JpaRepository<User, Integer> {
User findByUsername(String username) ;

@Query(value = " select count(users.id) from users µwhere role=:role",nativeQuery = true)
  int  countUserByrole (String role);
}
