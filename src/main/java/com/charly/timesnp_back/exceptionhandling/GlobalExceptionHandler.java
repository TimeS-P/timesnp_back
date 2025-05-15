package com.charly.timesnp_back.exceptionhandling;

import com.charly.timesnp_back.controllers.ApiResponseTemplate;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ApiResponseTemplate<Object>> handleJsonMappingException(JsonMappingException ex) {
        // Loguear el error completo en los logs del servidor
        logger.error("Mapping JSON Error: {}", ex.getMessage(), ex);

        // Responder con un mensaje amigable y no exponer el stack trace
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseTemplate.error("Uno o más valores de rol no son válidos."));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiResponseTemplate<String>> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {

        // Loguear el error completo en los logs del servidor
        logger.error("Load Size Error: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(ApiResponseTemplate.error("El tamaño del archivo excede el máximo permitidod de 10MB."));
    }

    // Global Exception Handler for Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseTemplate<String>> handleException(Exception ex) {
        // Loguear el error completo en los logs del servidor
        logger.error("Internal server error: {}", ex.getMessage(), ex);

        // Responder con un mensaje amigable y no exponer el stack trace
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseTemplate.error("Error interno del servidor: " + ex.getMessage()));
    }


}
