package com.foodorder.application.online_food_ordering_application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(length = 1000)
    private String img;
    private double price;
    @Column(length = 1000)
    private String description;
    private String countryDish;
    private String type;
    private String dishCategory;
    private String category;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private Restaurant restaurant;

    // Constructors
    public Dish() {}

    public Dish(String name, String img,double price, String description, String countryDish, String type, String category,String dishCategory, Restaurant restaurant) {
        this.name = name;
        this.img=img;
        this.price = price;
        this.description = description;
        this.countryDish = countryDish;
        this.type = type;
        this.category = category;
        this.dishCategory=dishCategory;
        this.restaurant = restaurant;
    }
}
