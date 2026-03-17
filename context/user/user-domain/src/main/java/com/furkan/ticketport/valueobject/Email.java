package com.furkan.ticketport.valueobject;

import com.furkan.ticketport.user.InvalidEmailException;

import java.util.regex.Pattern;

public final class Email {

    private static final String EMAIL_REGEX =
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

    private final String value;

    private Email (String value) {
        this.value = value;
    }

    public static Email valueOf(String value) {
        validate(value);
        return new Email(value);
    }

    private static void validate(String value){
        if(value == null || value.trim().isEmpty()) {
            throw new InvalidEmailException("Email cannot be null or empty");
        }

        if(value.length() > 320) {
            throw new InvalidEmailException("Email length cannot be greater than 320");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidEmailException("Geçersiz email formatı: " + value);
        }
    }

    public String asString() {
        return value;
    }

}
