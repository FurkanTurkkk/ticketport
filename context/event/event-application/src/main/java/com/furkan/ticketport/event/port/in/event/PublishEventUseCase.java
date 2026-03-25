package com.furkan.ticketport.event.port.in.event;

import com.furkan.ticketport.event.command.event.PublishEventCommand;

public interface PublishEventUseCase {
    void execute(PublishEventCommand cmd);
}
