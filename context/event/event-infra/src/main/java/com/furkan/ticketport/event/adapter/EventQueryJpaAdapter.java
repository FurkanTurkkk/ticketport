package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.entity.EventEntity;
import com.furkan.ticketport.event.mapper.EventMapper;
import com.furkan.ticketport.event.model.Category;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import com.furkan.ticketport.event.repository.EventJpaRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Title;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EventQueryJpaAdapter implements EventQueryPort {

    private final EventJpaRepository eventJpaRepository;

    public EventQueryJpaAdapter(EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = eventJpaRepository;
    }

    @Override
    public Optional<Event> findByEventId(EventId eventId) {
        return eventJpaRepository.findById(eventId.asString()).map(EventMapper::toDomain);
    }

    @Override
    public Optional<Event> findByEventTitle(Title title) {
        List<EventEntity> matches = eventJpaRepository.findByTitle(title.asString());
        if (matches.size() != 1) {
            return Optional.empty();
        }
        return Optional.of(EventMapper.toDomain(matches.get(0)));
    }

    @Override
    public List<Event> findByCategory(Category category) {
        return eventJpaRepository.findByCategoryId(category.categoryId().asString()).stream()
                .map(EventMapper::toDomain)
                .toList();
    }

    @Override
    public List<Event> findAll() {
        return eventJpaRepository.findAll().stream().map(EventMapper::toDomain).toList();
    }
}
