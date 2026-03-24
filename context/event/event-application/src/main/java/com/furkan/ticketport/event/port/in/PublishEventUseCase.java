package com.furkan.ticketport.event.port.in;

import com.furkan.ticketport.event.command.PublishEventCommand;

public interface PublishEventUseCase {
    void execute(PublishEventCommand cmd);
}
