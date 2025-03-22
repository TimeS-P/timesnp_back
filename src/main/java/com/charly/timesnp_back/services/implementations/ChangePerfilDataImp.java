package com.charly.timesnp_back.services.implementations;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.charly.timesnp_back.dtos.PerfilDTO;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.UserInfoRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import com.charly.timesnp_back.services.IChangePerfilData;

@Service
public class ChangePerfilDataImp implements IChangePerfilData {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public Perfil changeData(PerfilDTO perfilDTO) {
        Optional<Perfil> perfilOptional = Optional.empty();
        Optional<Usuario> usuarioOptional = Optional.empty();
        Perfil perfil;

        // Verificar si el DTO contiene un ID
        if (perfilDTO.getId().isPresent()) {
            UUID id = perfilDTO.getId().get();
            perfilOptional = userInfoRepository.findById(id);
        }else{
            String username = getCurrentUsername();
            if (username != null) {
                // Buscar el usuario por su nombre de usuario
                usuarioOptional = usuarioRepository.findByEmail(username);
                if (usuarioOptional.isPresent()) {
                    // Buscar el perfil del usuario
                    perfilOptional = userInfoRepository.findByUserId(usuarioOptional.get().getId());
                }
            }
        }

        if (perfilOptional.isPresent()) {
            // Actualizar el perfil existente
            perfil = perfilOptional.get();
        } else {
            // Crear un nuevo perfil
            perfil = new Perfil();
        }
        
        

        // Actualizar los campos del perfil con los datos del DTO
        usuarioOptional.ifPresent(usuario -> perfil.setUsuario(usuario));
        perfilDTO.getNombre().ifPresent(perfil::setNombre);
        perfilDTO.getApellidoPaterno().ifPresent(perfil::setApellidoPaterno);
        perfilDTO.getApellidoMaterno().ifPresent(perfil::setApellidoMaterno);
        perfilDTO.getTelefono().ifPresent(perfil::setTelefono);
        perfilDTO.getFechaNacimiento().ifPresent(perfil::setFechaNacimiento);
        perfilDTO.getGenero().ifPresent(perfil::setGenero);
        perfilDTO.getDescripcion().ifPresent(perfil::setDescripcion);

        // Guardar el perfil en el repositorio
        userInfoRepository.save(perfil);

        return perfil;

    }

    @Override
    public Optional<Perfil> getPerfilData() {
        String username = getCurrentUsername();
        if (username != null) {
            // Buscar el usuario por su nombre de usuario
            Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);
            if (usuarioOptional.isPresent()) {
                // Buscar el perfil del usuario
                return userInfoRepository.findByUserId(usuarioOptional.get().getId());
            }
        }
        return Optional.empty();
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
