package com.riwi.beautySalon.infraestructure.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.riwi.beautySalon.domain.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService 
{
    //Crear clave o firma
    private final String SECRET_KEY = "c3VwZXJtZWdhY2xhdmUgdHJ5aGFyZCwgc3VwZXJtZWdhY2xhdmUgdHJ5aGFyZCwgc3VwZXJtZWdhY2xhdmUgdHJ5aGFyZA==";//generada de una web en base 64, ya que se pide asi para el metodo

    //Metodo para encriptar clave secreta
    public SecretKey getKey()
    {
        //Convertir llave a bytes
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        //Retornar la llave encriptada
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getToken(Map<String, Object> claims, User user)
    {
        return Jwts.builder()
                    .claims(claims)//claims son los datos del payload, no enviar nada sensible (cuerpo del token con info como tiempo de expiracion etc)
                    .subject(user.getUsername())//A quien pertenece el jwt
                    .issuedAt(new Date(System.currentTimeMillis()))//Tiempo de creacion
                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))//Tiempo de expiracion
                    .signWith(this.getKey())//El server firma el token con la clave del server
                    .compact();
    }

    //Metodo para obtener el jwt
    public String getToken(User user)
    {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", user.getId());
        claims.put("role", user.getRole().name());//name() es para retornar el string como tal
        return getToken(claims, user);
    }

    //Metodo para obtener los claims
    public Claims getAllClaims(String token)
    {
        //parser es para desarmar, contrario a builder
        //verificar la firma del servidor: verifyWith
        return Jwts
                .parser()
                .verifyWith(this.getKey())//validar
                .build()//construirlo
                .parseSignedClaims(token)//extraer los tokens de base 64 a json
                .getPayload();//obtener el payload o cuerpo
    } 

    //Obtiene todos los claims pero retorna uno
    public <T> T getClaim(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = this.getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //username del token
    public String getUsernameFromToken(String token)
    {
        return this.getClaim(token, Claims::getSubject);
    }

    //Fecha de expiracion del token
    public Date getExpiration(String token)
    {
        return this.getClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token)
    {
        return this.getExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        String userName = this.getUsernameFromToken(token);
        
        return (userName.equals(userDetails.getUsername()) && !this.isTokenExpired(token));
    }
}
