package com.furkan.ticketport.event.exception;

public class EventNotPublishedException extends RuntimeException {
    public EventNotPublishedException(String message) {
        super(message);
    }
}
