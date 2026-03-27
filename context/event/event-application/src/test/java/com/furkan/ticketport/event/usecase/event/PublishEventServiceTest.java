package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.PublishEventCommand;
import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.testsupport.InMemoryEventRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.EventStatus;
import com.furkan.ticketport.event.valueobject.Slug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PublishEventServiceTest {

    private InMemoryEventRepository repo;
    private PublishEventService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryEventRepository();
        service = new PublishEventService(repo, repo);
    }

    @Test
    void publishesWhenSlugPresent() {
        Event e = EventTestFixtures.draftEvent("Pub");
        EventId id = e.eventId();
        e.assignSlug(Slug.valueOf("pub-ev"));
        repo.save(e);

        service.execute(new PublishEventCommand(id));

        assertEquals(EventStatus.PUBLISHED, repo.findByEventId(id).orElseThrow().status());
    }

    @Test
    void rejectsMissingEvent() {
        assertThrows(
                EventNotFoundException.class,
                () -> service.execute(new PublishEventCommand(EventId.valueOf("missing-id"))));
    }
}
