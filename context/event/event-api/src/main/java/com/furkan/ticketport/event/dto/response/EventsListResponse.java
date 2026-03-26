package com.furkan.ticketport.event.dto.response;

import com.furkan.ticketport.event.model.Event;

import java.util.List;

public record EventsListResponse(List<EventResponse> events) {

    public static EventsListResponse from(List<Event> events) {
        return new EventsListResponse(events.stream().map(EventResponse::from).toList());
    }
}
