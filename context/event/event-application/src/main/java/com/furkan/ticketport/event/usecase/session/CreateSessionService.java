package com.furkan.ticketport.event.usecase.session;

import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.exception.EventNotPublishedException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.in.session.CreateSessionUseCase;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import com.furkan.ticketport.event.port.out.session.SessionPersistencePort;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.stereotype.Service;

@Service
public class CreateSessionService implements CreateSessionUseCase {

    private final EventQueryPort eventQueryPort;
    private final SessionPersistencePort sessionPersistencePort;

    public CreateSessionService(EventQueryPort eventQueryPort, SessionPersistencePort sessionPersistencePort) {
        this.eventQueryPort = eventQueryPort;
        this.sessionPersistencePort = sessionPersistencePort;
    }

    @Override
    public SessionId execute(CreateSessionCommand cmd) {
        Event event = requireExistingEvent(cmd.eventId());
        requirePublishedForNewSession(event);

        return sessionPersistencePort.save(sessionFrom(cmd));
    }

    private Event requireExistingEvent(EventId eventId) {
        return eventQueryPort
                .findByEventId(eventId)
                .orElseThrow(
                        () ->
                                new EventNotFoundException("Event not found: " + eventId.asString())
                );
    }

    private static void requirePublishedForNewSession(Event event) {
        if (!event.isPublished()) {
            throw new EventNotPublishedException(
                    "Sessions can only be added to published events: " + event.eventId().asString()
            );
        }
    }

    private static Session sessionFrom(CreateSessionCommand cmd) {
        return Session.create(
                SessionId.random(),
                cmd.eventId(),
                cmd.capacity(),
                cmd.startedAt(),
                cmd.endsAt());
    }
}
