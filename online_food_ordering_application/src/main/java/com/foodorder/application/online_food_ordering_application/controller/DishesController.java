package com.foodorder.application.online_food_ordering_application.controller;

import com.foodorder.application.online_food_ordering_application.model.Dish;
import com.foodorder.application.online_food_ordering_application.service.DishesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DishesController {

    @Autowired
    DishesService dishesService;

    @GetMapping("/dishes")
    public ResponseEntity<List<Dish>> getAllDishes(){
        List<Dish> dishes=dishesService.getDishes();
        if(dishes!=null && !dishes.isEmpty()){
            return new ResponseEntity<>(dishes, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/dishes/{id}")
    public ResponseEntity<List<Dish>> getRestaurantDishes(@PathVariable Long id) {
        List<Dish> dishes = dishesService.getRestaurantDishes(id);
        return ResponseEntity.ok(dishes);
    }

    @GetMapping("/dishes/category/{category}")
    public ResponseEntity<List<Dish>> getDishesByCategory(@PathVariable String category) {
        List<Dish> dishes = dishesService.getDishesByCategory(category);
        return ResponseEntity.ok(dishes);
    }
}
