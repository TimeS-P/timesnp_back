package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProveedorRepository extends JpaRepository<Proveedor, UUID> {
}
