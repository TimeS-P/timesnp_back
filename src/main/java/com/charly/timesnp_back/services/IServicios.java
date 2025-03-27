package com.charly.timesnp_back.services;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.dtos.ObtenerServiciosDTO;

public interface IServicios {
    public Optional<List<ServicioGeneral>> obtenerServicios(ObtenerServiciosDTO obtenerServiciosDTO);
    public ServicioGeneral obtenerServicioPorId(UUID id);
}
