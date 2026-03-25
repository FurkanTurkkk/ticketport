package com.furkan.ticketport.event.port.in.session;

import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.valueobject.SessionId;

public interface CreateSessionUseCase {
    SessionId execute(CreateSessionCommand cmd);
}
