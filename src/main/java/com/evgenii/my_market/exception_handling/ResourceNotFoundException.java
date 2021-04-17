package com.evgenii.my_market.exception_handling;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
            super(message);
    }
}
