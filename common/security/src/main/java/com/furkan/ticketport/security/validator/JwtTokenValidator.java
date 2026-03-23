package com.furkan.ticketport.security.validator;

import com.furkan.ticketport.security.config.JwtConfig;
import com.furkan.ticketport.security.model.AuthenticatedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenValidator {

    private final String secret;

    public JwtTokenValidator(JwtConfig config) {
        this.secret = config.getSecret();
    }

    public AuthenticatedUser validate(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            String role = claims.get("role", String.class);
            String userId = claims.get("userId", String.class);
            String email = claims.get("email", String.class);
            return new AuthenticatedUser(userId,email,role);

        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
}
