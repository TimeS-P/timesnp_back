package com.charly.timesnp_back.filter;

import com.charly.timesnp_back.constants.ApplicationConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader("Authorization");

        if (jwt != null) {
            try {
                // Obtnemeos el environment para obtener la secret key
                Environment env = getEnvironment();
                if (env != null) {

                    // En caso de  que no se encuentre la variable de entorno, se asigna un valor por defecto
                    String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                    // Creamos un secret key
                    SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                    // Si el secretkey no es nulo, validamos el token
                    if (secretKey != null) {
                        // Validamos el token
                        // Si el token no es válido, se lanzará una excepción
                        Claims claims = Jwts.parser().verifyWith(secretKey).build()
                                .parseSignedClaims(jwt) // En caso de que el token sea válido, se obtienen los claims
                                .getPayload();

                        String username = String.valueOf(claims.get("username"));
                        String authorities = String.valueOf(claims.get("authorities"));

                        // Si el token es válido, se crea un nuevo token de autenticación con el usuario autenticado
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities) // Convertimos los roles a una lista de GrantedAuthority
                        );

                        // Lo almacenamos en el SecurityContextHolder
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            } catch (Exception e) {
                throw new BadCredentialsException("INVALID TOKEN RECEIVED");
            }

        }


        // Pasamos la petición al siguiente filtro
        filterChain.doFilter(request, response);
    }

    // Evitamos que el filtro se llame en el login path (No necesitamos validar el token en el login)
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/auth/login");
    }
}
