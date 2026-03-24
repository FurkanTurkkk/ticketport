package com.furkan.ticketport.event.exception;

public class InvalidEventIdException extends RuntimeException {
    public InvalidEventIdException(String message) {
        super(message);
    }
}
