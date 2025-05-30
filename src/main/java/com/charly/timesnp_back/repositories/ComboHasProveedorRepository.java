package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.ComboHasProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ComboHasProveedorRepository extends JpaRepository<ComboHasProveedor, UUID> {
}
