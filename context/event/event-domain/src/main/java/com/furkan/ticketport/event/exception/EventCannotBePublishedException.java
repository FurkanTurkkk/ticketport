package com.furkan.ticketport.event.exception;

public class EventCannotBePublishedException extends RuntimeException {
    public EventCannotBePublishedException(String message) {
        super(message);
    }
}
