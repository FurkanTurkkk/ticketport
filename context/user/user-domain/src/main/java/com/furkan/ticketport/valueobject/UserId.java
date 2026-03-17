package com.furkan.ticketport.valueobject;

import com.furkan.ticketport.user.InvalidUserIdException;

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
        if(value == null || value.isEmpty()) {
            throw new InvalidUserIdException("User id cannot be empty");
        }
    }

    public String asString() {
        return value;
    }

}
