package controllers;

import dtos.RegisterUserDto;
import lombok.RequiredArgsConstructor;
import models.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repositories.UsuarioRepository;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    // Inyecta repositorio de usuarios y password encoder por constructor
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseTemplate<Usuario>> registerUser(@RequestBody RegisterUserDto registerUserDto) {

        try {

            // Hasheamos el password
            String hashPwd = this.passwordEncoder.encode(registerUserDto.getPassword());
            registerUserDto.setPassword(hashPwd);

            // Verificamos si el email ya existe
            if (this.usuarioRepository.existsByEmail(registerUserDto.getEmail())) {
                // Retornamos una mala respuesta
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponseTemplate.error("Email already exists"));
            }

            // Mapeamos el DTO a la entidad
            Usuario newUser = this.usuarioRepository.save(Usuario.from(registerUserDto));

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
            // Retornamos una mala respuesta
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Ocurrio un error al registrar al usuario"));
        }
    }

}
