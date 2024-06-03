package com.riwi.beautySalon.infraestructure.helpers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserDetailsService userdetailsService;

    //Controlar los datos que llegan a security y lo que responden desde adentro
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
    {
        final String token = getTokenFromRequest(request);

        if (token == null) 
        {
            filterChain.doFilter(request, response);//filtros propios de security

            return;
        }

        //Obtener el user del token
        String userName = this.jwtService.getUsernameFromToken(token);

        //Si no lo encuentra en el contexto de spring Security
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) 
        {
            //Obtener usuario por username a partir del repositorio
            UserDetails userDetails = userdetailsService.loadUserByUsername(userName);

            if(this.jwtService.isTokenValid(token, userDetails))
            {
                //Toma el tipo de dato que usando var
               var authToken = new UsernamePasswordAuthenticationToken(userName, null, userDetails.getAuthorities());

               //Establece detalles adicionales como la ip, ubicacion con la ip
               authToken.setDetails(new WebAuthenticationDetails(request));

               //Registra el token de autenticacion para el tiempo que dure la solicitud del usuario(osea lo que dure el proceso de los filtros)
               SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }

    //Obtener el token del request
    public String getTokenFromRequest(HttpServletRequest request)
    {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Los tokens llegan con la palabra bearer al principio, por lo tanto debemos quitarla para enviar solo el token
        if(StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }

        return authHeader;
    }
}
