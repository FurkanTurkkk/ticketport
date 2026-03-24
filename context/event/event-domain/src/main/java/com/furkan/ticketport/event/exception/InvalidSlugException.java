package com.furkan.ticketport.event.exception;

public class InvalidSlugException extends RuntimeException {
    public InvalidSlugException(String message) {
        super(message);
    }
}
