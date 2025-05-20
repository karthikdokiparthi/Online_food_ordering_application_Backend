package com.foodorder.application.online_food_ordering_application.repository;

import com.foodorder.application.online_food_ordering_application.model.Cart;
import com.foodorder.application.online_food_ordering_application.model.CartItem;
import com.foodorder.application.online_food_ordering_application.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartAndDish(Cart cart, Dish dish);
}