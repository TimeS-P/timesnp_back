package com.charly.timesnp_back.filter;

import com.charly.timesnp_back.constants.ApplicationConstants;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.services.PerfilServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    private final PerfilServiceImpl perfilService;

    /**
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Obtenemos el usuario autenticado
        Usuario user = (Usuario) authentication.getPrincipal();
        Perfil perfil = null;
        try {
             perfil = perfilService.getPerfilByUserEmail(user.getEmail());
        } catch (Exception e) {
            log.error("Error al obtener el perfil del usuario autenticado en JWTTokenGeneratorFilter: {}", e.getMessage());
        }

        if (authentication != null) {
            // Obtenemos el secret key de las variables de entorno
            Environment env = getEnvironment(); // Metodo de GenericFilterBean
            if (env != null) {
                // En caso de  que no se encuentre la variable de entorno, se asigna un valor por defecto
                String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
                // Creamos un secret key
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

                String jwt = Jwts.builder().issuer("Timesnp") // Emisor del token
                        .subject("JWT Token") // Asunto del token
                        .claim("username", authentication.getName()) // Claim de username
                        .claim(
                                "authorities",
                                authentication.getAuthorities()
                                        .stream()
                                        .map(GrantedAuthority::getAuthority)
                                        .collect(Collectors.joining(","))
                        ) // Claim de authorities (Roles) del usuario separados por coma
                        //Claim para agregar si la cuenta esta bloqueada o no
                        .claim("accountNonLocked", user.isAccountNonLocked())
                        //Claim para agregar el nombre, apellidos y el share code del perfil
                        .claim("name", perfil != null ? perfil.getNombre() : "")
                        .claim("lastName", perfil != null ? perfil.getApellidoPaterno() + " " + perfil.getApellidoMaterno() : "")
                        .claim("shareCode", perfil != null ? perfil.getCodigoCompartir() : "")
                        // Claim para saber si el usuario esta verificado
                        .claim("isVerified", perfil != null && perfil.getVerificacion() != null)
                        .issuedAt(new Date()) // Fecha de emisión
                        // Expiration time de 8 horas
                        .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 8))
                        .signWith(secretKey) // Firma del token
                        .compact();

                // Añadimos el token al header de la respuesta
                response.setHeader(ApplicationConstants.JWT_HEADER, jwt);
            }
        }

        filterChain.doFilter(request, response);
    }

    // Evitamos que el filtro se llame en los paths que no sean el de login
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getServletPath().equals("/api/auth/login");
    }
}
