package com.charly.timesnp_back.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.charly.timesnp_back.models.Perfil;

import jakarta.transaction.Transactional;

@Repository
public interface UserInfoRepository extends JpaRepository<Perfil, UUID> {

    @Query("SELECT p FROM perfil p WHERE p.usuario.id = :idUsuario")
    Optional<Perfil> findByUserId(UUID idUsuario);

}
