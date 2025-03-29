package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.Verificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificacionRepository extends JpaRepository<Verificacion, UUID> {

    Optional<Verificacion> findByPerfil(Perfil perfil);

}
