package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContratacionRepository extends JpaRepository<Contratacion, UUID> {

    Optional<Contratacion> findByPerfil(Perfil perfil);
}
