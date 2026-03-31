package com.furkan.ticketport.booking.model;

import com.furkan.ticketport.booking.exception.InvalidBookingStateException;
import com.furkan.ticketport.booking.valueobject.BookingId;
import com.furkan.ticketport.booking.valueobject.Money;
import com.furkan.ticketport.booking.valueobject.Quantity;
import com.furkan.ticketport.booking.valueobject.SessionId;
import com.furkan.ticketport.booking.valueobject.Status;
import com.furkan.ticketport.booking.valueobject.UserId;

import java.time.Instant;

public class Booking {

    private final BookingId bookingId;
    private final SessionId sessionId;
    private final UserId userId;
    private final Quantity quantity;
    private final Money total;
    private Status status;
    private final Instant createdAt;
    private Instant updatedAt;

    private Booking(
            BookingId bookingId,
            SessionId sessionId,
            UserId userId,
            Quantity quantity,
            Money total,
            Status status,
            Instant createdAt,
            Instant updatedAt) {
        this.bookingId = bookingId;
        this.sessionId = sessionId;
        this.userId = userId;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    /** Yeni rezervasyon; durum daima {@link Status#PENDING_PAYMENT}. {@code total} snapshot (uygulama hesaplar). */
    public static Booking place(
            BookingId bookingId, SessionId sessionId, UserId userId, Quantity quantity, Money total) {
        Instant now = Instant.now();
        return new Booking(
                bookingId, sessionId, userId, quantity, total, Status.PENDING_PAYMENT, now, now);
    }

    /**
     * Veritabanından okuma. {@link #place} ile kurulumdan farklı olarak kayıtlı {@code status} ve zamanlar geri yüklenir.
     */
    public static Booking restore(
            BookingId bookingId,
            SessionId sessionId,
            UserId userId,
            Quantity quantity,
            Money total,
            Status status,
            Instant createdAt,
            Instant updatedAt) {
        return new Booking(bookingId, sessionId, userId, quantity, total, status, createdAt, updatedAt);
    }

    public BookingId bookingId() {
        return bookingId;
    }

    public SessionId sessionId() {
        return sessionId;
    }

    public UserId userId() {
        return userId;
    }

    public Quantity quantity() {
        return quantity;
    }

    /** Rezervasyon anındaki ödenecek toplam (snapshot). */
    public Money total() {
        return total;
    }

    public Status status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public void confirmPayment() {
        ensurePending("Only a pending booking can be confirmed after payment");
        this.status = Status.CONFIRMED;
        refreshUpdatedAt();
    }

    public void cancel() {
        ensurePending("Only a pending booking can be cancelled");
        this.status = Status.CANCELLED;
        refreshUpdatedAt();
    }

    public void expire() {
        ensurePending("Only a pending booking can expire");
        this.status = Status.EXPIRED;
        refreshUpdatedAt();
    }

    private void ensurePending(String message) {
        if (status != Status.PENDING_PAYMENT) {
            throw new InvalidBookingStateException(message + " (current: " + status + ")");
        }
    }

    private void refreshUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
