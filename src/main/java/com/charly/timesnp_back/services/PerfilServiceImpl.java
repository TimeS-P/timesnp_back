package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.PerfilRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements IPerfilService {

    private final PerfilRepository perfilRepository;

    /**
     * @param id ID del usuario
     * @return
     */
    @Override
    public Perfil getPerfilById(UUID id) {
        return null;
    }

    /**
     * @param perfil perfil del usuario
     * @return
     */
    @Override
    public Perfil createPerfil(Perfil perfil) {

        Perfil newPerfil = perfilRepository.save(perfil);

        return newPerfil;
    }

    /**
     * @param perfil perfil del usuario
     * @return
     */
    @Override
    public Perfil updatePerfil(Perfil perfil) {
        return null;
    }

    /**
     * @param id ID del usuario
     */
    @Override
    public void deletePerfil(UUID id) {

    }

    /**
     * @param id ID del usuario
     * @return
     */
    @Override
    public Perfil verifyPerfil(UUID id) {
        return null;
    }

    /**
     * @param email correo del usuario
     * @return perfil del usuario
     */
    @Override
    public Perfil getPerfilByUserEmail(String email) throws Exception {


        Perfil perfil = perfilRepository.findByUsuario_Email(email).orElse(null);

        if (perfil == null) {
            // Lanzamos una excepción si no se encuentra el perfil
            throw new RuntimeException("Perfil no encontrado");
        }

        return perfil;

    }
}
