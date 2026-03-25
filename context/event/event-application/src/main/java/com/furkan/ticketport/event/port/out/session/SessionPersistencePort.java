package com.furkan.ticketport.event.port.out.session;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.SessionId;

public interface SessionPersistencePort {
    SessionId save(Session session);
    void delete(Session session);
}
