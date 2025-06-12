package com.auth_service.auth_service.infraestructure.services;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtService {

    private final Key secret;

    public JwtService(@Value("${jwt.secret}") String secretKey) 
    {
        // Decode the JWT secret key from the application properties file (e.g., application.yml or application.properties).
        // The @Value annotation injects the value of the "jwt.secret" property into the secretKey parameter.
        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(
            StandardCharsets.UTF_8)); // Ensures the string is encoded in UTF-8 before decoding.
        
        // Generate a cryptographic key using the decoded secret.
        // This key will be used for signing or verifying JWTs (JSON Web Tokens).
        this.secret = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email, String role)
    {
        return Jwts.builder()
            .subject(email)
            .claim("role", role) // Add a custom claim to the JWT with the user's role.
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set the expiration time to 10 hours from now.
            .signWith(secret) // Sign the JWT with the secret key.
            .compact(); // Create the JWT and return it as a compact string.
    }

    /**
     * Validates the provided JSON Web Token (JWT).
     *
     * This method parses and validates the JWT using a secret key. It ensures
     * that the token is properly signed and has not been tampered with. If the
     * token is invalid, appropriate exceptions are thrown.
     *
     * @param token The JWT to be validated.
     * @throws JwtException If the token is invalid or cannot be parsed.
     * @throws SignatureException If the JWT signature is invalid.
     */
    public void validateToken(String token) 
    {
        System.out.println("Validating token: " + token); // Log the token being validated.
        try {
            Jwts.parser().verifyWith((SecretKey) secret)
                .build()
                .parseSignedClaims(token); // Parse and validate the JWT using the secret key.
        }
        catch (SignatureException e) 
        {
            throw new JwtException("Invalid JWT signature"); // Handle invalid signature exception.
        }
        catch (JwtException e) 
        {
            throw new JwtException("Invalid JWT token"); // Handle invalid JWT token exception.
        } 
    }

}
