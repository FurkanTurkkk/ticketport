package com.furkan.ticketport.event.query.event;

import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.testsupport.EventTestFixtures;
import com.furkan.ticketport.event.testsupport.InMemoryEventRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListAllEventsQueryServiceTest {

    @Test
    void listsCopyOfAll() {
        InMemoryEventRepository repo = new InMemoryEventRepository();
        Event a =
                Event.create(
                        EventId.valueOf("list-e-a"),
                        EventTestFixtures.SEED_CATEGORY,
                        Title.valueOf("A"));
        Event b =
                Event.create(
                        EventId.valueOf("list-e-b"),
                        EventTestFixtures.SEED_CATEGORY,
                        Title.valueOf("B"));
        repo.save(a);
        repo.save(b);
        ListAllEventsQueryService q = new ListAllEventsQueryService(repo);
        assertEquals(2, q.listAll().size());
    }
}
