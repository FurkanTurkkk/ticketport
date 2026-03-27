package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidEventIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EventIdTest {

    @Test
    void randomAndValueOf() {
        EventId id = EventId.random();
        assertNotNull(id.asString());
        assertFalse(id.asString().isEmpty());
        assertEquals("e1", EventId.valueOf("e1").asString());
    }

    @Test
    void rejectsNullOrEmpty() {
        assertThrows(InvalidEventIdException.class, () -> EventId.valueOf(null));
        assertThrows(InvalidEventIdException.class, () -> EventId.valueOf(""));
    }
}
