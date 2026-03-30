package com.furkan.ticketport.event.model;

import com.furkan.ticketport.event.exception.InvalidSessionCapacityException;
import com.furkan.ticketport.event.exception.InvalidSessionScheduleException;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Money;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.SessionStatus;

import java.time.Instant;

public class Session {

    private SessionId sessionId;
    private EventId eventId;
    private int capacity;
    private SessionStatus status;
    private Money money;
    private Instant startedAt;
    private Instant endsAt;
    private final Instant createdAt;
    private Instant updatedAt;

    private Session(
            SessionId sessionId,
            EventId eventId,
            int capacity,
            SessionStatus status,
            Money money,
            Instant startedAt,
            Instant endsAt) {
        this.sessionId = sessionId;
        this.eventId = eventId;
        this.capacity = capacity;
        this.status = status;
        this.money = money;
        this.startedAt = startedAt;
        this.endsAt = endsAt;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    private Session(
            SessionId sessionId,
            EventId eventId,
            int capacity,
            SessionStatus status,
            Money money,
            Instant startedAt,
            Instant endsAt,
            Instant createdAt,
            Instant updatedAt) {
        this.sessionId = sessionId;
        this.eventId = eventId;
        this.capacity = capacity;
        this.status = status;
        this.money = money;
        this.startedAt = startedAt;
        this.endsAt = endsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Session create(
            SessionId sessionId, EventId eventId, int capacity, Money money, Instant startedAt, Instant endsAt
    ){
        validateCapacity(capacity);
        validateSchedule(startedAt, endsAt);
        return new Session(sessionId, eventId, capacity, SessionStatus.ON_SALE, money, startedAt, endsAt);
    }

    /** Persistanstan okuma; yeni seans için {@link #create} kullan. */
    public static Session restore(
            SessionId sessionId,
            EventId eventId,
            int capacity,
            SessionStatus status,
            Money money,
            Instant startedAt,
            Instant endsAt,
            Instant createdAt,
            Instant updatedAt) {
        validateCapacity(capacity);
        validateSchedule(startedAt, endsAt);
        return new Session(
                sessionId, eventId, capacity, status, money, startedAt, endsAt, createdAt, updatedAt);
    }

    private static void validateCapacity(int capacity) {
        if (capacity < 1) {
            throw new InvalidSessionCapacityException("Session capacity must be at least 1");
        }
    }

    private static void validateSchedule(Instant startedAt, Instant endsAt) {
        if (startedAt == null || endsAt == null) {
            throw new InvalidSessionScheduleException("Session start and end instants are required");
        }
        if (!endsAt.isAfter(startedAt)) {
            throw new InvalidSessionScheduleException("Session end must be after start");
        }
    }

    public SessionId sessionId() {
        return sessionId;
    }

    public EventId eventId() {
        return eventId;
    }

    public int capacity() {
        return capacity;
    }

    public SessionStatus status() {
        return status;
    }

    public Money money() { return money; }

    public Instant startedAt() {
        return startedAt;
    }

    public Instant endsAt() {
        return endsAt;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public void reschedule(Instant startedAt, Instant endsAt) {
        validateSchedule(startedAt, endsAt);
        this.startedAt = startedAt;
        this.endsAt = endsAt;
        refreshUpdatedAt();
    }

    public void cancel() {
        this.status = SessionStatus.CANCELLED;
        refreshUpdatedAt();
    }

    public void markSoldOut() {
        this.status = SessionStatus.SOLD_OUT;
        refreshUpdatedAt();
    }

    public void resumeSaleIfPossible() {
        if (this.status == SessionStatus.SOLD_OUT) {
            this.status = SessionStatus.ON_SALE;
            refreshUpdatedAt();
        }
    }

    private void refreshUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
