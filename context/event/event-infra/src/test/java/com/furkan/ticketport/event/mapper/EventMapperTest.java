package com.furkan.ticketport.event.mapper;

import com.furkan.ticketport.event.entity.EventEntity;
import com.furkan.ticketport.event.model.Event;
import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CoverImageRef;
import com.furkan.ticketport.event.valueobject.EventId;
import com.furkan.ticketport.event.valueobject.EventStatus;
import com.furkan.ticketport.event.valueobject.Slug;
import com.furkan.ticketport.event.valueobject.Title;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EventMapperTest {

    private static final CategoryId SEED_CAT =
            CategoryId.valueOf("b0000001-0000-4000-8000-000000000001");

    @Test
    void roundTripWithSlugAndCover() {
        Instant c = Instant.parse("2018-01-01T00:00:00Z");
        Instant u = Instant.parse("2018-01-02T00:00:00Z");
        Event original =
                Event.restore(
                        EventId.valueOf("me1"),
                        SEED_CAT,
                        Title.valueOf("Map Me"),
                        "map me",
                        "Desc",
                        CoverImageRef.valueOf("https://i/x"),
                        Slug.valueOf("map-me"),
                        EventStatus.PUBLISHED,
                        c,
                        u);
        EventEntity entity = EventMapper.fromDomain(original);
        Event back = EventMapper.toDomain(entity);
        assertEquals(original.eventId().asString(), back.eventId().asString());
        assertEquals("map-me", back.slug().asString());
        assertEquals("https://i/x", back.coverImage().asString());
    }

    @Test
    void toDomainMapsNullCoverAndSlug() {
        EventEntity entity =
                new EventEntity(
                        "e-null",
                        SEED_CAT.asString(),
                        "T",
                        "t",
                        null,
                        "",
                        "  ",
                        EventStatus.DRAFT,
                        Instant.now(),
                        Instant.now());
        Event e = EventMapper.toDomain(entity);
        assertNull(e.coverImage());
        assertNull(e.slug());
    }
}
