package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.AssignEventSlugCommand;
import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.testsupport.InMemoryEventRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssignEventSlugServiceTest {

    private InMemoryEventRepository repo;
    private AssignEventSlugService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryEventRepository();
        service = new AssignEventSlugService(repo, repo);
    }

    @Test
    void assignsSlug() {
        Event e = EventTestFixtures.draftEvent("Sluggy");
        EventId id = e.eventId();
        repo.save(e);

        service.execute(new AssignEventSlugCommand(id, Slug.valueOf("new-slug")));

        assertEquals("new-slug", repo.findByEventId(id).orElseThrow().slug().asString());
    }

    @Test
    void rejectsMissingEvent() {
        assertThrows(
                EventNotFoundException.class,
                () ->
                        service.execute(
                                new AssignEventSlugCommand(
                                        EventId.valueOf("absent"), Slug.valueOf("sl"))));
    }
}
