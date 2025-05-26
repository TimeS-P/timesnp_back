package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Contratacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContratacionRepository extends JpaRepository<Contratacion, UUID> {

    List<Contratacion> findByPerfilId(UUID perfilId);
}
