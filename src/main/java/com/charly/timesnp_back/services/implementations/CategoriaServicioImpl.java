package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.CategoriaServicio;
import com.charly.timesnp_back.repositories.CategoriaServicioRepository;
import com.charly.timesnp_back.services.ICategoriaServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoriaServicioImpl implements ICategoriaServicioService {

    private final CategoriaServicioRepository categoriaServicioRepository;

    @Override
    public List<CategoriaServicio> obtenerCategoriasServicios() {
        return categoriaServicioRepository.findAll();
    }
}
