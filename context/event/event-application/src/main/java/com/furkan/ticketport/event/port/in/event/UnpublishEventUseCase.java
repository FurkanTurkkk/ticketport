package com.furkan.ticketport.event.port.in.event;

import com.furkan.ticketport.event.command.event.UnpublishEventCommand;

public interface UnpublishEventUseCase {
    void execute(UnpublishEventCommand cmd);
}
