package com.furkan.ticketport.user.valueobject;

import com.furkan.ticketport.user.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordTest {

    /** {@code user-application} testleriyle aynı düz metin (modüller arası test classpath yok). */
    static String validPlain() {
        return "SecureP1!";
    }

    @Test
    void valueOfAcceptsStrongPassword() {
        assertEquals(validPlain(), Password.valueOf(validPlain()).asString());
    }

    @Test
    void fromStoredHashAcceptsBcryptLikeString() {
        String hash = "$2a$10$abcdefghijklmnopqrstuvwxyz01234567890123456789012";
        assertEquals(hash, Password.fromStoredHash(hash).asString());
    }

    @Test
    void valueOfRejectsWeakOrShort() {
        assertThrows(InvalidPasswordException.class, () -> Password.valueOf("short1!"));
        assertThrows(InvalidPasswordException.class, () -> Password.valueOf("noupper1!"));
    }
}
