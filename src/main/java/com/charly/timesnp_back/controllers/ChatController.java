package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.ChatDTO;
import com.charly.timesnp_back.models.Chat;
import com.charly.timesnp_back.models.Mensaje;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.services.MensajesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private MensajesService mensajesService;

    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public Mensaje handleChatMessage(@Payload Mensaje message, @DestinationVariable String chatId) {
        // Aquí puedes agregar lógica para procesar el mensaje antes de enviarlo a los suscriptores
        return message; // Devuelve el mensaje para que sea enviado a los suscriptores
    }

    @GetMapping("/api/chat/create-chat")
    public ResponseEntity<ApiResponseTemplate<Chat>> createChat(@RequestParam String servicioId) {
        try {
            Chat chat = mensajesService.createChat(servicioId);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Chat creado correctamente", chat));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al crear el chat: " + e.getMessage()));
        }
    }

    @GetMapping("/api/chat/get-servicio-general")
    public ResponseEntity<ApiResponseTemplate<ServicioGeneral>> getServicioGeneral(@RequestParam String servicioId) {
        try {
            ServicioGeneral servicioGeneral = mensajesService.getServicioGeneralById(servicioId);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Servicios generales recuperados correctamente", servicioGeneral));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al recuperar los servicios generales: " + e.getMessage()));
        }
    }
}


