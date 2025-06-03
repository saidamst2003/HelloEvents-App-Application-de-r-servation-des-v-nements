package com.AppH.HelloEvents.service;

import com.AppH.HelloEvents.model.User;
import com.AppH.HelloEvents.repository.UserReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class UserService {

@Autowired
    private UserReposetory userRepository;




    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Page<User> findByRole(String role, Pageable pageable) {
        return userRepository.findByRole(role, pageable);
    }
    public int countUsersByRole(String role) {
        return userRepository.countUserByrole(role);
    }
}