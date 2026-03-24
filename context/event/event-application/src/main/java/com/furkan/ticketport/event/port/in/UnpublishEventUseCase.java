package com.furkan.ticketport.event.port.in;

import com.furkan.ticketport.event.command.UnpublishEventCommand;

public interface UnpublishEventUseCase {
    void execute(UnpublishEventCommand cmd);
}
