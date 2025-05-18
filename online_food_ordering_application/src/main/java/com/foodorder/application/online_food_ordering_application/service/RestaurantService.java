package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.model.Dish;
import com.foodorder.application.online_food_ordering_application.model.Restaurant;
import com.foodorder.application.online_food_ordering_application.repository.DishRepository;
import com.foodorder.application.online_food_ordering_application.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {
    @Autowired
    RestaurantRepository restaurantRepository;
    DishRepository dishRepository;

    public List<Restaurant> getAllRestaurantsData() {
        return restaurantRepository.findAll();
    }

    public void addRestaurantData(Restaurant restaurant) {
        for(Dish dish:restaurant.getDishes()){
            if(dish.getId()==null){
                dishRepository.save(dish);
            }else{
                dishRepository.save(dish);
            }
            restaurantRepository.save(restaurant);
        }
    }
}
