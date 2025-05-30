package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.Proveedor;
import com.charly.timesnp_back.models.TipoPrecio;
import com.charly.timesnp_back.repositories.ProveedorRepository;
import com.charly.timesnp_back.repositories.TipoPrecioRepository;
import com.charly.timesnp_back.services.IProveedor;
import com.charly.timesnp_back.services.ITipoPrecio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TipoPreciolmpl implements ITipoPrecio {

    @Autowired
    private TipoPrecioRepository tipoPrecioRepository;

    @Override
    public TipoPrecio getTipoPrecioById(UUID id) {
        return tipoPrecioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de precio no encontrado con ID: " + id));
    }
}
