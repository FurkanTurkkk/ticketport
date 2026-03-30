package com.furkan.ticketport.event.mapper;

import com.furkan.ticketport.event.entity.SessionEntity;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Money;
import com.furkan.ticketport.event.valueobject.SessionId;

import java.math.BigDecimal;
import java.util.Currency;

public final class SessionMapper {

    private SessionMapper() {}

    public static Session toDomain(SessionEntity entity) {
        Currency currency = Currency.getInstance(entity.getCurrency());
        BigDecimal raw = entity.getAmount();
        Money money =
                raw == null || raw.compareTo(BigDecimal.ZERO) == 0
                        ? Money.free(currency)
                        : Money.create(raw, currency);
        return Session.restore(
                SessionId.valueOf(entity.getId()),
                EventId.valueOf(entity.getEventId()),
                entity.getCapacity(),
                entity.getStatus(),
                money,
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
                session.money().amount(),
                session.money().currency().getCurrencyCode(),
                session.startedAt(),
                session.endsAt(),
                session.createdAt(),
                session.updatedAt());
    }
}
