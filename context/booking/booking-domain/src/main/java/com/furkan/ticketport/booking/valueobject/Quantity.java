package com.furkan.ticketport.booking.valueobject;

import com.furkan.ticketport.booking.exception.InvalidQuantityException;

public record Quantity(int value) {

    public Quantity {
        if (value < 1) {
            throw new InvalidQuantityException("Quantity must be at least 1");
        }
    }
}
