package com.furkan.ticketport.event.mapper;

import com.furkan.ticketport.event.entity.EventEntity;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CoverImageRef;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;

public final class EventMapper {

    private EventMapper() {}

    public static Event toDomain(EventEntity entity) {
        return Event.restore(
                EventId.valueOf(entity.getId()),
                CategoryId.valueOf(entity.getCategoryId()),
                Title.valueOf(entity.getTitle()),
                entity.getNormalizedTitle(),
                entity.getDescription(),
                toCoverImageRef(entity.getCoverImageRef()),
                toSlug(entity.getSlug()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    public static EventEntity fromDomain(Event event) {
        return new EventEntity(
                event.eventId().asString(),
                event.categoryId().asString(),
                event.title().asString(),
                event.normalizedTitle(),
                event.description(),
                event.coverImage() == null ? null : event.coverImage().asString(),
                event.slug() == null ? null : event.slug().asString(),
                event.status(),
                event.createdAt(),
                event.updatedAt());
    }

    private static CoverImageRef toCoverImageRef(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        return CoverImageRef.valueOf(raw);
    }

    private static Slug toSlug(String raw) {
        if (raw == null || raw.isBlank()) {
            return null;
        }
        return Slug.valueOf(raw);
    }
}
