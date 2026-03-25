package com.furkan.ticketport.event.port.out.session;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;

import java.util.List;
import java.util.Optional;

public interface SessionQueryPort {
    Optional<Session> findBySessionId(SessionId sessionId);
    List<Session> findByEventId(EventId eventId);
}
