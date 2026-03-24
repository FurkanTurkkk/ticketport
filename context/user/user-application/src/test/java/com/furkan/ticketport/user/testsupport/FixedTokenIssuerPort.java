package com.furkan.ticketport.user.testsupport;

import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.out.TokenIssuerPort;
import com.furkan.ticketport.user.result.LoginResult;

/** Login use case testinde JWT üretimini sabit string ile değiştirir. */
public final class FixedTokenIssuerPort implements TokenIssuerPort {

    private final String token;

    public FixedTokenIssuerPort(String token) {
        this.token = token;
    }

    @Override
    public LoginResult login(User user) {
        return new LoginResult(token);
    }
}
