package com.furkan.ticketport.event.port.in.session;

import com.furkan.ticketport.event.valueobject.SessionId;

public interface DeleteSessionUseCase {
    void execute(SessionId sessionId);
}
