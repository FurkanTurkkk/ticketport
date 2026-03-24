package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidEventIdException;

import java.util.UUID;

public final class EventId {

    private final String value;

    private EventId(String value) {
        this.value = value;
    }

    public static EventId random() {
        return new EventId(UUID.randomUUID().toString());
    }

    public static EventId valueOf(String value) {
        validate(value);
        return new EventId(value);
    }

    private static void validate(String value) {
        if(value == null || value.isEmpty()) {
            throw new InvalidEventIdException("Event ID cannot be empty");
        }
    }

    public String asString() {
        return value;
    }
}
