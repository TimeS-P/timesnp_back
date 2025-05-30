package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.ServicioGeneral;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServicioGeneralRepository extends JpaRepository<ServicioGeneral, UUID> {

}

