package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.mapper.SessionMapper;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.out.session.SessionPersistencePort;
import com.furkan.ticketport.event.port.out.session.SessionQueryPort;
import com.furkan.ticketport.event.repository.SessionJpaRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SessionJpaAdapter implements SessionPersistencePort, SessionQueryPort {

    private final SessionJpaRepository sessionJpaRepository;

    public SessionJpaAdapter(SessionJpaRepository sessionJpaRepository) {
        this.sessionJpaRepository = sessionJpaRepository;
    }

    @Override
    public SessionId save(Session session) {
        return SessionId.valueOf(sessionJpaRepository.save(SessionMapper.fromDomain(session)).getId());
    }

    @Override
    public void delete(Session session) {
        sessionJpaRepository.deleteById(session.sessionId().asString());
    }

    @Override
    public Optional<Session> findBySessionId(SessionId sessionId) {
        return sessionJpaRepository.findById(sessionId.asString()).map(SessionMapper::toDomain);
    }

    @Override
    public List<Session> findByEventId(EventId eventId) {
        return sessionJpaRepository.findByEventIdOrderByStartedAtAsc(eventId.asString()).stream()
                .map(SessionMapper::toDomain)
                .toList();
    }
}
