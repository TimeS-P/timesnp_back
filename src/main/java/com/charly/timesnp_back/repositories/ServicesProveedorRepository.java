package com.charly.timesnp_back.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.charly.timesnp_back.models.ServicioGeneral;

@Repository
public interface ServicesProveedorRepository extends JpaRepository<ServicioGeneral, UUID> {
    
    @Query("SELECT s FROM servicio_general s WHERE s.proveedorHasServicio.categoriaServicio.id = :categoria ORDER BY s.precio ASC")
    Optional<List<ServicioGeneral>> findByCategoriaOrPrecioAsc(UUID categoria);

    @Query("SELECT s FROM servicio_general s WHERE s.proveedorHasServicio.categoriaServicio.id = :categoria ORDER BY s.precio DESC")
    Optional<List<ServicioGeneral>> findByCategoriaOrPrecioDesc(UUID categoria);

    @Query("SELECT s FROM servicio_general s WHERE s.proveedorHasServicio.categoriaServicio.id = :categoria ORDER BY s.nombre ASC")
    Optional<List<ServicioGeneral>> findByCategoriaOrAlf(UUID categoria);

    @Query("SELECT s FROM servicio_general s WHERE s.proveedorHasServicio.categoriaServicio.id = :categoria ORDER BY s.proveedorHasServicio.calificacion DESC")
    Optional<List<ServicioGeneral>> findByCategoriaOrCalif(UUID categoria);
    
}
