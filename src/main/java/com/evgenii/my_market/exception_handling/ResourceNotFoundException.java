package com.evgenii.my_market.exception_handling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
        Logger logger = LoggerFactory.getLogger(ResourceNotFoundException.class);
        logger.error(message);
    }
}
