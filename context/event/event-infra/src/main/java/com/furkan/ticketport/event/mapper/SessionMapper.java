package com.furkan.ticketport.event.mapper;

import com.furkan.ticketport.event.entity.SessionEntity;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;

public final class SessionMapper {

    private SessionMapper() {}

    public static Session toDomain(SessionEntity entity) {
        return Session.restore(
                SessionId.valueOf(entity.getId()),
                EventId.valueOf(entity.getEventId()),
                entity.getCapacity(),
                entity.getStatus(),
                entity.getStartedAt(),
                entity.getEndsAt(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static SessionEntity fromDomain(Session session) {
        return new SessionEntity(
                session.sessionId().asString(),
                session.eventId().asString(),
                session.capacity(),
                session.status(),
                session.startedAt(),
                session.endsAt(),
                session.createdAt(),
                session.updatedAt());
    }
}
