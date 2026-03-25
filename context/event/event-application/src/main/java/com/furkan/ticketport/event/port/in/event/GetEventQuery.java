package com.furkan.ticketport.event.port.in.event;

import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.EventId;

public interface GetEventQuery {

    Event getById(EventId eventId);
}
