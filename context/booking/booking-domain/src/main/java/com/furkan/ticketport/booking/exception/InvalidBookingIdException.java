package com.furkan.ticketport.booking.exception;

public class InvalidBookingIdException extends RuntimeException {
    public InvalidBookingIdException(String message) {
        super(message);
    }
}
