package com.backend.aeroportuaria.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.aeroportuaria.security.serviceimpl.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Esta clase se ejecura por cada petición, comprueba que sea válido el token haciendo uso de la clase JwtUtil
//en caso de que sea válido el token va a permitir el acceso al recurso, en caso contrario lanza la exepción.

public class JwtFilter extends OncePerRequestFilter { //OncePerRequestFilter indica que por cada petición se va a ejecutar el filtro

    private final static Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(req); //Obtiene le token a partir del método getToken que se encuentra a continuación
            if(token != null && jwtUtil.validateToken(token)){ //Valida si el token no es nulo y el método validateToken de la clase JwtUtil responde true, es decir que es válido.
                String nombreUsuario = jwtUtil.getNombreUsuarioFromToken(token); //Trae el nombre de usuario que está en el token
                UserDetails userDetails = userDetailsService.loadUserByUsername(nombreUsuario);

                UsernamePasswordAuthenticationToken auth = //Crea una nueva autenticación
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); //Además de la autenticación necesita la autorización para saber a que recursos puede acceder
                SecurityContextHolder.getContext().setAuthentication(auth); //Al contexto de autenticación se le asigna el usuario
            }
        } catch (Exception e){ //Utiliza excepción genérica
            logger.error("Fallo en el método doFilterInternal " + e.getMessage());
        }
        filterChain.doFilter(req, res);
    }
    
    //Método para extraer el token, le quitar el Bearer 
    
    private String getToken(HttpServletRequest request){
        String header = request.getHeader("Authorization"); //Obtiene la cabecera de la petición
        if(header != null && header.startsWith("Bearer")) //Valida si la cabecera tiene el token e inicia por Bearer
            return header.replace("Bearer ", ""); //Reemplaza el Bearer y el espacio para que no tenga nada
        return null;
    }
}
