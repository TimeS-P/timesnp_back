package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Verificacion;

import java.util.List;

public interface VerificacionService {

    /**
     * Obtiene todas las verificacione pendientes
     * @return Lista de verificaciones pendientes
     */
    public List<Verificacion> obtenerVerificacionesPendientes();

}
