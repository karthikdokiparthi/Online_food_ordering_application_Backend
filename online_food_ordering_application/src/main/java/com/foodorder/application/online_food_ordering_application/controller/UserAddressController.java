package com.foodorder.application.online_food_ordering_application.controller;

import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.model.UserAddress;
import com.foodorder.application.online_food_ordering_application.model.UserPrincipal;
import com.foodorder.application.online_food_ordering_application.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAddressController {

    @Autowired
    UserAddressService userAddressService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            return userPrincipal.getUser(); // Now this will work
        }
        throw new IllegalStateException("User not authenticated");
    }

    @PostMapping("/address")
    public ResponseEntity<UserAddress> addAddress(@RequestBody UserAddress userAddress){
        User currentUser=getCurrentUser();
        UserAddress saveAddress=userAddressService.saveOrUpdateAddress(userAddress,currentUser);
        return ResponseEntity.ok(saveAddress);
    }

    @GetMapping("/address")
    public ResponseEntity<UserAddress> getUserAddress(){
        User user=getCurrentUser();
        UserAddress userAddress=userAddressService.getUserAddress(user);
        return ResponseEntity.ok(userAddress);
    }

    @PutMapping("/address")
    public ResponseEntity<UserAddress> updateAddress(@RequestBody UserAddress userAddress){
        User user=getCurrentUser();
        UserAddress updateAddress=userAddressService.saveOrUpdateAddress(userAddress,user);
        return ResponseEntity.ok(updateAddress);
    }
}
