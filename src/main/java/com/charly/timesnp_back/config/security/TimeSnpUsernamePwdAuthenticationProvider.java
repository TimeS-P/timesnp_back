package com.charly.timesnp_back.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class TimeSnpUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    // Inyecta el bean del servicio de detalles de usuario
    private final TimeSnpUserDetailsService timeSnpUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    /**
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Obtiene el nombre de usuario y la contraseña del token de autenticación
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        // Obtiene el usuario de la base de datos
        UserDetails userDetails = timeSnpUserDetailsService.loadUserByUsername(username);

        // En modo desarrollo, se omite la comparación de contraseñas para facilitar el desarrollo
        // Compara las contraseñas
        //if (passwordEncoder.matches(pwd, userDetails.getPassword())) {
            // Si las contraseñas coinciden, se crea un nuevo token de autenticación con el usuario autenticado
            return new UsernamePasswordAuthenticationToken(username, pwd, userDetails.getAuthorities());
        //} else {
            // Si las contraseñas no coinciden, se lanza una excepción de autenticación
        //    throw new BadCredentialsException("Invalid password.");
        //}

    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        // Indica que este proveedor de autenticación soporta la autenticación por usuario y contraseña
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
