package com.furkan.ticketport.event.usecase.session;

import com.furkan.ticketport.event.exception.SessionNotFoundException;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.testsupport.InMemorySessionRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteSessionServiceTest {

    private final InMemorySessionRepository sessions = new InMemorySessionRepository();
    private DeleteSessionService service;

    @BeforeEach
    void setUp() {
        service = new DeleteSessionService(sessions, sessions);
    }

    @Test
    void deletesExisting() {
        Instant t0 = Instant.parse("2033-01-01T10:00:00Z");
        Instant t1 = Instant.parse("2033-01-01T11:00:00Z");
        SessionId sid = SessionId.valueOf("sd1");
        sessions.save(Session.create(sid, EventId.valueOf("ev2"), 2, t0, t1));

        service.execute(sid);

        assertTrue(sessions.findBySessionId(sid).isEmpty());
    }

    @Test
    void rejectsMissing() {
        assertThrows(SessionNotFoundException.class, () -> service.execute(SessionId.valueOf("missing")));
    }
}
