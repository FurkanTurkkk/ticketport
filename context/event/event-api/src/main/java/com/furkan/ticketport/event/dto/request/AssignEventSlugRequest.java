package com.furkan.ticketport.event.dto.request;

import com.furkan.ticketport.event.command.event.AssignEventSlugCommand;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Slug;

public record AssignEventSlugRequest(String slug) {

    public AssignEventSlugCommand toCommand(String eventId) {
        return new AssignEventSlugCommand(EventId.valueOf(eventId), Slug.valueOf(slug));
    }
}
