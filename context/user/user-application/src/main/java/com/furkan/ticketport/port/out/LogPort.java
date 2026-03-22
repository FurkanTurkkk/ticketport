package com.furkan.ticketport.port.out;

public interface LogPort {
    void info(String message);
    void error(String message);
}
