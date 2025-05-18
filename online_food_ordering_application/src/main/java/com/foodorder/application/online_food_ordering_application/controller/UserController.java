package com.foodorder.application.online_food_ordering_application.controller;


import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.model.UserPrincipal;
import com.foodorder.application.online_food_ordering_application.service.JwtService;
import com.foodorder.application.online_food_ordering_application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @GetMapping("/")
    public String getMessage(){
        return "Hello";
    }

    @PostMapping("register")
    public User register(@RequestBody User user) {
        return service.saveUser(user);
    }
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody User user) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        // Extract the ACTUAL username from the authenticated user
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtService.generateToken(userPrincipal.getUsername()); // Use DB username

        return ResponseEntity.ok().body(Map.of("token", token));
    }
}

