package com.furkan.ticketport.user.testsupport;

import com.furkan.ticketport.user.port.out.HasherPort;

/**
 * Deterministik hash: {@code hash(raw) = "HASH:" + raw}, match aynı kuralı kullanır.
 * BCrypt yok; unit testte parola akışını izole eder.
 */
public final class StubHasherPort implements HasherPort {

    @Override
    public String hash(String rawPassword) {
        return "HASH:" + rawPassword;
    }

    @Override
    public boolean match(String raw, String hashed) {
        return hashed.equals("HASH:" + raw);
    }
}
