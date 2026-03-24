package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidCoverImageRefException;


public final class CoverImageRef {

    private static final int MAX_LENGTH = 2048;

    private final String value;

    private CoverImageRef(String value) {
        this.value = value;
    }

    public static CoverImageRef valueOf(String value) {
        validate(value);
        return new CoverImageRef(value.trim());
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidCoverImageRefException("Cover image reference cannot be null or empty");
        }
        if (value.length() > MAX_LENGTH) {
            throw new InvalidCoverImageRefException(
                    "Cover image reference length cannot be greater than " + MAX_LENGTH);
        }
    }

    public String asString() {
        return value;
    }
}
