package com.backend.aeroportuaria.exeption;

import com.backend.aeroportuaria.dto.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {

        //Funciona esta excepción cuando en la clase producto le borra la O a la palabra PRODUCTO para probar.

        @ExceptionHandler(SQLException.class) //SQLException es la excepción que se va a tratar
        public ResponseEntity<ResponseCode> sqlExcepcion(SQLException ex){ //
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseCode(1, "Error En base de datos")); //1 es el número que se quiera poner a la exepción, desúés va el mensaje que aparecerá al usuario
        }

}
