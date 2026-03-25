package com.furkan.ticketport.event.port.in.session;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.SessionId;

public interface GetSessionQuery {

    Session getById(SessionId sessionId);
}
