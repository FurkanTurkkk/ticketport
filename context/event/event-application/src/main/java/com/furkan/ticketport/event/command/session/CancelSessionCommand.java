package com.furkan.ticketport.event.command.session;

import com.furkan.ticketport.event.valueobject.SessionId;

public record CancelSessionCommand(SessionId sessionId) {
}
