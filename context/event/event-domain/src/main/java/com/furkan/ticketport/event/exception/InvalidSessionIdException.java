package com.furkan.ticketport.event.exception;

public class InvalidSessionIdException extends RuntimeException {
    public InvalidSessionIdException(String message) {
        super(message);
    }
}
