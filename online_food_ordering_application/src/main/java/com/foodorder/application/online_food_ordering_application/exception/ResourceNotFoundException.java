// src/main/java/com/foodorder/application/online_food_ordering_application/exception/ResourceNotFoundException.java
package com.foodorder.application.online_food_ordering_application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}