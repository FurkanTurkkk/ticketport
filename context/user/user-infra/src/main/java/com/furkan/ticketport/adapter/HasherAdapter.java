package com.furkan.ticketport.adapter;

import com.furkan.ticketport.port.out.HasherPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
