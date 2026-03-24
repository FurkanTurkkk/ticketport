package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidSlugException;

import java.util.regex.Pattern;

public final class Slug {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 120;
    private static final Pattern SLUG_PATTERN = Pattern.compile("^[a-z0-9]+(-[a-z0-9]+)*$");

    private final String value;

    private Slug(String value) {
        this.value = value;
    }

    public static Slug valueOf(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new InvalidSlugException("Slug cannot be null or empty");
        }
        String v = raw.trim().toLowerCase();
        if (v.length() < MIN_LENGTH || v.length() > MAX_LENGTH) {
            throw new InvalidSlugException(
                    "Slug length must be between " + MIN_LENGTH + " and " + MAX_LENGTH);
        }
        if (!SLUG_PATTERN.matcher(v).matches()) {
            throw new InvalidSlugException(
                    "Slug must be lowercase ASCII letters, digits and single hyphens between segments");
        }
        return new Slug(v);
    }

    public String asString() {
        return value;
    }
}
