package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.RecuperarPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RecuperarPasswordRepository extends JpaRepository<RecuperarPassword, UUID> {
    Optional<RecuperarPassword> findByToken(String token);
    Optional<RecuperarPassword> findByUsuario_Id(UUID id_usuario);

    // Cambié el nombre del método para que sea más coherente con la relación entre RecuperarPassword y Usuario
    boolean existsByUsuario_Id(UUID id_usuario);
}
