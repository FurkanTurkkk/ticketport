package com.furkan.ticketport.booking.model;

import com.furkan.ticketport.booking.valueobject.BookingId;
import com.furkan.ticketport.booking.valueobject.SessionId;
import com.furkan.ticketport.booking.valueobject.UserId;

import java.time.Instant;

public class Booking {

    private BookingId bookingId;
    private SessionId sessionId;
    private UserId userId;
    private Quantity quantity;
    private Money money;
    private Statu statu;
    private Instant createdAt;
    private Instant updatedAt;

}
