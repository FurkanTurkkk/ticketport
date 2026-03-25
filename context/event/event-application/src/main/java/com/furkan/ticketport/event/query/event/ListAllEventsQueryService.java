package com.furkan.ticketport.event.query.event;

import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.port.in.event.ListAllEventsQuery;
import com.furkan.ticketport.event.port.out.event.EventQueryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllEventsQueryService implements ListAllEventsQuery {

    private final EventQueryPort eventQueryPort;

    public ListAllEventsQueryService(EventQueryPort eventQueryPort) {
        this.eventQueryPort = eventQueryPort;
    }

    @Override
    public List<Event> listAll() {
        return List.copyOf(eventQueryPort.findAll());
    }
}
