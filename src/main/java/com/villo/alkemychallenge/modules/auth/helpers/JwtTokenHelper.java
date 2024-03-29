package com.villo.alkemychallenge.modules.auth.helpers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.function.Function;

@Component
public class JwtTokenHelper {
    private final String jwtSecretKey;

    public JwtTokenHelper(@Value("${jwt.secret.key}") String jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    public <T> T getClaim(final String token, final Function<Claims, T> claimsResolver) {
        final var key = this.getKey();
        final var claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claimsResolver.apply(claims);
    }

    public Key getKey() {
        final var keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
