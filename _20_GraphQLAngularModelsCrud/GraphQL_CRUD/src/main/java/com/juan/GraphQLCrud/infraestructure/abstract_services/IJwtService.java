package com.juan.GraphQLCrud.infraestructure.abstract_services;

import java.util.Date;
import org.springframework.stereotype.Service;
import com.juan.GraphQLCrud.domain.entities.User;

@Service
public interface IJwtService {
    String generateToken(User user);
    String generateRefreshToken(User user);
    String buildToken(User user, long expiration);
    String extractUsername(final String token);
    boolean isTokenValid(final String token, User user);
    boolean isTokenExpired(final String token);
    Date extractExpiration(final String token);
}
