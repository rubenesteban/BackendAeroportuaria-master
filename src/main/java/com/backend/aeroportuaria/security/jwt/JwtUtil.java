package com.backend.aeroportuaria.security.jwt;

import com.backend.aeroportuaria.security.serviceimpl.UserJWTServiceImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

//Esta clase genera el token y tiene un método de validación para ver que esté bien formado que no esté expirado. No implementa ni hereda de ninguna otra clase

@Component
public class JwtUtil {
    private final static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}") //Permite acceder a la variable jwt.secret que está en el archivo aplication.properties
    private String secret;

    @Value("${jwt.expiration}") //Permite acceder a la variable jwt.expiration que está en el archivo aplication.properties
    private int expiration;
    
    //Método para crear el token
    
    public String generateToken(Authentication authentication){
        UserJWTServiceImpl userJWTServiceImpl = (UserJWTServiceImpl) authentication.getPrincipal(); //El usuario principal que es de una clase genérica lo guarda en un bjeto de la clase UserJWTServiceImpl creada en este proyecto.
        return Jwts.builder()
        		.setSubject(userJWTServiceImpl.getUsername()) //Le pasa el nombre de usuario en el campo subject
                .setIssuedAt(new Date()) //Fecha de creación del token
                .setExpiration(new Date(new Date().getTime() + expiration * 1000)) //Especifica el tiempo de expiración del token
                .signWith(SignatureAlgorithm.HS512, secret) //Firma el token
                .compact();
    }

    public String getNombreUsuarioFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject(); //.getSubject() tiene el nombre de usuario, se le pasó en el método anterior
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true; //Si el token es válido devuelve cierto
        }catch (MalformedJwtException e){
            logger.error("Token mal formado");
            System.out.println("Token mal formado en JwtUtil");
        }catch (UnsupportedJwtException e){
            logger.error("Token no soportado");
            System.out.println("Token no soportado en JwtUtil");
        }catch (ExpiredJwtException e){
            logger.error("Token expirado");
            System.out.println("Token expirado en JwtUtil");
        }catch (IllegalArgumentException e){
            logger.error("Token vacío");
            System.out.println("Token vacío en JwtUtil");
        }catch (SignatureException e){
            logger.error("Fallo en la firma");
            System.out.println("Fallo en la firma en JwtUtil");
        }
        return false; //Si el token no es válido devuelve falso
    }
}
