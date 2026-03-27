package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidCategoryIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoryIdTest {

    @Test
    void randomAndValueOf() {
        assertNotNull(CategoryId.random().asString());
        CategoryId.valueOf("cid");
    }

    @Test
    void rejectsNullOrEmpty() {
        assertThrows(InvalidCategoryIdException.class, () -> CategoryId.valueOf(null));
        assertThrows(InvalidCategoryIdException.class, () -> CategoryId.valueOf(""));
    }
}
