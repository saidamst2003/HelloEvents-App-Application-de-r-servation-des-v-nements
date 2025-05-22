package com.AppH.HelloEvents.repository;

import com.AppH.HelloEvents.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReposetory extends JpaRepository<User, Integer> {
User findByUserName(String userName) ;
}
