package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.in.event.DeleteEventUseCase;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.stereotype.Service;

@Service
public class DeleteEventService implements DeleteEventUseCase {

    private final EventPersistencePort eventPersistencePort;
    private final EventQueryPort eventQueryPort;

    public DeleteEventService(EventPersistencePort eventPersistencePort, EventQueryPort eventQueryPort) {
        this.eventPersistencePort = eventPersistencePort;
        this.eventQueryPort = eventQueryPort;
    }

    @Override
    public void execute(EventId eventId) {
        Event event = requireExistingEvent(eventId);
        eventPersistencePort.delete(event);
    }

    private Event requireExistingEvent(EventId eventId) {
        return eventQueryPort
                .findByEventId(eventId)
                .orElseThrow(
                        () ->
                                new EventNotFoundException("Event not found: " + eventId.asString())
                );
    }
}
