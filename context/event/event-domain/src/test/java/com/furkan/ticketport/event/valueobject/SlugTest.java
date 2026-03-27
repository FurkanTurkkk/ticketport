package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidSlugException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlugTest {

    @Test
    void acceptsValidSegmentedSlug() {
        assertEquals("a-b-1", Slug.valueOf("A-B-1").asString());
    }

    @Test
    void rejectsNullBlankLengthPattern() {
        assertThrows(InvalidSlugException.class, () -> Slug.valueOf(null));
        assertThrows(InvalidSlugException.class, () -> Slug.valueOf(" "));
        assertThrows(InvalidSlugException.class, () -> Slug.valueOf("a"));
        assertThrows(InvalidSlugException.class, () -> Slug.valueOf("a".repeat(121)));
        assertThrows(InvalidSlugException.class, () -> Slug.valueOf("bad_underscore"));
        assertThrows(InvalidSlugException.class, () -> Slug.valueOf("-bad"));
        assertThrows(InvalidSlugException.class, () -> Slug.valueOf("bad-"));
    }
}
