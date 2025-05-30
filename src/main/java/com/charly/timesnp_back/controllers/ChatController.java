package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.ChatDTO;
import com.charly.timesnp_back.dtos.MessageDTO;
import com.charly.timesnp_back.models.Chat;
import com.charly.timesnp_back.models.Mensaje;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.services.MensajesService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public MessageDTO handleChatMessage(@Payload String message, @DestinationVariable String chatId) {
        ObjectMapper objectMapper = new ObjectMapper();
        Mensaje mensaje = null;
        try{
            MessageDTO messageDTO = objectMapper.readValue(message, MessageDTO.class); // Cambiado a readValue
            System.out.println("MensajeDTO: " + messageDTO);
            mensaje = mensajesService.createMessage(messageDTO);
        }catch (Exception e){
            System.out.printf("Error al procesar el mensaje: %s\n", e.getMessage());
            return null; // O maneja el error de otra manera
        }
        MessageDTO mensajeDTO = new MessageDTO();
        mensajeDTO.setMensaje(mensaje.getMensaje());
        mensajeDTO.setFecha(mensaje.getFecha().toString());
        mensajeDTO.setEmisor(mensaje.getEmisor().getId().toString());
        mensajeDTO.setChatId(mensaje.getChat().getId().toString());
        return mensajeDTO; // Devuelve el mensaje para que sea enviado a los suscriptores
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

    @GetMapping("/api/usuario/getId")
    public ResponseEntity<ApiResponseTemplate<String>> getUsuarioId() {
        try {
            String usuarioId = mensajesService.getUsuarioId();
            return ResponseEntity.ok(ApiResponseTemplate.ok("ID de usuario recuperado correctamente", usuarioId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al recuperar el ID de usuario: " + e.getMessage()));
        }
    }

    @GetMapping("/api/chat/get-mensajes")
    public ResponseEntity<ApiResponseTemplate<List<Mensaje>>> getMensajes(@RequestParam String chatId) {
        try {
            List<Mensaje> mensajes = mensajesService.getMensajesByChatId(chatId);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Mensajes recuperados correctamente", mensajes));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al recuperar los mensajes: " + e.getMessage()));
        }
    }

    @GetMapping("/api/chat/get-chats-usuario")
    public ResponseEntity<ApiResponseTemplate<List<Chat>>> getChatsByUsuarioId() {
        try {
            List<Chat> chats = mensajesService.getChatsByUsuarioId();
            return ResponseEntity.ok(ApiResponseTemplate.ok("Chats recuperados correctamente", chats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al recuperar los chats: " + e.getMessage()));
        }
    }

    @GetMapping("/api/chat/get-chats-proveedor")
    public ResponseEntity<ApiResponseTemplate<List<Chat>>> getChatsByProveedorId() {
        try {
            List<Chat> chats = mensajesService.getChatsByProveedorId();
            return ResponseEntity.ok(ApiResponseTemplate.ok("Chats recuperados correctamente", chats));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al recuperar los chats: " + e.getMessage()));
        }
    }
}


