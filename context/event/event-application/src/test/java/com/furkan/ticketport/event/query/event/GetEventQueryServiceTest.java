package com.furkan.ticketport.event.query.event;

import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.testsupport.InMemoryEventRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GetEventQueryServiceTest {

    @Test
    void returnsExisting() {
        InMemoryEventRepository repo = new InMemoryEventRepository();
        Event e = EventTestFixtures.draftEvent("Q");
        repo.save(e);
        GetEventQueryService q = new GetEventQueryService(repo);

        assertEquals(e.eventId().asString(), q.getById(e.eventId()).eventId().asString());
    }

    @Test
    void throwsWhenMissing() {
        GetEventQueryService q = new GetEventQueryService(new InMemoryEventRepository());
        assertThrows(EventNotFoundException.class, () -> q.getById(EventId.valueOf("z")));
    }
}
