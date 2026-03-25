package com.furkan.ticketport.event.port.out.event;

import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.EventId;

public interface EventPersistencePort {
    EventId save(Event event);
    void delete(Event event);
}
