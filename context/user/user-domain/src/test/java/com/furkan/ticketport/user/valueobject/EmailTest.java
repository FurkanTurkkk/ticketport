package com.furkan.ticketport.user.valueobject;

import com.furkan.ticketport.user.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailTest {

    @Test
    void acceptsValidEmail() {
        assertEquals("a@b.co", Email.valueOf("a@b.co").asString());
    }

    @Test
    void rejectsNullOrBlank() {
        assertThrows(InvalidEmailException.class, () -> Email.valueOf(null));
        assertThrows(InvalidEmailException.class, () -> Email.valueOf("   "));
    }

    @Test
    void rejectsInvalidFormat() {
        assertThrows(InvalidEmailException.class, () -> Email.valueOf("not-an-email"));
    }
}
