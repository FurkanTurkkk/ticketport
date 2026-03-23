package com.furkan.ticketport.user.port.out;

public interface LogPort {
    void info(String message);
    void error(String message);
}
