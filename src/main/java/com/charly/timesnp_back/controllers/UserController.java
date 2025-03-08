package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.RegisterUserDto;
import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.models.RolNombre;
import com.charly.timesnp_back.repositories.RolRepository;
import lombok.RequiredArgsConstructor;
import com.charly.timesnp_back.models.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.charly.timesnp_back.repositories.UsuarioRepository;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
// Anotación para el manejo de logs, para que cree un logger llamado "log" en la clase
@Slf4j
public class UserController {

    // Inyecta repositorio de usuarios y password encoder por constructor
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseTemplate<Usuario>> registerUser(@RequestBody RegisterUserDto registerUserDto) {

        try {
            // Hasheamos el password
            String hashPwd = this.passwordEncoder.encode(registerUserDto.getPassword());
            registerUserDto.setPassword(hashPwd);

            // Verificamos si el email ya existe
            if (this.usuarioRepository.existsByEmail(registerUserDto.getEmail())) {
                // Retornamos una mala respuesta
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseTemplate.error("El correo ya esta registrado"));
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

            if (newUser.getId() != null) {
                // Retornamos una buena respuesta
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponseTemplate.ok("Usuario registrado exitosamente", newUser));
            } else {
                // Retornamos una mala respuesta
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponseTemplate.error("Ocurrio un error al registrar al usuario"));
            }

        } catch (Exception e) {
            log.error("Error al registrar usuario", e);
            // Retornamos una mala respuesta
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Ocurrio un error al registrar al usuario"));
        }
    }

}
