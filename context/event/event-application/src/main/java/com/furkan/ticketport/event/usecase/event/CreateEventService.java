package com.furkan.ticketport.event.usecase.event;

import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.exception.UniqueSlugAllocationException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.in.event.CreateEventUseCase;
import com.furkan.ticketport.event.port.out.event.EventPersistencePort;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import com.furkan.ticketport.event.slug.TitleSlugifier;
import com.furkan.ticketport.event.valueobject.CoverImageRef;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class CreateEventService implements CreateEventUseCase {

    private static final int MAX_SLUG_ATTEMPTS = 40;

    private final EventPersistencePort eventPersistencePort;
    private final EventQueryPort eventQueryPort;

    public CreateEventService(
            EventPersistencePort eventPersistencePort, EventQueryPort eventQueryPort) {
        this.eventPersistencePort = eventPersistencePort;
        this.eventQueryPort = eventQueryPort;
    }

    @Override
    public EventId execute(CreateEventCommand cmd) {
        EventId newId = EventId.random();
        Event event = toDraftEvent(newId, cmd);
        event.assignSlug(resolveUniqueSlug(cmd.title()));
        return eventPersistencePort.save(event);
    }

    private Slug resolveUniqueSlug(Title title) {
        String base = TitleSlugifier.toSlugCandidate(title.asString());
        String candidate = base;
        for (int attempt = 0; attempt < MAX_SLUG_ATTEMPTS; attempt++) {
            candidate = trimCandidate(candidate);
            Slug slug = Slug.valueOf(candidate);
            if (eventQueryPort.findBySlug(slug).isEmpty()) {
                return slug;
            }
            candidate = base + "-" + randomSuffix();
        }
        throw new UniqueSlugAllocationException("Could not allocate unique slug for title: " + title.asString());
    }

    private static String trimCandidate(String candidate) {
        if (candidate.length() > 120) {
            candidate = candidate.substring(0, 120).replaceAll("-+$", "");
        }
        if (candidate.length() < 2) {
            return "ev-" + randomSuffix();
        }
        return candidate;
    }

    private static String randomSuffix() {
        String u =
                Long.toUnsignedString(ThreadLocalRandom.current().nextLong(), 36)
                        .replace("-", "");
        return u.substring(0, Math.min(8, u.length()));
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
