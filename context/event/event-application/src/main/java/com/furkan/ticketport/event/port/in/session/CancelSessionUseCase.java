package com.furkan.ticketport.event.port.in.session;

import com.furkan.ticketport.event.command.session.CancelSessionCommand;

public interface CancelSessionUseCase {
    void execute(CancelSessionCommand cmd);
}
