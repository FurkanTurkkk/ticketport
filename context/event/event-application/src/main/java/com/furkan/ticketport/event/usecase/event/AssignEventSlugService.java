package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.AssignEventSlugCommand;
import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.in.event.AssignEventSlugUseCase;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import org.springframework.stereotype.Service;

@Service
public class AssignEventSlugService implements AssignEventSlugUseCase {

    private final EventQueryPort eventQueryPort;
    private final EventPersistencePort eventPersistencePort;

    public AssignEventSlugService(EventQueryPort eventQueryPort, EventPersistencePort eventPersistencePort) {
        this.eventQueryPort = eventQueryPort;
        this.eventPersistencePort = eventPersistencePort;
    }

    @Override
    public void execute(AssignEventSlugCommand cmd) {
        Event event =
                eventQueryPort
                        .findByEventId(cmd.eventId())
                        .orElseThrow(
                                () ->
                                        new EventNotFoundException(
                                                "Event not found: " + cmd.eventId().asString()));

        event.assignSlug(cmd.slug());
        eventPersistencePort.save(event);
    }
}
