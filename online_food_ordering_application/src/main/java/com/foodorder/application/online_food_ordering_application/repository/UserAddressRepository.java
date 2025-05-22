package com.foodorder.application.online_food_ordering_application.repository;

import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {

    Optional<UserAddress> findByUser(User user);
}
