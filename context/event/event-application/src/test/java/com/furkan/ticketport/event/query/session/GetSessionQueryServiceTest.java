package com.furkan.ticketport.event.query.session;

import com.furkan.ticketport.event.exception.SessionNotFoundException;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.testsupport.InMemorySessionRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetSessionQueryServiceTest {

    @Test
    void returnsExisting() {
        InMemorySessionRepository repo = new InMemorySessionRepository();
        Instant t0 = Instant.parse("2040-01-01T09:00:00Z");
        Instant t1 = Instant.parse("2040-01-01T10:00:00Z");
        SessionId sid = SessionId.valueOf("qg1");
        repo.save(Session.create(sid, EventId.valueOf("evq"), 6, t0, t1));
        GetSessionQueryService q = new GetSessionQueryService(repo);
        assertEquals(sid.asString(), q.getById(sid).sessionId().asString());
    }

    @Test
    void throwsWhenMissing() {
        assertThrows(
                SessionNotFoundException.class,
                () -> new GetSessionQueryService(new InMemorySessionRepository()).getById(SessionId.valueOf("n")));
    }
}
