package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.model.Dish;
import com.foodorder.application.online_food_ordering_application.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DishesService {

    @Autowired
    DishRepository dishRepository;

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }
}
