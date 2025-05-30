package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.constants.ApplicationConstants;
import com.charly.timesnp_back.dtos.LoginRequestDTO;
import com.charly.timesnp_back.dtos.RegisterUserDto;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.services.implementations.UserServiceImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import com.charly.timesnp_back.models.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
// Anotación para el manejo de logs, para que cree un logger llamado "log" en la clase
@Slf4j
public class UserController {

    // Inyecta repositorio de usuarios y password encoder por constructor
    private final UserServiceImpl userService;

    // Inyectamos el authentication manager definido en la clase de configuración de seguridad
    private final AuthenticationManager authenticationManager;

    private final Environment env;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseTemplate<Usuario>> registerUser(@RequestBody RegisterUserDto registerUserDto) {

        try {

            Usuario newUser = this.userService.registerUser(registerUserDto);

            if (newUser.getId() != null) {
                // Retornamos una buena respuesta
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(ApiResponseTemplate.ok("Usuario registrado exitosamente", newUser));
            }

            // Retornamos una mala respuesta
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Ocurrio un error al registrar al usuario"));

        } catch (IllegalArgumentException e) {
            log.error("Error al registrar usuario: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponseTemplate.error(e.getMessage()));

        } catch (Exception e) {
            log.error("Error inesperado al registrar usuario", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Ocurrió un error inesperado"));
        }
    }

    // Login endpoint
    @GetMapping("/login")
    public ResponseEntity<ApiResponseTemplate<Usuario>> loginUser(Authentication authentication) {

        try {

            Usuario user = this.userService.getUserByEmail(authentication.getName());

            if (user != null) {
                // Retornamos una buena respuesta
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(ApiResponseTemplate.ok("Usuario logueado exitosamente", user));
            } else {
                // Retornamos una mala respuesta
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponseTemplate.error("Ocurrio un error al loguear al usuario"));
            }

        } catch (Exception e) {
            log.error("Error al loguear usuario", e);
            // Retornamos una mala respuesta
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Ocurrio un error al loguear al usuario"));
        }

    }

    // New Login endpoint seguro con POST
    @PostMapping("/loginSecure")
    public ResponseEntity<ApiResponseTemplate<Usuario>> login(@RequestBody LoginRequestDTO loginRequestDTO) {

        // Iniciamos la autenticacion de manera manual sin usar httpbasic
        String jwt = "";

        Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequestDTO.email(), loginRequestDTO.password());

        // Autenticamos al usuario
        Authentication authenticationResponse = authenticationManager.authenticate(authentication);

        // Obtenemos el usuario autenticado
        Usuario user = (Usuario) authenticationResponse.getPrincipal();
        // Obtenemos el perfil del usuario
        Perfil perfil = user.getPerfil();

        if (authenticationResponse != null && authenticationResponse.isAuthenticated()) {
            // Si la autenticacion es correcta, generamos el token
            String secret = env.getProperty(ApplicationConstants.JWT_SECRET_KEY, ApplicationConstants.JWT_SECRET_DEFAULT_VALUE);
            // Creamos un secret key
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            jwt = Jwts.builder().issuer("Timesnp") // Emisor del token
                    .subject("JWT Token") // Asunto del token
                    .claim("username", authenticationResponse.getName()) // Claim de username
                    .claim(
                            "authorities",
                            authenticationResponse.getAuthorities()
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
                    .claim("puntos", perfil != null ? perfil.getPuntos() : 0)
                    .claim("id_perfil", perfil != null ? perfil.getId() : null)
                    .issuedAt(new Date()) // Fecha de emisión
                    // Expiration time de 8 horas
                    .expiration(new Date(new Date().getTime() + 1000 * 60 * 60 * 8))
                    .signWith(secretKey) // Firma del token
                    .compact();

        } else {
            log.error("Error al autenticar usuario: {}", authenticationResponse);
            // Si la autenticacion falla, retornamos un error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseTemplate.error("Usuario o contraseña incorrectos"));
        }

        log.info("Usuario inicio sesion con el correo: {}", user.getEmail());
        return ResponseEntity.status(HttpStatus.OK).header(ApplicationConstants.JWT_HEADER, jwt)
                .body(ApiResponseTemplate.ok("Usuario logueado exitosamente", user));

    }



}
