package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Reporte;
import com.charly.timesnp_back.models.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, UUID> {

}
