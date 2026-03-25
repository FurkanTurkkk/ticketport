package com.furkan.ticketport.event.port.in.session;

import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.EventId;

import java.util.List;

public interface ListSessionsByEventQuery {

    List<Session> listByEventId(EventId eventId);
}
