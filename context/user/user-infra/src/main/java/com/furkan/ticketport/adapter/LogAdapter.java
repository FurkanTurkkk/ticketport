package com.furkan.ticketport.adapter;

import com.furkan.ticketport.port.out.LogPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogAdapter implements LogPort {

    private static final Logger log = LoggerFactory.getLogger(LogAdapter.class);

    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }
}
