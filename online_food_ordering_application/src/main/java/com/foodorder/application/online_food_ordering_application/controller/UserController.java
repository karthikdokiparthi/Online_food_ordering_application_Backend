package com.foodorder.application.online_food_ordering_application.controller;

import com.foodorder.application.online_food_ordering_application.exception.AuthenticationFailedException;
import com.foodorder.application.online_food_ordering_application.exception.DuplicateResourceException;
import com.foodorder.application.online_food_ordering_application.exception.UnauthorizedAccessException;
import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.model.UserRole;
import com.foodorder.application.online_food_ordering_application.service.JwtService;
import com.foodorder.application.online_food_ordering_application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService service, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.service = service;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // Regular user registration
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        try {
            // Force USER role for public registration
            user.setRole(UserRole.ROLE_USER);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user,false));
        } catch (Exception ex) {
            throw new DuplicateResourceException("Username or email already exists");
        }
    }

    // Admin registration (secured)
    @PostMapping("/admin/register")
    public ResponseEntity<?> adminRegister(@Valid @RequestBody User user) {
        // Authorization check
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(UserRole.ROLE_ADMIN.name()))) {
            throw new UnauthorizedAccessException("Only admins can create admin accounts");
        }

        try {
            user.setRole(UserRole.ROLE_ADMIN);
            return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user, true));
        } catch (Exception ex) {
            throw new DuplicateResourceException(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());
            return ResponseEntity.ok(Map.of("token", token));
        } catch (BadCredentialsException ex) {
            throw new AuthenticationFailedException("Invalid username or password");
        }
    }
}