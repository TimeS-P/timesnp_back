package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Proveedor;
import com.charly.timesnp_back.models.ProveedorHasServicio;

import java.util.UUID;

public interface IProveedor {
    public Proveedor getProveedorById(UUID id);
}
