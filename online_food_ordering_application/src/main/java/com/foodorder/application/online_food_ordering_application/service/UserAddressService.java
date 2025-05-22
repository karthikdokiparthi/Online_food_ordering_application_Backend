package com.foodorder.application.online_food_ordering_application.service;

import com.foodorder.application.online_food_ordering_application.model.User;
import com.foodorder.application.online_food_ordering_application.model.UserAddress;
import com.foodorder.application.online_food_ordering_application.repository.UserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAddressService {

    @Autowired
    private UserAddressRepository userAddressRepository;

    public UserAddressService(UserAddressRepository userAddressRepository){
        this.userAddressRepository=userAddressRepository;
    }

    public UserAddress saveOrUpdateAddress(UserAddress userAddress, User user) {
        Optional<UserAddress> existingAddress=userAddressRepository.findByUser(user);
        if(existingAddress.isPresent()){
            UserAddress addressToUpdate=existingAddress.get();
            updateAddressField(addressToUpdate,userAddress);
            return userAddressRepository.save(addressToUpdate);
        }else{
            userAddress.setUser(user);
            return userAddressRepository.save(userAddress);
        }
    }

    public void updateAddressField(UserAddress existing,UserAddress newAddress){
        existing.setFullName(newAddress.getFullName());
        existing.setPhoneNumber(newAddress.getPhoneNumber());
        existing.setAlternateNumber(newAddress.getAlternateNumber());
        existing.setPinCode(newAddress.getPinCode());
        existing.setCity(newAddress.getCity());
        existing.setState(newAddress.getState());
        existing.setHouseNo(newAddress.getHouseNo());
        existing.setArea(newAddress.getArea());
    }

    public UserAddress getUserAddress(User user) {
        return userAddressRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Address not found"));
    }
}
