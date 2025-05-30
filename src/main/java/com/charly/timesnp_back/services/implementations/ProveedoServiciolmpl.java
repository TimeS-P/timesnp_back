package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Proveedor;
import com.charly.timesnp_back.models.ProveedorHasServicio;
import com.charly.timesnp_back.repositories.ProveedorHasServicioRepository;
import com.charly.timesnp_back.repositories.ProveedorRepository;
import com.charly.timesnp_back.services.IProveedor;
import com.charly.timesnp_back.services.IProveedorHasServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProveedoServiciolmpl implements IProveedor {


    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    public Proveedor getProveedorById(UUID id) {
        return proveedorRepository.findById(id).orElse(null);
    }
}
