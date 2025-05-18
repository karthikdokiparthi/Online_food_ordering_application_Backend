package com.foodorder.application.online_food_ordering_application.controller;

import com.foodorder.application.online_food_ordering_application.model.Restaurant;
import com.foodorder.application.online_food_ordering_application.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;


    @GetMapping("/restaurant")
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        List<Restaurant> restaurant=restaurantService.getAllRestaurantsData();
        if(restaurant!=null && !restaurant.isEmpty()) {
            return new ResponseEntity<>(restaurant, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
