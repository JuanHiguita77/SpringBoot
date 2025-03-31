package com.juan.GraphQLCrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.juan.GraphQLCrud.domain.entities.Token;
import com.juan.GraphQLCrud.domain.repositories.TokenRepository;
import com.juan.GraphQLCrud.infraestructure.services.authentication.AuthService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtFilter;
    private final AuthenticationProvider authenticationProdiver;
    private final TokenRepository tokenRepository;
    private final AuthService authService;
       
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable) // ❌ CSRF no es necesario en APIs
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/graphql").permitAll()  // 🔥 Permite acceso sin autenticación a GraphQL
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 🔐 Usamos JWT, sin sesiones
        )
        .authenticationProvider(authenticationProdiver) // ✅ Ahora está en el lugar correcto
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
               
        return http.build();
    }

}

