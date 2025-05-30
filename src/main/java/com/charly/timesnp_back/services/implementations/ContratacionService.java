package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.ServicioGeneral;

import java.util.List;
import java.util.UUID;

public interface ContratacionService {

    /**
     * Method to get all the contracted services for a user
     *
     * @param idPerfil the ID of the profile
     * @return a list of contracted services
     */

    List<Contratacion> getContrataciones(UUID idPerfil);

    public void saveContratacion(Perfil perfilCurrent, Contratacion contratacion, String codigo, boolean pointsUsed) throws Exception;

}
