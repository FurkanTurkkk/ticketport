package com.furkan.ticketport.event.port.out;

public interface LogPort {

    void info(String message);

    /**
     * SLF4J-style message; use {@code {}} placeholders for arguments.
     */
    void info(String format, Object... args);

    void error(String message);

    void error(String format, Object... args);

    void error(String message, Throwable throwable);
}
