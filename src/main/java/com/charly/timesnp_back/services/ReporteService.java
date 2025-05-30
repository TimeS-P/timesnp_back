package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Reporte;

import java.util.List;
import java.util.UUID;

public interface ReporteService {

    /**
     * Crea un reporte para un servicio general.
     *
     * @param idServicioGeneral ID del servicio general al que se le crea el reporte.
     * @param comentario        Comentario del reporte.
     * @return El reporte creado.
     */
    public void crearReporte(UUID idServicioGeneral, String comentario) throws Exception;

    /**
     * Obtiene todos los reportes de un servicio general.
     *
     * @param idServicioGeneral ID del servicio general del cual se obtienen los reportes.
     * @return Lista de reportes del servicio general.
     */
    public List<Reporte> obtenerReportesPorServicioGeneral(UUID idServicioGeneral) throws Exception;

    /**
     * Obtener todos los reportes en general
     *
     * @return Lista de reportes
     */
    public List<Reporte> obtenerTodosLosReportes() throws Exception;

}
