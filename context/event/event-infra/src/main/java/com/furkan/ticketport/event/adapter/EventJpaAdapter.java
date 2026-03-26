package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.mapper.EventMapper;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.repository.EventJpaRepository;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.stereotype.Component;

@Component
public class EventJpaAdapter implements EventPersistencePort {

    private final EventJpaRepository eventJpaRepository;

    public EventJpaAdapter(EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = eventJpaRepository;
    }

    @Override
    public EventId save(Event event) {
        return EventId.valueOf(eventJpaRepository.save(EventMapper.fromDomain(event)).getId());
    }

    @Override
    public void delete(Event event) {
        eventJpaRepository.deleteById(event.eventId().asString());
    }
}
