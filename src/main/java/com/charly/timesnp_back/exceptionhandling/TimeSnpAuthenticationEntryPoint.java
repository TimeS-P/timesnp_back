package com.charly.timesnp_back.exceptionhandling;

import com.charly.timesnp_back.controllers.ApiResponseTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TimeSnpAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(TimeSnpAuthenticationEntryPoint.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String message = (authException != null && authException.getMessage() != null) ? authException.getMessage() : "Authentication failed.";

        // Loguea el error completo
        logger.error("Authentication failed error: {}", authException != null ? authException.getMessage() : "Authentication failed in Authentication Entry Point.");

        // Prepara la respuesta personalizada
        ApiResponseTemplate<Object> apiResponse = ApiResponseTemplate.error("Credenciales inválidas.");

        response.setHeader("timesnp-error-reason", "Authentication failed.");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));

    }
}
