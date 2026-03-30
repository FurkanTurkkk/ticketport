package com.furkan.ticketport.booking.valueobject;

import com.furkan.ticketport.booking.exception.InvalidBookingIdException;

import java.util.UUID;

public final class BookingId {

    private final String value;

    private BookingId(String value) {
        this.value = value;
    }

    public static BookingId random() {
        return new BookingId(UUID.randomUUID().toString());
    }

    public static BookingId valueOf(String value) {
        return new BookingId(value);
    }

    private static void validate(String value) {
        if(value == null || value.isEmpty()) {
            throw new InvalidBookingIdException("Booking ID cannot be empty");
        }
    }

    public String asString() {
        return value;
    }
}
