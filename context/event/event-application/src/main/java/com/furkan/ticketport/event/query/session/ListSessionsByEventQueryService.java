package com.furkan.ticketport.event.query.session;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.in.session.ListSessionsByEventQuery;
import com.furkan.ticketport.event.port.out.session.SessionQueryPort;
import com.furkan.ticketport.event.valueobject.EventId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListSessionsByEventQueryService implements ListSessionsByEventQuery {

    private final SessionQueryPort sessionQueryPort;

    public ListSessionsByEventQueryService(SessionQueryPort sessionQueryPort) {
        this.sessionQueryPort = sessionQueryPort;
    }

    @Override
    public List<Session> listByEventId(EventId eventId) {
        return List.copyOf(sessionQueryPort.findByEventId(eventId));
    }
}
