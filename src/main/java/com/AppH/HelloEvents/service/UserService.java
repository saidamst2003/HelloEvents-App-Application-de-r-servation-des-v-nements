package com.AppH.HelloEvents.service;

import com.AppH.HelloEvents.repository.UserReposetory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {

@Autowired
    private UserReposetory userRepository;

    public int countUsersByRole(String role) {
        return userRepository.countUserByrole(role);
    }
}