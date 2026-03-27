package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidSessionIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SessionIdTest {

    @Test
    void randomAndValueOf() {
        assertNotNull(SessionId.random().asString());
        SessionId.valueOf("sid");
    }

    @Test
    void rejectsNullOrEmpty() {
        assertThrows(InvalidSessionIdException.class, () -> SessionId.valueOf(null));
        assertThrows(InvalidSessionIdException.class, () -> SessionId.valueOf(""));
    }
}
