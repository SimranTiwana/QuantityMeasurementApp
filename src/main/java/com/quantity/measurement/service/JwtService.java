package com.quantity.measurement.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.expiration}")
    private long expiration;

    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }

    public String generateToken(
            String email,
            String name,
            String picture
    ) {

        return Jwts.builder()

                .setSubject(email)

                .claim("name", name)

                .claim("picture", picture)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + expiration
                        )
                )

                .signWith(
                        getSigningKey(),
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    public Claims extractAllClaims(
            String token
    ) {

        return Jwts.parserBuilder()

                .setSigningKey(getSigningKey())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

    public String extractUsername(
            String token
    ) {

        return extractAllClaims(token)
                .getSubject();
    }

    public boolean isTokenValid(
            String token
    ) {

        try {

            Claims claims =
                    extractAllClaims(token);

            return claims
                    .getExpiration()
                    .after(new Date());

        } catch (Exception e) {

            return false;
        }
    }

    public User getUserFromToken(
            String token
    ) {

        String email =
                extractUsername(token);

        return new User(
                email,
                "",
                java.util.Collections.emptyList()
        );
    }
}