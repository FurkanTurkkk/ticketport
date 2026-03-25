package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.in.event.CreateEventUseCase;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.valueobject.CoverImageRef;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.stereotype.Service;

@Service
public class CreateEventService implements CreateEventUseCase {

    private final EventPersistencePort eventPersistencePort;

    public CreateEventService(EventPersistencePort eventPersistencePort) {
        this.eventPersistencePort = eventPersistencePort;
    }

    @Override
    public EventId execute(CreateEventCommand cmd) {
        EventId newId = EventId.random();
        Event event = toDraftEvent(newId, cmd);
        return eventPersistencePort.save(event);
    }

    private static Event toDraftEvent(EventId newId, CreateEventCommand cmd) {
        boolean hasDescription = cmd.description() != null && !cmd.description().isBlank();
        boolean hasCover = cmd.coverImageRef() != null && !cmd.coverImageRef().isBlank();

        if (hasCover) {
            CoverImageRef cover = CoverImageRef.valueOf(cmd.coverImageRef());
            return Event.createWithCover(
                    newId, cmd.categoryId(), cmd.title(), hasDescription ? cmd.description() : null, cover);
        }
        if (hasDescription) {
            return Event.createWithDescription(newId, cmd.categoryId(), cmd.title(), cmd.description());
        }
        return Event.create(newId, cmd.categoryId(), cmd.title());
    }
}
