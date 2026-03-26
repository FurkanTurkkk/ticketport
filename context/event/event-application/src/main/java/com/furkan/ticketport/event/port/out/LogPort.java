package com.furkan.ticketport.event.port.out;

public interface LogPort {

    void info(String message);

    void info(String format, Object... args);

    void error(String message);

    void error(String format, Object... args);

    void error(String message, Throwable throwable);
}
