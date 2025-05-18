package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.model.Dish;
import com.foodorder.application.online_food_ordering_application.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishesService {

    @Autowired
    DishRepository dishRepository;

    public List<Dish> getDishes() {
        return dishRepository.findAll();
    }

    public List<Dish> getRestaurantDishes(Long id) {
        return dishRepository.findByRestaurantId(id);
    }


    public List<Dish> getDishesByCategory(String category) {
        return dishRepository.findByDishesCategory( category);
    }
}
