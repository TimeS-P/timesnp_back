package com.charly.timesnp_back.repositories;

import com.charly.timesnp_back.models.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MensajesRepository extends JpaRepository<Mensaje, UUID> {

    //Obtener los mensajes de un chat ordenados por fecha
    @Query("SELECT m FROM mensaje m WHERE m.chat.id = :chatId ORDER BY m.fecha ASC")
    List<Mensaje> findByChatIdOrderByFechaCreacionAsc(UUID chatId);

}
