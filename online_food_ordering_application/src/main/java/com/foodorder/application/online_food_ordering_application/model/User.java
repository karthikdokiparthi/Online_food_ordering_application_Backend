package com.foodorder.application.online_food_ordering_application.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "online_food_order")
@Data
public class User {
    @Id
    int id;
    String username;
    String password;
}
