package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.exception.ResourceNotFoundException;
import com.foodorder.application.online_food_ordering_application.model.*;
import com.foodorder.application.online_food_ordering_application.repository.CartItemRepository;
import com.foodorder.application.online_food_ordering_application.repository.CartRepository;
import com.foodorder.application.online_food_ordering_application.repository.DishRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private DishRepository dishRepository;

    @Transactional
    public Cart getOrCreateCart(User user) {
        Optional<Cart> cartOpt = cartRepository.findByUserWithItems(user);
        if (cartOpt.isPresent()) {
            return cartOpt.get();
        } else {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        }
    }

    @Transactional
    public Cart addItemToCart(User user, Long dishId, int quantity) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
        Cart cart = getOrCreateCart(user);
        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartAndDish(cart, dish);

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setDish(dish);
            newItem.setQuantity(quantity);
            cartItemRepository.save(newItem);
            cart.getCartItems().add(newItem);
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeItemFromCart(User user, Long cartItemId) {
        Cart cart = getOrCreateCart(user);
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (item.getCart().equals(cart)) {
            cartItemRepository.delete(item);
            cart.getCartItems().remove(item);
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart updateItemQuantity(User user, Long cartItemId, int quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        if (quantity <= 0) {
            return removeItemFromCart(user, cartItemId);
        } else {
            item.setQuantity(quantity);
            cartItemRepository.save(item);
            return getOrCreateCart(user);
        }
    }

    @Transactional
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}