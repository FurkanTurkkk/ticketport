package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.port.out.HasherPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class HasherAdapter implements HasherPort {

    private final PasswordEncoder passwordEncoder;

    public HasherAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean match(String raw, String hashed) {
        return passwordEncoder.matches(raw, hashed);
    }
}
