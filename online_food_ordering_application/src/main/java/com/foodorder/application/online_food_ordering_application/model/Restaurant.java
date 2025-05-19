package com.foodorder.application.online_food_ordering_application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private String image;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dish> dishes;

    // Constructors
    public Restaurant() {}

    public Restaurant(String name,String image ,List<Dish> dishes) {
        this.name = name;
        this.image=image;
        this.dishes = dishes;
    }
}
