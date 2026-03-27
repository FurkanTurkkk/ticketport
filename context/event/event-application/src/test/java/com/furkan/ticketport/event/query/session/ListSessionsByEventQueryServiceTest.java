package com.furkan.ticketport.event.query.session;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.testsupport.InMemorySessionRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListSessionsByEventQueryServiceTest {

    @Test
    void listsByEvent() {
        InMemorySessionRepository repo = new InMemorySessionRepository();
        EventId ev = EventId.valueOf("evlist");
        Instant t0 = Instant.parse("2041-01-01T09:00:00Z");
        Instant t1 = Instant.parse("2041-01-01T10:00:00Z");
        repo.save(Session.create(SessionId.valueOf("ls1"), ev, 1, t0, t1));
        repo.save(
                Session.create(
                        SessionId.valueOf("ls2"),
                        ev,
                        2,
                        Instant.parse("2041-01-02T09:00:00Z"),
                        Instant.parse("2041-01-02T10:00:00Z")));

        ListSessionsByEventQueryService q = new ListSessionsByEventQueryService(repo);
        assertEquals(2, q.listByEventId(ev).size());
    }
}
