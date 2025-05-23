package com.AppH.HelloEvents.controller;

import com.AppH.HelloEvents.config.jwtUtils;
import com.AppH.HelloEvents.model.User;
import com.AppH.HelloEvents.repository.UserReposetory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    private static final Logger log = LoggerFactory.getLogger(Authcontroller.class);

    private final UserReposetory userReposetory;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public Authcontroller(UserReposetory userReposetory, PasswordEncoder passwordEncoder, jwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userReposetory = userReposetory;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        log.info("Register attempt for username: {}", user.getUsername());

        if (userReposetory.findByUsername(user.getUsername()) != null) {
            log.warn("Username already exists: {}", user.getUsername());
            return ResponseEntity.badRequest().body("Username already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Encrypted password: {}", user.getPassword());

        User savedUser = userReposetory.save(user);
        log.info("User registered successfully: {}", savedUser.getUsername());

        return ResponseEntity.ok(savedUser);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        log.info("Login attempt for username: {}", username);

        if (username == null || password == null) {
            log.error("Username or password is null");
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken(username);

                Map<String, Object> authData = new HashMap<>();
                authData.put("token", token);
                authData.put("type", "Bearer");

                log.info("Login successful for username: {}", username);
                return ResponseEntity.ok(authData);
            }

            log.warn("Authentication failed for username: {}", username);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        } catch (AuthenticationException e) {
            log.error("Authentication exception for username {}: {}", username, e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}
