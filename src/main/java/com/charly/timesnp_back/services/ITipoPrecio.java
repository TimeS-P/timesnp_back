package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Proveedor;
import com.charly.timesnp_back.models.TipoPrecio;

import java.util.UUID;

public interface ITipoPrecio {
    public TipoPrecio getTipoPrecioById(UUID id);
}
