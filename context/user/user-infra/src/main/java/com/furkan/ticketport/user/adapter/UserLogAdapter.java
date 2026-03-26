package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.port.out.LogPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserLogAdapter implements LogPort {

    private static final Logger log = LoggerFactory.getLogger("ticketport.user.application");

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
