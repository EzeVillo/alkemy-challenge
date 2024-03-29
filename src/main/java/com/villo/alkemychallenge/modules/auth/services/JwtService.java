package com.villo.alkemychallenge.modules.auth.services;

import com.villo.alkemychallenge.modules.auth.helpers.JwtTokenHelper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtTokenHelper jwtTokenHelper;

    public String getToken(final UserDetails user) {
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(jwtTokenHelper.getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
