package com.charly.timesnp_back.config;

import com.charly.timesnp_back.controllers.ApiResponseTemplate;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ApiResponseTemplate<Object>> handleJsonMappingException(JsonMappingException ex) {
        // Loguear el error completo en los logs del servidor
        logger.error("Error al mapear JSON", ex);

        // Responder con un mensaje amigable y no exponer el stack trace
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseTemplate.error("Uno o más valores de rol no son válidos."));
    }

    // Exception handler for the BadCredentialsException
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponseTemplate<Object>> handleBadCredentialsException(BadCredentialsException ex) {
        // Log the full error in the server logs
        logger.error("Bad credentials (Usuario o contrasena incorrectos) ", ex);

        // Respond with a friendly message and do not expose the stack trace
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseTemplate.error("Credenciales inválidas."));
    }


}
