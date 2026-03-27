package com.furkan.ticketport.event.testsupport;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryEventRepositoryTest {

    @Test
    void findByEventTitleEmptyWhenZeroOrManyMatches() {
        InMemoryEventRepository repo = new InMemoryEventRepository();
        Title t = Title.valueOf("Dup Title");
        assertTrue(repo.findByEventTitle(t).isEmpty());

        Event a = Event.create(EventId.valueOf("e1"), EventTestFixtures.SEED_CATEGORY, t);
        Event b = Event.create(EventId.valueOf("e2"), EventTestFixtures.SEED_CATEGORY, t);
        repo.save(a);
        repo.save(b);
        assertTrue(repo.findByEventTitle(t).isEmpty());

        repo.delete(b);
        assertEquals("e1", repo.findByEventTitle(t).orElseThrow().eventId().asString());
    }

    @Test
    void findByCategoryFilters() {
        InMemoryEventRepository repo = new InMemoryEventRepository();
        Category cat =
                Category.create(CategoryId.valueOf("catx"), CategoryType.MOVIE);
        Event e =
                Event.create(
                        EventId.valueOf("ecat"),
                        CategoryId.valueOf("catx"),
                        Title.valueOf("In Cat"));
        repo.save(e);
        assertEquals(1, repo.findByCategory(cat).size());
    }
}
