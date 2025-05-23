package com.foodorder.application.online_food_ordering_application.repository;

import com.foodorder.application.online_food_ordering_application.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

}
