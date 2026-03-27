package com.furkan.ticketport.event.exception;

public class UniqueSlugAllocationException extends RuntimeException {
    public UniqueSlugAllocationException(String message) {
        super(message);
    }
}
