package com.furkan.ticketport.event.adapter;

import com.furkan.ticketport.event.port.out.LogPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventLogAdapter implements LogPort {

    private static final Logger log = LoggerFactory.getLogger("ticketport.event.application");

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void info(String format, Object... args) {
        log.info(format, args);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

    @Override
    public void error(String format, Object... args) {
        log.error(format, args);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log.error(message, throwable);
    }
}
