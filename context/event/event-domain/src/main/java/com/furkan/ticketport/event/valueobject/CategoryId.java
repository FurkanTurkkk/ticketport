package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidCategoryIdException;

import java.util.UUID;

public final class CategoryId {

    private final String value;

    private CategoryId(String value) {
        this.value = value;
    }

    public static CategoryId random() {
        return new CategoryId(UUID.randomUUID().toString());
    }

    public static CategoryId valueOf(String value) {
        validate(value);
        return new CategoryId(value);
    }

    private static void validate(String value) {
        if(value == null || value.isEmpty()) {
            throw new InvalidCategoryIdException("Category ID cannot be empty");
        }
    }

    public String asString() {
        return value;
    }
}
