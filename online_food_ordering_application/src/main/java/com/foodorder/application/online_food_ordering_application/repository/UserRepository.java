package com.foodorder.application.online_food_ordering_application.repository;

import com.foodorder.application.online_food_ordering_application.model.User;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsernameOrEmail(String username, String email);

    // For checking existing usernames during generation
    int countByUsernameStartingWith(String prefix);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
