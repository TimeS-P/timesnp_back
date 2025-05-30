package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.ObtenerServiciosDTO;
import com.charly.timesnp_back.dtos.ServicioGeneralDTO;
import com.charly.timesnp_back.models.ProveedorHasServicio;
import com.charly.timesnp_back.models.ServicioGeneral;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProveedorHasServicio {
    public UUID saveProveedorHasServicio(ProveedorHasServicio proveedorHasServicio) throws Exception;
    public ProveedorHasServicio getProveedorHasServicioById(UUID id) throws Exception;
}
