package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, UUID> {
    List<Reporte> findByServicioGeneralId(UUID servicioGeneralId);
}
