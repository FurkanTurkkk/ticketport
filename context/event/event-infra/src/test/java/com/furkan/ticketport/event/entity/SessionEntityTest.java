package com.furkan.ticketport.event.entity;

import com.furkan.ticketport.event.valueobject.SessionStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionEntityTest {

    @Test
    void noArgAndSetters() {
        SessionEntity s = new SessionEntity();
        Instant n = Instant.now();
        s.setId("i");
        s.setEventId("e");
        s.setCapacity(4);
        s.setStatus(SessionStatus.CANCELLED);
        s.setStartedAt(n);
        s.setEndsAt(n);
        s.setCreatedAt(n);
        s.setUpdatedAt(n);
        assertEquals(4, s.getCapacity());
    }

    @Test
    void fullConstructor() {
        Instant n = Instant.now();
        SessionEntity s =
                new SessionEntity("i", "e", 2, SessionStatus.ON_SALE, null, "TRY", n, n, n, n);
        assertEquals("i", s.getId());
    }
}
