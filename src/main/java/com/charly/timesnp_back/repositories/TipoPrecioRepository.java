package com.charly.timesnp_back.repositories;


import com.charly.timesnp_back.models.ProveedorHasServicio;
import com.charly.timesnp_back.models.TipoPrecio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TipoPrecioRepository extends JpaRepository<TipoPrecio, UUID> {
}
