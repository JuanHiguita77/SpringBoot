package com.juan.GraphQLCrud.infraestructure.services;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.juan.GraphQLCrud.domain.entities.User;
import com.juan.GraphQLCrud.infraestructure.abstract_services.IJwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements IJwtService {

    @Value("${app.security.secret-key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    @Override
    public String generateToken(User user) {
        return buildToken(user, expiration);
    }

    @Override
    public String generateRefreshToken(User user) {
        return buildToken(user, refreshExpiration);
    }

    @Override
    public String buildToken(User user, long expiration) {
        return Jwts.builder()
                    .id(user.getId().toString())
                    .claims(Map.of("name", user.getName()))
                    .subject(user.getEmail())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSignInkey())
                    .compact();
    }
    
    private SecretKey getSignInkey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(final String token){
        final Claims jwtToken = Jwts.parser()
        .verifyWith(getSignInkey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

        return jwtToken.getSubject();
    }

    @Override
    public boolean isTokenValid(String token, User user) {
        final String username = extractUsername(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        final Claims jwtToken = Jwts.parser()
        .verifyWith(getSignInkey())
        .build()
        .parseSignedClaims(token)
        .getPayload();

        return jwtToken.getExpiration();
    }
}
