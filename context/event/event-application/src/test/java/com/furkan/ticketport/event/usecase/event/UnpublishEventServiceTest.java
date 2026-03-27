package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.UnpublishEventCommand;
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

class UnpublishEventServiceTest {

    private InMemoryEventRepository repo;
    private UnpublishEventService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryEventRepository();
        service = new UnpublishEventService(repo, repo);
    }

    @Test
    void unpublishesToDraft() {
        Event e = EventTestFixtures.draftEvent("Up");
        EventId id = e.eventId();
        e.assignSlug(Slug.valueOf("up"));
        e.publish();
        repo.save(e);

        service.execute(new UnpublishEventCommand(id));

        assertEquals(EventStatus.DRAFT, repo.findByEventId(id).orElseThrow().status());
    }

    @Test
    void rejectsMissing() {
        assertThrows(
                EventNotFoundException.class,
                () -> service.execute(new UnpublishEventCommand(EventId.valueOf("na"))));
    }
}
