package com.furkan.ticketport.event.dto.request;

import com.furkan.ticketport.event.command.session.CreateSessionCommand;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Money;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

public record CreateSessionRequest(
        String eventId, int capacity, BigDecimal amount, String currency, Instant startedAt, Instant endsAt) {

    public CreateSessionCommand toCommand() {
        Currency curr = Currency.getInstance(currency);
        Money money =
                amount == null || amount.compareTo(BigDecimal.ZERO) == 0
                        ? Money.free(curr)
                        : Money.create(amount, curr);
        return new CreateSessionCommand(EventId.valueOf(eventId), capacity, money, startedAt, endsAt);
    }
}
