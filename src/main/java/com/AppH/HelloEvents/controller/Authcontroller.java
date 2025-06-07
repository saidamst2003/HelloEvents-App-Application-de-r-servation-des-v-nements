package com.AppH.HelloEvents.controller;

import com.AppH.HelloEvents.config.jwtUtils;
import com.AppH.HelloEvents.dto.UserDto;
import com.AppH.HelloEvents.model.Role;
import com.AppH.HelloEvents.model.User;
import com.AppH.HelloEvents.repository.RoleRepository;
import com.AppH.HelloEvents.repository.UserReposetory;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/api/auth")
public class Authcontroller {

    @Autowired
    private RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(Authcontroller.class);

    private final UserReposetory userReposetory;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public Authcontroller(UserReposetory userReposetory, PasswordEncoder passwordEncoder,
                          jwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userReposetory = userReposetory;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    // Initialize default roles when application starts
    @PostConstruct
    public void initRoles() {
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_CLIENT");
        log.info("Default roles initialized: ROLE_ADMIN, ROLE_CLIENT");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> registrationData) {
        try {
            log.info("Registration attempt with data: {}", registrationData);

            // Extract data from the request
            String username = (String) registrationData.get("username");
            String email = (String) registrationData.get("email");
            String password = (String) registrationData.get("password");

            // Handle roles from Angular format
            List<Map<String, Object>> rolesData = (List<Map<String, Object>>) registrationData.get("roles");
            Set<String> roleNames = new HashSet<>();

            if (rolesData != null && !rolesData.isEmpty()) {
                for (Map<String, Object> roleData : rolesData) {
                    String roleName = (String) roleData.get("name");
                    if (roleName != null) {
                        roleNames.add(roleName);
                    }
                }
            }

            // Validate required fields
            if (username == null || username.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (password == null || password.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }

            log.info("Register attempt for username: {}, roles: {}", username, roleNames);

            // Check if username already exists
            if (userReposetory.findByUsername(username) != null) {
                log.warn("Username already exists: {}", username);
                return ResponseEntity.badRequest().body("Username already exists");
            }

            // Check if email already exists
            if (userReposetory.existsByEmail(email)) {
                log.warn("Email already exists: {}", email);
                return ResponseEntity.badRequest().body("Email already exists");
            }

            // Create new user
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));

            Set<Role> roles = new HashSet<>();

            // Handle role assignment
            if (!roleNames.isEmpty()) {
                for (String roleName : roleNames) {
                    // Ensure role name starts with "ROLE_"
                    String normalizedRoleName = roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName.toUpperCase();

                    // Only allow ADMIN and CLIENT roles
                    if (normalizedRoleName.equals("ROLE_ADMIN") || normalizedRoleName.equals("ROLE_CLIENT")) {
                        Role role = roleRepository.findByName(normalizedRoleName)
                                .orElseGet(() -> createRoleIfNotExists(normalizedRoleName));
                        roles.add(role);
                    } else {
                        log.warn("Invalid role requested: {}", roleName);
                        return ResponseEntity.badRequest().body("Invalid role: " + roleName + ". Only ADMIN and CLIENT roles are allowed.");
                    }
                }
            } else {
                // Assign default CLIENT role if no roles provided
                Role defaultRole = roleRepository.findByName("ROLE_CLIENT")
                        .orElseGet(() -> createRoleIfNotExists("ROLE_CLIENT"));
                roles.add(defaultRole);
            }

            user.setRoles(roles);

            User savedUser = userReposetory.save(user);
            log.info("User registered successfully: {} with roles: {}",
                    savedUser.getUsername(),
                    savedUser.getRoles().stream().map(Role::getName).toArray());

            // Create response without sensitive information
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedUser.getId());
            response.put("username", savedUser.getUsername());
            response.put("email", savedUser.getEmail());
            response.put("roles", savedUser.getRoles().stream().map(Role::getName).toArray());
            response.put("message", "User registered successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Error during registration: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while registering user: " + e.getMessage());
        }
    }

    // Helper method to create roles if they don't exist
    private Role createRoleIfNotExists(String roleName) {
        return roleRepository.findByName(roleName).orElseGet(() -> {
            Role role = new Role();
            role.setName(roleName);
            Role savedRole = roleRepository.save(role);
            log.info("Created new role: {}", roleName);
            return savedRole;
        });
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        log.info("Login attempt for username: {}", username);

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            log.error("Username or password is null or empty");
            return ResponseEntity.badRequest().body("Username and password are required");
        }

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            if (authentication.isAuthenticated()) {
                String token = jwtUtils.generateToken(username);

                // Get user details for response
                User user = userReposetory.findByUsername(username);

                Map<String, Object> authData = new HashMap<>();
                authData.put("token", token);
                authData.put("type", "Bearer");
                authData.put("username", user.getUsername());
                authData.put("email", user.getEmail());
                authData.put("roles", user.getRoles().stream().map(Role::getName).toArray());

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

    // Endpoint to get available roles
    @GetMapping("/roles")
    public ResponseEntity<?> getAvailableRoles() {
        return ResponseEntity.ok(new String[]{"ADMIN", "CLIENT"});
    }
}