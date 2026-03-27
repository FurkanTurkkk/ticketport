package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidTitleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TitleTest {

    @Test
    void trimsAndNormalizesForDuplicateCheck() {
        Title t = Title.valueOf("  Hello   World  ");
        assertEquals("Hello   World", t.asString());
        assertEquals("hello world", t.normalizedForDuplicateCheck());
    }

    @Test
    void rejectsNullBlankOrTooLong() {
        assertThrows(InvalidTitleException.class, () -> Title.valueOf(null));
        assertThrows(InvalidTitleException.class, () -> Title.valueOf("   "));
        String longTitle = "x".repeat(201);
        assertThrows(InvalidTitleException.class, () -> Title.valueOf(longTitle));
    }
}
