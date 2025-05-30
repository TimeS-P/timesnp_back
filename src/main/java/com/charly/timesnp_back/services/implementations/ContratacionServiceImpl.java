package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.repositories.ContratacionRepository;
import com.charly.timesnp_back.services.IPerfilService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    IPerfilService perfilService;

    /**
     * @param idPerfil the ID of the profile
     * @return
     */
    @Override
    public List<Contratacion> getContrataciones(UUID idPerfil) {
        return contratacionRepository.findByPerfilId(idPerfil);
    }

    @Override
    public void saveContratacion(Perfil perfilCurrent, Contratacion contratacion, String codigo, boolean pointsUsed) throws Exception {
        Perfil perfil = perfilService.existsByCodigoCompartirContratacion(codigo);

        if (perfil != null) {
            perfil.setPuntos(perfil.getPuntos() + 50);
        }

        if (pointsUsed) {
            perfilCurrent.setPuntos(0);
        }

        contratacionRepository.save(contratacion);
    }

}
