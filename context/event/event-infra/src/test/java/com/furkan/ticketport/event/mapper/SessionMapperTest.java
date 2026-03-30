package com.furkan.ticketport.event.mapper;

import com.furkan.ticketport.event.entity.SessionEntity;
import com.furkan.ticketport.event.model.Session;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.Money;
import com.furkan.ticketport.event.valueobject.SessionId;
import com.furkan.ticketport.event.valueobject.SessionStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionMapperTest {

    @Test
    void roundTrip() {
        Instant c = Instant.parse("2017-05-05T12:00:00Z");
        Instant u = Instant.parse("2017-05-06T12:00:00Z");
        Instant t0 = Instant.parse("2017-06-01T10:00:00Z");
        Instant t1 = Instant.parse("2017-06-01T12:00:00Z");
        Session original =
                Session.restore(
                        SessionId.valueOf("sm1"),
                        EventId.valueOf("evm"),
                        9,
                        SessionStatus.SOLD_OUT,
                        Money.create(new BigDecimal("12.5"), Currency.getInstance("TRY")),
                        t0,
                        t1,
                        c,
                        u);
        SessionEntity entity = SessionMapper.fromDomain(original);
        Session back = SessionMapper.toDomain(entity);
        assertEquals(original.sessionId().asString(), back.sessionId().asString());
        assertEquals(SessionStatus.SOLD_OUT, back.status());
    }
}
