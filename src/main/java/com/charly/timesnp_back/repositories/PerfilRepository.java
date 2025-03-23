package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PerfilRepository extends JpaRepository<Perfil, UUID> {

    Optional<Perfil> findByUsuario_Email(String usuarioEmail);

}
