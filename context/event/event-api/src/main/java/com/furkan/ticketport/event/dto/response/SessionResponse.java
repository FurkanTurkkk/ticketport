package com.furkan.ticketport.event.dto.response;

import com.furkan.ticketport.event.model.Session;

import java.time.Instant;

public record SessionResponse(
        String id,
        String eventId,
        int capacity,
        String status,
        Instant startedAt,
        Instant endsAt,
        Instant createdAt,
        Instant updatedAt) {

    public static SessionResponse from(Session session) {
        return new SessionResponse(
                session.sessionId().asString(),
                session.eventId().asString(),
                session.capacity(),
                session.status().name(),
                session.startedAt(),
                session.endsAt(),
                session.createdAt(),
                session.updatedAt());
    }
}
