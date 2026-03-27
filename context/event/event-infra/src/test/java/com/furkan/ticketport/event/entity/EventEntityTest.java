package com.furkan.ticketport.event.entity;

import com.furkan.ticketport.event.valueobject.EventStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EventEntityTest {

    @Test
    void noArgAndSetters() {
        EventEntity e = new EventEntity();
        assertNull(e.getId());
        Instant n = Instant.now();
        e.setId("i");
        e.setCategoryId("c");
        e.setTitle("t");
        e.setNormalizedTitle("nt");
        e.setDescription("d");
        e.setCoverImageRef("ref");
        e.setSlug("s");
        e.setStatus(EventStatus.PUBLISHED);
        e.setCreatedAt(n);
        e.setUpdatedAt(n);
        assertEquals("i", e.getId());
        assertEquals(EventStatus.PUBLISHED, e.getStatus());
    }

    @Test
    void fullConstructor() {
        Instant n = Instant.now();
        EventEntity e =
                new EventEntity("id", "cat", "T", "nt", "d", "cov", "sl", EventStatus.DRAFT, n, n);
        assertEquals("sl", e.getSlug());
    }
}
