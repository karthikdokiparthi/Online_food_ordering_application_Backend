package com.foodorder.application.online_food_ordering_application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    private int quantity;
    private double price;
    private String dishName;
    private String dishImage;

    @PrePersist
    @PreUpdate
    private void updateDishDetails() {
        if (dish != null) {
            this.dishName = dish.getName();
            this.dishImage = dish.getImg();
            this.price = dish.getPrice();
        }
    }
}