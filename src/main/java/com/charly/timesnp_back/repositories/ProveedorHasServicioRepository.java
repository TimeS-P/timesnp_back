package com.charly.timesnp_back.repositories;


import com.charly.timesnp_back.models.ProveedorHasServicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProveedorHasServicioRepository extends JpaRepository<ProveedorHasServicio, UUID> {
}
