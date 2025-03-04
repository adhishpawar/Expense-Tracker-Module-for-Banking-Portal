package com.adhish.Expense.Tracker.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Jws<Claims> validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(SECRET_KEY)  // Updated method in JJWT 0.12.6
                    .build()
                    .parseSignedClaims(token);
        } catch (JwtException e) {
            return null; // Token is invalid
        }
    }
}
