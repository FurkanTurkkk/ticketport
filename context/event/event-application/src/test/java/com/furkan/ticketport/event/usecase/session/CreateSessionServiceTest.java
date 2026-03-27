package com.furkan.ticketport.event.usecase.session;

import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.exception.EventNotPublishedException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.testsupport.InMemoryEventRepository;
import com.furkan.ticketport.event.testsupport.InMemorySessionRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.Slug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreateSessionServiceTest {

    private final InMemoryEventRepository events = new InMemoryEventRepository();
    private final InMemorySessionRepository sessions = new InMemorySessionRepository();
    private CreateSessionService service;

    @BeforeEach
    void setUp() {
        service = new CreateSessionService(events, sessions);
    }

    @Test
    void createsSessionForPublishedEvent() {
        Event e = publishedFixture();
        Instant start = Instant.parse("2031-01-01T10:00:00Z");
        Instant end = Instant.parse("2031-01-01T12:00:00Z");
        SessionId sid =
                service.execute(new CreateSessionCommand(e.eventId(), 20, start, end));

        Session s = sessions.findBySessionId(sid).orElseThrow();
        assertEquals(20, s.capacity());
    }

    @Test
    void rejectsDraftEvent() {
        Event e = EventTestFixtures.draftEvent("DraftOnly");
        events.save(e);
        Instant start = Instant.parse("2031-02-01T10:00:00Z");
        Instant end = Instant.parse("2031-02-01T12:00:00Z");
        assertThrows(
                EventNotPublishedException.class,
                () -> service.execute(new CreateSessionCommand(e.eventId(), 5, start, end)));
    }

    @Test
    void rejectsMissingEvent() {
        Instant start = Instant.parse("2031-03-01T10:00:00Z");
        Instant end = Instant.parse("2031-03-01T12:00:00Z");
        assertThrows(
                EventNotFoundException.class,
                () ->
                        service.execute(
                                new CreateSessionCommand(
                                        EventId.valueOf("missing-ev"), 3, start, end)));
    }

    private Event publishedFixture() {
        Event e = EventTestFixtures.draftEvent("Published For Session");
        e.assignSlug(Slug.valueOf("sess-host"));
        e.publish();
        events.save(e);
        return e;
    }
}
