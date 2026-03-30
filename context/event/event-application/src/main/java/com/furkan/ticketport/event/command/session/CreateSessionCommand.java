package com.furkan.ticketport.event.command.session;

import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Money;

import java.time.Instant;

public record CreateSessionCommand(EventId eventId, int capacity, Money money, Instant startedAt, Instant endsAt) {
}
