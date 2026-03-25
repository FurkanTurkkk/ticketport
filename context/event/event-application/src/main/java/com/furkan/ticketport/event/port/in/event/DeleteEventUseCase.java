package com.furkan.ticketport.event.port.in.event;

import com.furkan.ticketport.event.valueobject.EventId;

public interface DeleteEventUseCase {
    void execute(EventId eventId);
}
