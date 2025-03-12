package com.charly.timesnp_back.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

@Slf4j
public class AuthoritiesLoggingAfterFilters implements Filter {
    /**
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            // En caso de que querramos hacer auditoria para seguridad y manejo de usuarios y llevar controlados los accesos
            log.info(
                    "User: {} is succesfully authenticated and has the authorities: {}",
                    authentication.getName(),
                    authentication.getAuthorities()
            );
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
