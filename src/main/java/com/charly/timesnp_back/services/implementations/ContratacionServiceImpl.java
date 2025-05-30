package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.repositories.ContratacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service implementation for managing contracted services.
 * This service is responsible for retrieving the contracted services for a user.
 */
@Service
@RequiredArgsConstructor
public class ContratacionServiceImpl implements ContratacionService{

    private final ContratacionRepository contratacionRepository;

    /**
     * @param idPerfil the ID of the profile
     * @return
     */
    @Override
    public List<Contratacion> getContrataciones(UUID idPerfil) {
        return contratacionRepository.findByPerfilId(idPerfil);
    }
}
