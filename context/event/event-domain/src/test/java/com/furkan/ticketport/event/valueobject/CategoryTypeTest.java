package com.furkan.ticketport.event.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTypeTest {

    @Test
    void allConstantsExist() {
        assertEquals(3, CategoryType.values().length);
        assertEquals(CategoryType.MOVIE, CategoryType.valueOf("MOVIE"));
        assertEquals(CategoryType.TRIP, CategoryType.valueOf("TRIP"));
        assertEquals(CategoryType.THEATRE, CategoryType.valueOf("THEATRE"));
    }
}
