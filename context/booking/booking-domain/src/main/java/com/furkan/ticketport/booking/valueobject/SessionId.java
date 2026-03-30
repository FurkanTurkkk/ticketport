package com.furkan.ticketport.booking.valueobject;

import com.furkan.ticketport.booking.exception.InvalidSessionIdException;

import java.util.UUID;

public final class SessionId {

    private final String value;

    private SessionId(String value) {
        this.value = value;
    }

    public static SessionId random() {
        return new SessionId(UUID.randomUUID().toString());
    }

    public static SessionId valueOf(String value) {
        validate(value);
        return new SessionId(value);
    }

    private static void validate(String value) {
        if(value == null || value.isEmpty()) {
            throw new InvalidSessionIdException("Session ID cannot be empty");
        }
    }

    public String asString() {
        return value;
    }
}
