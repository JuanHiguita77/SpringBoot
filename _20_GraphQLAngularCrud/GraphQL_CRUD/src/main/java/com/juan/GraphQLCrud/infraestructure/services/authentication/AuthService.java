package com.juan.GraphQLCrud.infraestructure.services.authentication;

import java.util.List;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.juan.GraphQLCrud.api.dto.user.LoginRequest;
import com.juan.GraphQLCrud.api.dto.user.RegisterRequest;
import com.juan.GraphQLCrud.api.dto.user.TokenResponse;
import com.juan.GraphQLCrud.domain.entities.Token;
import com.juan.GraphQLCrud.domain.entities.User;
import com.juan.GraphQLCrud.domain.repositories.TokenRepository;
import com.juan.GraphQLCrud.domain.repositories.UserRepository;
import com.juan.GraphQLCrud.infraestructure.services.JwtService;
import com.juan.GraphQLCrud.util.enums.TokenType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor//Genera el constructor para los final y nonNull
public class AuthService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    //Necesitamos devolver el token, hacemos uso del JwtService
    private final JwtService jwtService;

    public TokenResponse register(RegisterRequest request) {

        //Generamos el usuario con contraseña encriptada
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        User savedUser = userRepository.save(user);

        //Generamos el token
        String token = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        saveUserToken(savedUser, token);

        return new TokenResponse(token, refreshToken);
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, token);

        return new TokenResponse(token, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token tokenEntity = Token.builder()
                                .user(user)
                                .token(jwtToken)
                                .type(TokenType.BEARER)
                                .expired(false)
                                .revoked(false)
                                .build();

        tokenRepository.save(tokenEntity);
    }

    private void revokeAllUserTokens(User user) {

        final List<Token> validUserTokens = tokenRepository.findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());

        if(!validUserTokens.isEmpty()) {
            for(final Token token : validUserTokens) {
                token.setExpired(true);
                token.setRevoked(true);
            }
        }

        tokenRepository.saveAll(validUserTokens);
    }

    public TokenResponse refreshToken(final String authHeader)
    {
        if(authHeader == null || !authHeader.startsWith("Bearer "))
            throw new IllegalArgumentException("Invalid Bearer Token");

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);
        
        if(userEmail == null)
            throw new IllegalArgumentException("Invalid Bearer Token");

        final User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        if(!jwtService.isTokenValid(refreshToken, user))
            throw new IllegalArgumentException("Invalid refresh Token");

        final String accessToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new TokenResponse(accessToken, refreshToken);
    }

    public void logout(final String token){
        if(token == null || !token.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid Token");
        }

        final String jwtToken = token.substring(7);
        final Token foundToken = tokenRepository.findByToken(jwtToken).orElseThrow(() -> new RuntimeException("Token not found"));
        
        foundToken.setExpired(true);
        foundToken.setRevoked(true);

        tokenRepository.save(foundToken);
    }
}
