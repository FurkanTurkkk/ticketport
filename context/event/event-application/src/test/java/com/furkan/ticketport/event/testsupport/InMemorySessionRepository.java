package com.furkan.ticketport.event.testsupport;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.out.session.SessionPersistencePort;
import com.furkan.ticketport.event.port.out.session.SessionQueryPort;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class InMemorySessionRepository implements SessionPersistencePort, SessionQueryPort {

    private final Map<String, Session> sessions = new LinkedHashMap<>();

    @Override
    public SessionId save(Session session) {
        sessions.put(session.sessionId().asString(), session);
        return session.sessionId();
    }

    @Override
    public void delete(Session session) {
        sessions.remove(session.sessionId().asString());
    }

    @Override
    public Optional<Session> findBySessionId(SessionId sessionId) {
        return Optional.ofNullable(sessions.get(sessionId.asString()));
    }

    @Override
    public List<Session> findByEventId(EventId eventId) {
        return sessions.values().stream()
                .filter(s -> s.eventId().asString().equals(eventId.asString()))
                .toList();
    }
}
