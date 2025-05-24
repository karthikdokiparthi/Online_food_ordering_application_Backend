package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.exception.DuplicateResourceException;
import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    // Original method for regular user registration
    public User saveUser(User user) {
        return saveUser(user, false);
    }

    // Updated method with admin registration flag
    public User saveUser(User user, boolean isAdminRegistration) {
        // Check for existing email first
        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        // Handle username based on registration type
        if (isAdminRegistration) {
            validateAdminUsername(user);
        } else {
            generateUsername(user);
        }

        // Check for existing username
        if (repository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        // Encode password
        user.setPassword(encoder.encode(user.getPassword()));

        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            handleIntegrityViolation(ex);
            throw ex; // This will never be reached due to previous checks
        }
    }

    private void validateAdminUsername(User user) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required for admin registration");
        }
    }

    private void generateUsername(User user) {
        String baseUsername = sanitizeName(user.getFirstName()) + "." + sanitizeName(user.getLastName());
        user.setUsername(generateUniqueUsername(baseUsername));
    }

    private String sanitizeName(String name) {
        return name.trim().toLowerCase()
                .replaceAll("[^a-zA-Z0-9.]", "")
                .replaceAll("\\s+", ".");
    }

    private String generateUniqueUsername(String baseUsername) {
        int count = repository.countByUsernameStartingWith(baseUsername);
        return count == 0 ? baseUsername : baseUsername + (count + 1);
    }

    private void handleIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            String constraintName = ((org.hibernate.exception.ConstraintViolationException) ex.getCause())
                    .getConstraintName();
            if (constraintName != null && constraintName.toLowerCase().contains("username")) {
                throw new DuplicateResourceException("Username already exists");
            }
        }
        throw new DuplicateResourceException("Duplicate resource entry");
    }
}