package com.furkan.ticketport.event.valueobject;

import com.furkan.ticketport.event.exception.InvalidCoverImageRefException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoverImageRefTest {

    @Test
    void trimsValue() {
        assertEquals("https://x", CoverImageRef.valueOf("  https://x  ").asString());
    }

    @Test
    void rejectsBlankOrTooLong() {
        assertThrows(InvalidCoverImageRefException.class, () -> CoverImageRef.valueOf(null));
        assertThrows(InvalidCoverImageRefException.class, () -> CoverImageRef.valueOf("  "));
        String longRef = "h".repeat(2049);
        assertThrows(InvalidCoverImageRefException.class, () -> CoverImageRef.valueOf(longRef));
    }
}
