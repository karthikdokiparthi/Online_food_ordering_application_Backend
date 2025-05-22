package com.foodorder.application.online_food_ordering_application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "username", referencedColumnName = "username"),
            @JoinColumn(name = "email", referencedColumnName = "email")
    })
    @JsonIgnore
    private User user;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Long phoneNumber;

    @Column(nullable = false)
    private Long alternateNumber;

    @Column(nullable = false)
    private int pinCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    private String houseNo;

    @Column(nullable = false)
    private String area;
}
