package com.furkan.ticketport.event.entity;

import com.furkan.ticketport.event.valueobject.CategoryType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryEntityTest {

    @Test
    void noArgAndSetters() {
        CategoryEntity c = new CategoryEntity();
        Instant n = Instant.now();
        c.setId("x");
        c.setType(CategoryType.MOVIE);
        c.setCreatedAt(n);
        c.setUpdatedAt(n);
        assertEquals(CategoryType.MOVIE, c.getType());
    }

    @Test
    void fullConstructor() {
        Instant n = Instant.now();
        CategoryEntity c = new CategoryEntity("cid", CategoryType.TRIP, n, n);
        assertEquals("cid", c.getId());
    }
}
