package com.furkan.ticketport.event.port.out.event;

import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;

import java.util.List;
import java.util.Optional;

public interface EventQueryPort {
    Optional<Event> findByEventId(EventId eventId);

    Optional<Event> findBySlug(Slug slug);

    Optional<Event> findByEventTitle(Title title);
    List<Event> findByCategory(Category category);
    List<Event> findAll();
}
