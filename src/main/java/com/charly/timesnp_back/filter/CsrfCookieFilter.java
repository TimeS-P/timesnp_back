package com.charly.timesnp_back.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// El filtro solo se ejecuta una vez por petición
public class CsrfCookieFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtiene el token CSRF de la petición
        CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        // Se genera el token CSRF y se añade a la cookie
        // Esto porque en la SecurityConfig se genera de manera EAGER, entonces en este caso se genera de manera explícita
        // Para que el cliente pueda acceder a él
        csrfToken.getToken();
        // Continúa con la cadena de filtros de la petición
        filterChain.doFilter(request, response);
    }
}
