package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.CategoriaServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaServicioRepository extends JpaRepository<CategoriaServicio, UUID> {
    boolean existsByNombre(String nombre);
}
