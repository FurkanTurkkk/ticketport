package com.furkan.ticketport.event.dto.request;

import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.valueobject.EventId;

import java.time.Instant;

public record CreateSessionRequest(
        String eventId, int capacity, Instant startedAt, Instant endsAt) {

    public CreateSessionCommand toCommand() {
        return new CreateSessionCommand(
                EventId.valueOf(eventId), capacity, startedAt, endsAt);
    }
}
