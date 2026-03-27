package com.furkan.ticketport.event.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Domain modülünde yalnızca başka bağlamlarda fırlatılan istisnaların kurucuları JaCoCo için burada çağrılır.
 */
class ExceptionMessagePropagationTest {

    @Test
    void constructorsRetainMessage() {
        assertEquals("a", new CategoryNotFoundException("a").getMessage());
        assertEquals("b", new EventNotFoundException("b").getMessage());
        assertEquals("c", new EventNotPublishedException("c").getMessage());
        assertEquals("d", new SessionNotFoundException("d").getMessage());
        assertEquals("e", new UniqueSlugAllocationException("e").getMessage());
    }
}
