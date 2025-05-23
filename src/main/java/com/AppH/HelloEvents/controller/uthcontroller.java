package com.AppH.HelloEvents.controller;

import com.AppH.HelloEvents.config.jwtUtils;
import com.AppH.HelloEvents.model.User;
import com.AppH.HelloEvents.repository.UserReposetory;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class uthcontroller {

    private final UserReposetory userReposetory;
    private final PasswordEncoder passwordEncoder ;
    private final jwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager ;


    @PostMapping("/regidter")
    public ResponseEntity<?> rergiter(@RequestBody User user) {
        if (userReposetory.findByUserName(user.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userReposetory.save(user));
    }






    
}
