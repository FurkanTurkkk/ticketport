package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidTitleException;

import java.util.Locale;

public final class Title {

    private static final int MAX_LENGTH = 200;

    private final String value;

    private Title(String value) {
        this.value = value;
    }

    public static Title valueOf(String value) {
        validate(value);
        return new Title(value.trim());
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidTitleException("Title cannot be null or empty");
        }
        if (value.length() > MAX_LENGTH) {
            throw new InvalidTitleException("Title length cannot be greater than " + MAX_LENGTH);
        }
    }

    public String asString() {
        return value;
    }

    /**
     * Aynı kategori içinde “Karadeniz Turu” / “karadeniz  turu” eşlemesi için; persistence’da
     * {@code UNIQUE (category_id, normalized_title)} ile isteğe bağlı sert kural uygulanabilir.
     */
    public String normalizedForDuplicateCheck() {
        return value.toLowerCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
    }
}
