package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Combo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComboRepository extends JpaRepository<Combo, UUID> {
}
