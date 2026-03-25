package com.furkan.ticketport.event.query.event;

import com.furkan.ticketport.event.exception.EventNotFoundException;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.in.event.GetEventQuery;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.stereotype.Service;

@Service
public class GetEventQueryService implements GetEventQuery {

    private final EventQueryPort eventQueryPort;

    public GetEventQueryService(EventQueryPort eventQueryPort) {
        this.eventQueryPort = eventQueryPort;
    }

    @Override
    public Event getById(EventId eventId) {
        return eventQueryPort
                .findByEventId(eventId)
                .orElseThrow(
                        () ->
                                new EventNotFoundException(
                                        "Event not found: " + eventId.asString()));
    }
}
