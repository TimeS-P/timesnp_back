package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.FotoTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface FotoRespository extends JpaRepository<FotoTrabajo, UUID> {

    //Obtener la foto de un trabajo por su servicio general
    /**
     * Find a FotoTrabajo by ServicioGeneral ID.
     *
     * @param servicioGeneralId the ID of the ServicioGeneral
     * @return an Optional containing the FotoTrabajo if found, or empty if not found
     */
    @Query("SELECT f FROM foto_trabajo f WHERE f.servicioGeneral.id = :servicioGeneralId")
    Optional<FotoTrabajo> findByServicioGeneral_Id(UUID servicioGeneralId);
}
