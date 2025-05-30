package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.RegisterUserDto;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.models.RolNombre;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.RolRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import com.charly.timesnp_back.services.IUserService;
import com.charly.timesnp_back.services.PerfilServiceImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final PerfilServiceImpl perfilService;

    /**
     * @param usuario
     * @return Usuario registrado
     */
    @Override
    @Transactional // Spring hace rollback si se lanza una excepción
    public Usuario registerUser(RegisterUserDto registerUserDto) {
        // Hasheamos el password
        String hashPwd = this.passwordEncoder.encode(registerUserDto.getPassword());
        registerUserDto.setPassword(hashPwd);

        // Verificamos si el email ya existe
        if (this.usuarioRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new IllegalArgumentException("El correo ya está registrado");
        }

        // Convertir los roles del DTO a instancias persistidas
        Set<Rol> roles = new HashSet<>();
        for (RolNombre rolNombre : registerUserDto.getRoles()) {
            Rol rolPersistido = rolRepository.findByNombre(rolNombre)
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: " + rolNombre));
            roles.add(rolPersistido);
        }

        // Guardar usuario
        Usuario newUser = this.usuarioRepository.save(Usuario.from(registerUserDto, roles));

        // Si el usuario si se guardo registramos el perfil
        if (newUser.getId() != null) {
            // Creamos el perfil en base al usuario
            Perfil newProfile = perfilService.createPerfil(new Perfil(
                    registerUserDto.getNombre(),
                    newUser,
                    Perfil.generateShareCode(registerUserDto.getEmail())
            ));

            // Si el perfil no se guardo lanzamos una excepción
            if (newProfile.getId() == null) {
                throw new IllegalArgumentException("Error al registrar el perfil del usuario");
            }

            // Seteamos el perfil al usuario
            newUser.setPerfil(newProfile);
        } else {
            throw new IllegalArgumentException("Error al registrar el usuario");
        }

        return newUser;
    }

    public Usuario getUserByEmail(String email) {
        Optional<Usuario> user = this.usuarioRepository.findByEmail(email);

        return user.orElse(null);
    }
}
