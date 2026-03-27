package com.furkan.ticketport.event.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EventStatusTest {

    @Test
    void values() {
        assertEquals(2, EventStatus.values().length);
        assertEquals(EventStatus.DRAFT, EventStatus.valueOf("DRAFT"));
        assertEquals(EventStatus.PUBLISHED, EventStatus.valueOf("PUBLISHED"));
    }
}
