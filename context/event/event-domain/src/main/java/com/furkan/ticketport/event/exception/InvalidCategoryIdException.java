package com.furkan.ticketport.event.exception;

public class InvalidCategoryIdException extends RuntimeException {
    public InvalidCategoryIdException(String message) {
        super(message);
    }
}
