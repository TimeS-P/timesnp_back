package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    //Obtener los chats de un servicio general
    @Query("SELECT c FROM chat c WHERE c.servicioGeneral.id = :idServicioGeneral")
    List<Chat> findByServicioGeneralId(UUID idServicioGeneral);

    //Obtener chat con un servicio general y un usuario
    @Query("SELECT c FROM chat c WHERE c.servicioGeneral.id = :idServicioGeneral AND c.usuario.id = :idUsuario")
    Optional<Chat> findByServicioGeneralIdAndUsuarioId(UUID idServicioGeneral, UUID idUsuario);
}
