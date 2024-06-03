package com.riwi.beautySalon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.riwi.beautySalon.domain.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class ApplicationConfig 
{
    @Autowired
    private final UserRepository userRepository;

    //Se usa para el registro
    @Bean//Sobreescribir un metodo de alguna libreria o springboot
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();//Encriptar y desencriptar constraseñas en la app: En la DB la constraseña ya no es reconocible
    }

    @Bean
    //Obtener config preparada de security
    //Permite el manejo del usuario en toda la app
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();   
    }

    @Bean
    //Guardar las credenciales del user: Guardamos toda la info y el tipo de cifrado que tiene su contraseña
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(this.passwordEncoder());
        authenticationProvider.setUserDetailsService(userdetailsService());
       
        return authenticationProvider;
    }

    @Bean
    //Servicio usado por security para cargar detalles del user durante la autentificacion
    public UserDetailsService userdetailsService()
    {
        return username -> userRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
