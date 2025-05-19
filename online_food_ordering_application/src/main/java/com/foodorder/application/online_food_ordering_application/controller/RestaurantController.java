package com.foodorder.application.online_food_ordering_application.controller;

import com.foodorder.application.online_food_ordering_application.model.Restaurant;
import com.foodorder.application.online_food_ordering_application.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;


    @GetMapping("/restaurant")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurantsData();
        return ResponseEntity.ok(restaurants); // Always return 200 even if empty
    }

    @PostMapping("/restaurant")
    public ResponseEntity<String> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        restaurantService.addRestaurantData(restaurant);
        return ResponseEntity.ok("Data added successfully");
    }
}
