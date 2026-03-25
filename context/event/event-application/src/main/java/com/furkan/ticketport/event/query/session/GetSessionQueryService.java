package com.furkan.ticketport.event.query.session;

import com.furkan.ticketport.event.exception.SessionNotFoundException;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.port.in.session.GetSessionQuery;
import com.furkan.ticketport.event.port.out.session.SessionQueryPort;
import com.furkan.ticketport.event.valueobject.SessionId;
import org.springframework.stereotype.Service;

@Service
public class GetSessionQueryService implements GetSessionQuery {

    private final SessionQueryPort sessionQueryPort;

    public GetSessionQueryService(SessionQueryPort sessionQueryPort) {
        this.sessionQueryPort = sessionQueryPort;
    }

    @Override
    public Session getById(SessionId sessionId) {
        return sessionQueryPort
                .findBySessionId(sessionId)
                .orElseThrow(
                        () ->
                                new SessionNotFoundException(
                                        "Session not found: " + sessionId.asString()));
    }
}
