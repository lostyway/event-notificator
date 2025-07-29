package com.lostway.eventnotificator.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JWTUtil {

    private final SecretKey secretKey;
    private final long expirationTime;

    public JWTUtil(
            @Value("${secret.key}") String secret,
            @Value("${expiration.time}") long expirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationTime = expirationTime;
    }

    public String validateAndGetUsername(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return claims.getBody().getSubject();
        } catch (Exception e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public String validateAndGetRole(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return claims.getBody().get("role", String.class);
        } catch (Exception e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public Long validateAndGetId(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return claims.getBody().get("userId", Long.class);
        } catch (Exception e) {
            throw new JwtException("Invalid JWT token");
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new JwtException("Invalid JWT token");
        }
    }
}
