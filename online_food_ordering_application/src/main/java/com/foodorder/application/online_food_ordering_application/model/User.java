package com.foodorder.application.online_food_ordering_application.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "online_food_order")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true) // Add unique constraint
    private String username;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
}