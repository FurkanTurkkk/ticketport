package com.furkan.ticketport.event.adapter;

import org.junit.jupiter.api.Test;

class EventLogAdapterTest {

    @Test
    void invokesAllLogOverloadersWithoutException() {
        EventLogAdapter adapter = new EventLogAdapter();
        adapter.info("plain");
        adapter.info("f {}", "a");
        adapter.error("e");
        adapter.error("e {}", "x");
        adapter.error("ex", new RuntimeException("r"));
    }
}
