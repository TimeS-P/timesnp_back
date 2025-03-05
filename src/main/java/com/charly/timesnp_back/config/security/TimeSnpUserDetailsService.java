package com.charly.timesnp_back.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.charly.timesnp_back.repositories.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class TimeSnpUserDetailsService implements UserDetailsService {

    // Inyecta el bean del repositorio de usuarios
    private final UsuarioRepository usuarioRepository;

    /**
     * @param username the username identifying the user whose data is required.
     * @return the UserDetails required by the security framework.
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retorna el usuario encontrado por el email o lanza una excepción si no se encuentra
        return this.usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found for the email: " + username));
    }
}
