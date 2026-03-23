package com.furkan.ticketport.user.adapter;

import com.furkan.ticketport.user.model.User;
import com.furkan.ticketport.user.port.out.TokenIssuerPort;
import com.furkan.ticketport.user.result.LoginResult;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoginAdapter implements TokenIssuerPort {

    @Value("${security.jwt.secret}")
    private String secret;

    @Value("${security.jwt.expiration-ms}")
    private long expirationMs;

    @Override
    public LoginResult login(User user) {

        JwtBuilder builder = Jwts.builder()
                .setSubject(user.email().asString())
                .claim("role", user.role())
                .claim("userId", user.userId().asString())
                .claim("email", user.email().asString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS256, secret);

        return new LoginResult(builder.compact());
    }
}
