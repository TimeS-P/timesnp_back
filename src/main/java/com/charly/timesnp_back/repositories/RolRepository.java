package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Rol;
import com.charly.timesnp_back.models.RolNombre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RolRepository extends JpaRepository<Rol, UUID> {

    Optional<Rol> findByNombre(RolNombre rolNombre);

}
