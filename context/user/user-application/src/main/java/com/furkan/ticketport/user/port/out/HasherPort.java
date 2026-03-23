package com.furkan.ticketport.user.port.out;

public interface HasherPort {
    String hash(String rawPassword);
    boolean match(String raw, String hashed);
}
