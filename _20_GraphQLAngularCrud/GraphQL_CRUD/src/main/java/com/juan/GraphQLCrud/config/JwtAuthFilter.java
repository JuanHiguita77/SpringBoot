package com.juan.GraphQLCrud.config;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.juan.GraphQLCrud.domain.entities.Token;
import com.juan.GraphQLCrud.domain.entities.User;
import com.juan.GraphQLCrud.domain.repositories.TokenRepository;
import com.juan.GraphQLCrud.domain.repositories.UserRepository;
import com.juan.GraphQLCrud.infraestructure.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor    
public class JwtAuthFilter extends OncePerRequestFilter {

    //Se ejecuta el filtro por cada petición
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;


    //Filtro para validar el token de autenticación de los usuarios autenticados
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException 
    {
        if(request.getServletPath().contains("/graphql")) {
            filterChain.doFilter(request, response); //No se aplica el filtro, se pasa al siguiente filtro (o al controlador, response); 
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response); //No se aplica el filtro, se pasa al siguiente filtro (o al controlador, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt);

        if(username == null || SecurityContextHolder.getContext().getAuthentication() != null)
            return;

        final Token token = tokenRepository.findByToken(jwt).orElseThrow(() -> new RuntimeException("Token not found"));

        if(token == null || token.isExpired() || token.isRevoked())
        {
            filterChain.doFilter(request, response);
            return;
        }

        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        final Optional<User> user = userRepository.findByEmail(userDetails.getUsername());

        //Si el usuario no existe, se pasa al siguiente filtro
        if(user.isEmpty())
        {
            filterChain.doFilter(request, response);
            return;
        }

        //Si el token no es valido, se pasa al siguiente filtro
        final boolean isTokenValid = jwtService.isTokenValid(jwt, user.get());

        if(!isTokenValid)
            return;

        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());    

        //Se guarda el token en el contexto de seguridad para que pueda ser utilizado en el controlador
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        //Se pasa al siguiente filtro
        filterChain.doFilter(request, response);
    }
}
