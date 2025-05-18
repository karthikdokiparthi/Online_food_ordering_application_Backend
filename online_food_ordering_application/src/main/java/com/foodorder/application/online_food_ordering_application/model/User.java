package com.foodorder.application.online_food_ordering_application.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_data")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String username; // Auto-generated from firstName + lastName

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
}