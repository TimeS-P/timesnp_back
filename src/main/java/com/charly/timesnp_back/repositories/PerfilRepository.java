package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, UUID> {

    Optional<Perfil> findByUsuario_Email(String usuarioEmail);
    Optional<Perfil> findByCodigoCompartir(String codigoCompartir);
}
