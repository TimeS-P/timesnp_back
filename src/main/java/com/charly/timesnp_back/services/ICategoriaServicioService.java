package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.CategoriaServicio;

import java.util.List;
import java.util.UUID;

public interface ICategoriaServicioService {
    public List<CategoriaServicio> obtenerCategoriasServicios();
    public CategoriaServicio obtenerCategoriaServicioPorId(UUID id);
}
