package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.controllers.ApiResponseTemplate;
import com.charly.timesnp_back.dtos.RegisterUserDto;
import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.models.RolNombre;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.RolRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import com.charly.timesnp_back.services.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    /**
     * @param usuario
     * @return Usuario registrado
     */
    @Override
    public Usuario registerUser(RegisterUserDto registerUserDto) {

        try {
            // Hasheamos el password
            String hashPwd = this.passwordEncoder.encode(registerUserDto.getPassword());
            registerUserDto.setPassword(hashPwd);

            // Verificamos si el email ya existe
            if (this.usuarioRepository.existsByEmail(registerUserDto.getEmail())) {
                // Retornamos una mala respuesta
                // Arrojamos una excepción si el correo ya está registrado
                throw new IllegalArgumentException("El correo ya esta registrado");
            }

            // Convertir los roles del DTO a instancias persistidas
            Set<Rol> roles = new HashSet<>();
            for (RolNombre rolNombre : registerUserDto.getRoles()) {
                Rol rolPersistido = rolRepository.findByNombre(rolNombre)
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + rolNombre));
                roles.add(rolPersistido);
            }


            // Mapeamos el DTO a la entidad
            Usuario newUser = this.usuarioRepository.save(Usuario.from(registerUserDto, roles));

            // Retornamos una buena respuesta
            return newUser;

        } catch (IllegalArgumentException e) {
            log.error("Error al registrarlo, parametros incorrectos", e);
            // Retornamos una mala respuesta
            return null;
        } catch (Exception e) {
            log.error("Error al registrar usuario", e);
            // Retornamos una mala respuesta
            return null;
        }

    }
}
