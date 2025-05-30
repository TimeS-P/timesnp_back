package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.ObtenerServiciosDTO;
import com.charly.timesnp_back.dtos.ServicioGeneralDTO;
import com.charly.timesnp_back.models.ProveedorHasServicio;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.repositories.ProveedorHasServicioRepository;
import com.charly.timesnp_back.repositories.ServicesProveedorRepository;
import com.charly.timesnp_back.services.IProveedorHasServicio;
import com.charly.timesnp_back.services.IServicios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProveedorHasServiciolmpl implements IProveedorHasServicio {

    @Autowired
    ProveedorHasServicioRepository proveedorHasServicioRepository;

    @Override
    public UUID saveProveedorHasServicio(ProveedorHasServicio proveedorHasServicio) throws Exception {
        try {
            ProveedorHasServicio savedEntity = proveedorHasServicioRepository.save(proveedorHasServicio);
            return savedEntity.getId(); // Asegúrate de que 'g type
        } catch (Exception e) {
            throw new Exception("Error al guardar el proveedor con el servicio: " + e.getMessage());
        }
    }

    @Override
    public ProveedorHasServicio getProveedorHasServicioById(UUID id) throws Exception {
        Optional<ProveedorHasServicio> proveedorHasServicio = proveedorHasServicioRepository.findById(id);
        if (proveedorHasServicio.isPresent()) {
            return proveedorHasServicio.get();
        } else {
            throw new Exception("Proveedor con servicio no encontrado con id: " + id);
        }
    }
}
