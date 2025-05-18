package com.foodorder.application.online_food_ordering_application.repository;

import com.foodorder.application.online_food_ordering_application.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish,Long> {

    List<Dish> findByRestaurantId(Long id);
}
