package com.charly.timesnp_back.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.charly.timesnp_back.dtos.ServicioGeneralDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charly.timesnp_back.dtos.ObtenerServiciosDTO;
import com.charly.timesnp_back.services.IServicios;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.repositories.ServicesProveedorRepository;

@Service
public class Servicios implements IServicios {

    @Autowired
    private ServicesProveedorRepository servicesProveedorRepository;

    @Override
    public Optional<List<ServicioGeneral>> obtenerServicios(ObtenerServiciosDTO obtenerServiciosDTO) {
        switch (obtenerServiciosDTO.getFiltro()) {
            case "PRECIO_ASC":
                return servicesProveedorRepository.findByCategoriaOrPrecioAsc(obtenerServiciosDTO.getIdCategoria());
            case "PRECIO_DESC":
                return servicesProveedorRepository.findByCategoriaOrPrecioDesc(obtenerServiciosDTO.getIdCategoria());
            case "CALIF":
                return servicesProveedorRepository.findByCategoriaOrCalif(obtenerServiciosDTO.getIdCategoria());
            case "ALF":
                return servicesProveedorRepository.findByCategoriaOrAlf(obtenerServiciosDTO.getIdCategoria());
            default:
                return Optional.of(servicesProveedorRepository.findAll());
        }
    }

    @Override
    public ServicioGeneral obtenerServicioPorId(UUID id) {
        return servicesProveedorRepository.findById(id).orElse(null);
    }

    @Override
    public ServicioGeneral crearServicio(ServicioGeneral servicioGeneral) {
        try {
            return servicesProveedorRepository.save(servicioGeneral);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el servicio: " + e.getMessage());
        }
    }

}
