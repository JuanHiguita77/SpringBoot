package com.juan.Bingo_Project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.juan.Bingo_Project.util.enums.Role;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Esto es necesario para que Spring gestione el bean
    }

    
    private final String[] PUBLIC_RESOURCES = {"/auth/**"};//Rutas publicas y cuando este despues de los asteriscos es publico: rutas del controller

    private final String[] ADMIN_RESOURCES = {"/register/admin"};//Ruta solo para admins

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
                .build();   
    }

}
