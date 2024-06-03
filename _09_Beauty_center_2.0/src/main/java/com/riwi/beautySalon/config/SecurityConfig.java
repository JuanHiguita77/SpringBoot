package com.riwi.beautySalon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.riwi.beautySalon.infraestructure.helpers.JwtAuthenticationFilter;
import com.riwi.beautySalon.utils.enums.Role;

import lombok.AllArgsConstructor;

//Configuracion de springSecurity
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig 
{
    @Autowired
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    //FILTROS

    private final String[] PUBLIC_RESOURCES = {"/services/public/get", "/auth/**"};//Rutas publicas y cuando este despues de los asteriscos es publico: rutas del controller

    private final String[] ADMIN_RESOURCES = {"/register/employee"};//Ruta solo para admins

    @Bean//El objeto retornado debe ser registrado como un bean de spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .csrf(csrf -> csrf.disable())//Desabilitar proteccion csrf ya que es para stailess y vamos a usar staifull, que es mejor
                .authorizeHttpRequests(authRequest -> authRequest
                .requestMatchers(PUBLIC_RESOURCES).permitAll()
                .requestMatchers(ADMIN_RESOURCES).hasAuthority(Role.ADMIN.name())//Puede pasarse como String directa"ADMIN" mente o desde el enumerado
                .anyRequest()
                .authenticated())
                .sessionManagement(sessionManager -> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(this.authenticationProvider)
                //Agregar el filtro de JWT antes del filtro de autentificacion de username y password
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();   
    }
}
