package com.furkan.ticketport.event.testsupport;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class InMemoryEventRepository implements EventPersistencePort, EventQueryPort {

    private final Map<String, Event> events = new LinkedHashMap<>();

    @Override
    public EventId save(Event event) {
        events.put(event.eventId().asString(), event);
        return event.eventId();
    }

    @Override
    public void delete(Event event) {
        events.remove(event.eventId().asString());
    }

    @Override
    public Optional<Event> findByEventId(EventId eventId) {
        return Optional.ofNullable(events.get(eventId.asString()));
    }

    @Override
    public Optional<Event> findBySlug(Slug slug) {
        return events.values().stream()
                .filter(e -> e.slug() != null && e.slug().asString().equals(slug.asString()))
                .findFirst();
    }

    @Override
    public Optional<Event> findByEventTitle(Title title) {
        List<Event> matches =
                events.values().stream()
                        .filter(e -> e.title().asString().equals(title.asString()))
                        .toList();
        if (matches.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(matches.get(0));
    }

    @Override
    public List<Event> findByCategory(Category category) {
        return events.values().stream()
                .filter(e -> e.categoryId().asString().equals(category.categoryId().asString()))
                .toList();
    }

    @Override
    public List<Event> findAll() {
        return List.copyOf(events.values());
    }
}
