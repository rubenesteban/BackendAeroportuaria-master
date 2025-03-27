package com.backend.aeroportuaria.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Clase que comprueba si hay un token válido, sino devuelve una respuesta 401 no autorizado
//rechaza todas peticiones que no estén autenticadas

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class); ///Sirve para ver cuál es el método que está dando error en caso que no funcione la aplicación.

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        logger.error("Fallo en el método commence"); //Lanza el error. Esto sólo se utiliza en desarrollo, no en producción
        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado"); //El response envía un error
    }
}
