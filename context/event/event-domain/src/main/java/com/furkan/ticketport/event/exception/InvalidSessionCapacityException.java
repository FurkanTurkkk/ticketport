package com.furkan.ticketport.event.exception;

public class InvalidSessionCapacityException extends RuntimeException {
    public InvalidSessionCapacityException(String message) {
        super(message);
    }
}
