package com.furkan.ticketport.event.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EventApiMethodSecurityConfigTest {

    @Test
    void canConstruct() {
        assertNotNull(new EventApiMethodSecurityConfig());
    }
}
