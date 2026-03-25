package com.furkan.ticketport.event.port.in.event;

import com.furkan.ticketport.event.command.event.CreateEventCommand;
import com.furkan.ticketport.event.valueobject.EventId;

public interface CreateEventUseCase {
    EventId execute(CreateEventCommand cmd);
}
