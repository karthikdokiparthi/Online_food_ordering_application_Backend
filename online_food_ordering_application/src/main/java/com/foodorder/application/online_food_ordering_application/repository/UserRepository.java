package com.foodorder.application.online_food_ordering_application.repository;

import com.foodorder.application.online_food_ordering_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username);
}
