package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ComboRepository extends JpaRepository<Combo, UUID> {
    //Obtener los combos de un proveedor

    /**
     * Find all Combos by Proveedor ID.
     *
     * @param proveedorId the ID of the Proveedor
     * @return a list of Combos associated with the given Proveedor ID
     */
    @Query("SELECT c FROM combo c WHERE c.proveedor.id = :proveedorId")
    Optional<List<Combo>> findByProveedor_Id(UUID proveedorId);

}
