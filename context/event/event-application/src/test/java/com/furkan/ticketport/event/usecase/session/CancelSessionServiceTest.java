package com.furkan.ticketport.event.usecase.session;

import com.furkan.ticketport.event.command.session.CancelSessionCommand;
import com.furkan.ticketport.event.exception.SessionNotFoundException;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.testsupport.InMemorySessionRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.SessionStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CancelSessionServiceTest {

    private final InMemorySessionRepository sessions = new InMemorySessionRepository();
    private CancelSessionService service;

    @BeforeEach
    void setUp() {
        service = new CancelSessionService(sessions, sessions);
    }

    @Test
    void cancelsExisting() {
        Instant t0 = Instant.parse("2032-01-01T10:00:00Z");
        Instant t1 = Instant.parse("2032-01-01T11:00:00Z");
        Session s = Session.create(SessionId.valueOf("sc1"), EventId.valueOf("ev1"), 4, t0, t1);
        sessions.save(s);

        service.execute(new CancelSessionCommand(SessionId.valueOf("sc1")));

        assertEquals(SessionStatus.CANCELLED, sessions.findBySessionId(SessionId.valueOf("sc1")).orElseThrow().status());
    }

    @Test
    void requireExistingSessionThrows() {
        assertThrows(
                SessionNotFoundException.class,
                () -> service.requireExistingSession(SessionId.valueOf("ghost")));
    }
}
