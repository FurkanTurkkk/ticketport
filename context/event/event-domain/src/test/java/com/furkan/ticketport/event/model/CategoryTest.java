package com.furkan.ticketport.event.model;

import com.furkan.ticketport.event.valueobject.CategoryId;
import com.furkan.ticketport.event.valueobject.CategoryType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryTest {

    @Test
    void createAndChangeType() {
        Category c = Category.create(CategoryId.valueOf("c1"), CategoryType.MOVIE);
        assertEquals("c1", c.categoryId().asString());
        assertEquals(CategoryType.MOVIE, c.categoryType());
        assertNotNull(c.createdAt());
        c.changeCategoryType(CategoryType.TRIP);
        assertEquals(CategoryType.TRIP, c.categoryType());
    }

    @Test
    void restorePreservesTimestamps() {
        Instant created = Instant.parse("2019-05-05T12:00:00Z");
        Instant updated = Instant.parse("2019-05-06T12:00:00Z");
        Category c = Category.restore(CategoryId.valueOf("c2"), CategoryType.THEATRE, created, updated);
        assertEquals(created, c.createdAt());
        assertEquals(updated, c.updatedAt());
    }
}
