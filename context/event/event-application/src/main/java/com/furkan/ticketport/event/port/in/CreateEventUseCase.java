package com.furkan.ticketport.event.port.in;

import com.furkan.ticketport.event.command.CreateEventCommand;
import com.furkan.ticketport.event.valueobject.EventId;

public interface CreateEventUseCase {
    EventId execute(CreateEventCommand cmd);
}
