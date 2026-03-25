package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.UnpublishEventCommand;
import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.in.event.UnpublishEventUseCase;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import org.springframework.stereotype.Service;

@Service
public class UnpublishEventService implements UnpublishEventUseCase {

    private final EventQueryPort eventQueryPort;
    private final EventPersistencePort eventPersistencePort;

    public UnpublishEventService(EventQueryPort eventQueryPort, EventPersistencePort eventPersistencePort) {
        this.eventQueryPort = eventQueryPort;
        this.eventPersistencePort = eventPersistencePort;
    }

    @Override
    public void execute(UnpublishEventCommand cmd) {
        Event event =
                eventQueryPort
                        .findByEventId(cmd.eventId())
                        .orElseThrow(
                                () ->
                                        new EventNotFoundException(
                                                "Event not found: " + cmd.eventId().asString()));

        event.unpublishToDraft();
        eventPersistencePort.save(event);
    }
}
