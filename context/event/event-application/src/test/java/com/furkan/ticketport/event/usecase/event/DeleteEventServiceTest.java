package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.testsupport.InMemoryEventRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteEventServiceTest {

    private InMemoryEventRepository repo;
    private DeleteEventService service;

    @BeforeEach
    void setUp() {
        repo = new InMemoryEventRepository();
        service = new DeleteEventService(repo, repo);
    }

    @Test
    void deletesExisting() {
        Event e = EventTestFixtures.draftEvent("Del");
        EventId id = e.eventId();
        repo.save(e);
        service.execute(id);
        assertTrue(repo.findByEventId(id).isEmpty());
    }

    @Test
    void rejectsMissing() {
        assertThrows(EventNotFoundException.class, () -> service.execute(EventId.valueOf("nope")));
    }
}
