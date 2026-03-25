package com.furkan.ticketport.event.command.session;

import com.furkan.ticketport.event.valueobject.EventId;

import java.time.Instant;

public record CreateSessionCommand(EventId eventId, int capacity, Instant startedAt, Instant endsAt) {
}
