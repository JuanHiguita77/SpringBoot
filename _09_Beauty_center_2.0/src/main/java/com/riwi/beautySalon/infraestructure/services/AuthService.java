package com.riwi.beautySalon.infraestructure.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.riwi.beautySalon.api.dto.request.ClientRegisterReq;
import com.riwi.beautySalon.api.dto.request.EmployeeRegisterReq;
import com.riwi.beautySalon.api.dto.request.EmployeeReq;
import com.riwi.beautySalon.api.dto.request.LoginReq;
import com.riwi.beautySalon.api.dto.request.RegisterReq;
import com.riwi.beautySalon.api.dto.response.AuthResp;
import com.riwi.beautySalon.api.dto.response.EmployeeResp;
import com.riwi.beautySalon.domain.entities.ClientEntity;
import com.riwi.beautySalon.domain.entities.Employee;
import com.riwi.beautySalon.domain.entities.User;
import com.riwi.beautySalon.domain.repositories.ClientRepository;
import com.riwi.beautySalon.domain.repositories.EmployeeRepository;
import com.riwi.beautySalon.domain.repositories.UserRepository;
import com.riwi.beautySalon.infraestructure.abstract_service.IAuthService;
import com.riwi.beautySalon.infraestructure.helpers.JwtService;
import com.riwi.beautySalon.utils.enums.Role;
import com.riwi.beautySalon.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Transactional//Devuelve los cambios en caso de error
@Service
@AllArgsConstructor
public class AuthService implements IAuthService
{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ClientRepository clientRepository;

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final JwtService jwtService;
    
    @Autowired
    private final PasswordEncoder passwordEncoder;//codificador de contraseñas

    @Autowired
    private final AuthenticationManager authenticationManager;
    
    @Override
    public AuthResp login(LoginReq request) {
        //Autenticar en la app

        try 
        {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        } 
        catch (Exception e) 
        {
            throw new BadRequestException("Invalid Credentials");
        }

        User user = this.findByUserName(request.getUserName());

        if (user == null) 
        {
            throw new BadRequestException("User not registered");    
        }

        return AuthResp.builder()
                        .message("Autenticated correctly")
                        .token(this.jwtService.getToken(user))
                        .build();
    }

    @Override
    public AuthResp register(RegisterReq request) 
    {
        //validar si no existe
        User exist = this.findByUserName(request.getUserName());

        if (exist != null) 
        {
            throw new BadRequestException("User exist");    
        }

        //construir el user
        User user = User.builder()
                        .userName(request.getUserName())
                        .password(passwordEncoder.encode(request.getPassword()))//encode
                        .role(Role.CLIENT)
                        .build();
        
        user = this.userRepository.save(user);

        return AuthResp.builder()
                        .message("Register completed succesfully")
                        .token(jwtService.getToken(user))
                        .build(); 
    }

    //Metodo para registrar un cliente
    @Override
    public AuthResp registerClient(ClientRegisterReq request)
    {
        User exist = this.findByUserName(request.getUserName());

        if (exist != null) 
        {
            throw new BadRequestException("User already has created");    
        }

        //Creamos el user
        User user = User.builder()
                        .userName(request.getUserName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.CLIENT)//Rol pasado directamente
                        .build();

        User userSaved = this.userRepository.save(user);


        ClientEntity client = ClientEntity.builder()    
                                        .firstName(request.getUserName())
                                        .lastName(request.getLastName())
                                        .phone(request.getPhone())
                                        .email(request.getEmail())
                                        .user(userSaved)//se le da el token apenas se crea para que pueda entrar de una vez sin logearse primero
                                        .appointments(new ArrayList<>())
                                        .build();

        this.clientRepository.save(client);  
        
        return AuthResp.builder()
                    .message("Register completed succesfully")
                    .token(jwtService.getToken(userSaved))
                    .build(); 
    }

    //Metodo para registrar un empledo desde el administrador
    @Override
    public AuthResp registerEmployee(EmployeeRegisterReq request)
    {
        User exist = this.findByUserName(request.getUserName());

        if (exist != null) 
        {
            throw new BadRequestException("Employee already has created");    
        }

        //Creamos el user
        User user = User.builder()
                        .userName(request.getUserName())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .role(Role.EMPLOYEE)//Rol pasado directamente
                        .build();

        User userSaved = this.userRepository.save(user);


        Employee employee = Employee.builder()    
                                        .firstName(request.getUserName())
                                        .lastName(request.getLastName())
                                        .phone(request.getPhone())
                                        .role(request.getRole())
                                        .email(request.getEmail())
                                        .user(userSaved)//se le da el token apenas se crea para que pueda entrar de una vez sin logearse primero
                                        .appointments(new ArrayList<>())
                                        .build();

        this.employeeRepository.save(employee);  
        
        return AuthResp.builder()
                    .message("Register completed succesfully")
                    .token(jwtService.getToken(userSaved))
                    .build(); 
    }

    private User findByUserName(String userName)
    {
        return this.userRepository.findByUserName(userName).orElse(null);
    }

}
