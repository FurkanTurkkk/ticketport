package com.furkan.ticketport.event.dto.response;

import com.furkan.ticketport.event.model.Event;

import java.time.Instant;

public record EventResponse(
        String id,
        String categoryId,
        String title,
        String normalizedTitle,
        String description,
        String coverImageRef,
        String slug,
        String status,
        Instant createdAt,
        Instant updatedAt) {

    public static EventResponse from(Event event) {
        return new EventResponse(
                event.eventId().asString(),
                event.categoryId().asString(),
                event.title().asString(),
                event.normalizedTitle(),
                event.description(),
                event.coverImage() == null ? null : event.coverImage().asString(),
                event.slug() == null ? null : event.slug().asString(),
                event.status().name(),
                event.createdAt(),
                event.updatedAt());
    }
}
