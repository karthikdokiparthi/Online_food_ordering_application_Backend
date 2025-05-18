package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user) {
        // Generate base username (e.g., "john.doe")
        String baseUsername = sanitizeName(user.getFirstName()) + "." + sanitizeName(user.getLastName());
        String uniqueUsername = generateUniqueUsername(baseUsername);

        user.setUsername(uniqueUsername);
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
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