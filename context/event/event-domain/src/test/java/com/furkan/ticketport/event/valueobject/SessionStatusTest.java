package com.furkan.ticketport.event.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SessionStatusTest {

    @Test
    void values() {
        assertEquals(3, SessionStatus.values().length);
        assertEquals(SessionStatus.ON_SALE, SessionStatus.valueOf("ON_SALE"));
        assertEquals(SessionStatus.SOLD_OUT, SessionStatus.valueOf("SOLD_OUT"));
        assertEquals(SessionStatus.CANCELLED, SessionStatus.valueOf("CANCELLED"));
    }
}
