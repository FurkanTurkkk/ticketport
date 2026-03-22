package com.furkan.ticketport.valueobject;

import com.furkan.ticketport.exception.InvalidPasswordException;

public final class Password {

    private final String value;

    private Password(String value) {
        this.value = value;
    }

    public static Password valueOf(String value) {
        validate(value);
        return new Password(value);
    }

    public static Password fromStoredHash(String bcryptHash) {
        if (bcryptHash == null || bcryptHash.isBlank()) {
            throw new InvalidPasswordException("Stored password hash cannot be null or empty");
        }
        return new Password(bcryptHash);
    }

    private static void validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new InvalidPasswordException("Password cannot be null or empty");
        }
        if (value.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters long");
        }
        if (!value.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("Password must contain at least one uppercase letter");
        }
        if (!value.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("Password must contain at least one lowercase letter");
        }
        if (!value.matches(".*\\d.*")) {
            throw new InvalidPasswordException("Password must contain at least one digit");
        }
        if (!value.matches(".*[!@#$%^&*()].*")) {
            throw new InvalidPasswordException("Password must contain at least one special character");
        }
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Password password)) return false;
        return value.equals(password.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
