package com.furkan.ticketport.user.valueobject;

import com.furkan.ticketport.user.exception.InvalidUserIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserIdTest {

    @Test
    void randomIsNonEmpty() {
        assertFalse(UserId.random().asString().isBlank());
    }

    @Test
    void valueOfTrimsEmpty() {
        assertThrows(InvalidUserIdException.class, () -> UserId.valueOf(""));
        assertThrows(InvalidUserIdException.class, () -> UserId.valueOf(null));
    }

    @Test
    void valueOfKeepsId() {
        assertEquals("u-1", UserId.valueOf("u-1").asString());
    }
}
