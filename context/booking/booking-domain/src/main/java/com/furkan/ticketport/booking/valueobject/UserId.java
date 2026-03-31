package com.furkan.ticketport.booking.valueobject;

import com.furkan.ticketport.booking.exception.InvalidUserIdException;

import java.util.UUID;

public final class UserId {

    private final String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId random() {
        return new UserId(UUID.randomUUID().toString());
    }

    public static UserId valueOf(String value) {
        validate(value);
        return new UserId(value);
    }

    private static void validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new InvalidUserIdException("User ID cannot be empty");
        }
    }

    public String asString() {
        return this.value;
    }
}
