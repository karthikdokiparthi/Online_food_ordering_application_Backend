package com.foodorder.application.online_food_ordering_application.controller;

import com.foodorder.application.online_food_ordering_application.model.Cart;
import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.model.UserPrincipal;
import com.foodorder.application.online_food_ordering_application.service.CartService;
import com.foodorder.application.online_food_ordering_application.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private MyUserDetailsService userDetailsService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getUser(); // Now this will work
        }
        throw new IllegalStateException("User not authenticated");
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestParam Long dishId, @RequestParam int quantity) {
        User user = getCurrentUser();
        Cart cart = cartService.addItemToCart(user, dishId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long cartItemId) {
        User user = getCurrentUser();
        Cart cart = cartService.removeItemFromCart(user, cartItemId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<Cart> updateQuantity(@PathVariable Long cartItemId, @RequestParam int quantity) {
        User user = getCurrentUser();
        Cart cart = cartService.updateItemQuantity(user, cartItemId, quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        User user = getCurrentUser();
        Cart cart = cartService.getOrCreateCart(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/clear")
    public ResponseEntity<Void> clearCart() {
        User user = getCurrentUser();
        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}