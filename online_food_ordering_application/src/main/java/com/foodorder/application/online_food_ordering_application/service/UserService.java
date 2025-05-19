package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.exception.DuplicateResourceException;
import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user) {
        // Check for existing email
        if (repository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        // Generate username
        String baseUsername = sanitizeName(user.getFirstName()) + "." + sanitizeName(user.getLastName());
        String uniqueUsername = generateUniqueUsername(baseUsername);
        user.setUsername(uniqueUsername);

        // Encode password
        user.setPassword(encoder.encode(user.getPassword()));

        try {
            return repository.save(user);
        } catch (DataIntegrityViolationException ex) {
            // Handle unique constraint violations (username)
            if (isUsernameConflict(ex)) {
                throw new DuplicateResourceException("Username already exists");
            }
            throw new DuplicateResourceException("Duplicate resource entry");
        }
    }

    private boolean isUsernameConflict(DataIntegrityViolationException ex) {
        return ex.getCause() instanceof ConstraintViolationException &&
                ((ConstraintViolationException) ex.getCause()).getConstraintName() != null &&
                ((ConstraintViolationException) ex.getCause()).getConstraintName().toLowerCase().contains("username");
    }

    private String sanitizeName(String name) {
        // Remove spaces and special characters, convert to lowercase
        return name.trim().toLowerCase()
                .replaceAll("[^a-zA-Z0-9.]", "") // Allow only letters, numbers, and dots
                .replaceAll("\\s+", ".");
    }

    private String generateUniqueUsername(String baseUsername) {
        int count = repository.countByUsernameStartingWith(baseUsername);
        if (count == 0) {
            return baseUsername; // No duplicates
        } else {
            // Append incrementing number (e.g., "john.doe1")
            return baseUsername + (count + 1);
        }
    }
}