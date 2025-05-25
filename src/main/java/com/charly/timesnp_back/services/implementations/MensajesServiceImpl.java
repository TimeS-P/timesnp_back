package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.ChatDTO;
import com.charly.timesnp_back.dtos.MessageDTO;
import com.charly.timesnp_back.models.*;
import com.charly.timesnp_back.repositories.*;
import com.charly.timesnp_back.services.MensajesService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class MensajesServiceImpl implements MensajesService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ServicioGeneralRepository servicioGeneralRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private MensajesRepository mensajesRepository;

    @Override
    public Mensaje createMessage(MessageDTO messageDTO) {
        Mensaje mensaje = new Mensaje();
        try{
        mensaje.setMensaje(messageDTO.getMensaje());
        System.out.println("Fecha: " + messageDTO.getFecha());
        Instant instant = Instant.parse(messageDTO.getFecha());
        java.util.Date fecha = java.util.Date.from(instant);
        System.out.println("Fecha convertida: " + fecha);

        // Convertir a java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(fecha.getTime());
        mensaje.setFecha(sqlDate);
        UUID uuid = UUID.fromString(messageDTO.getChatId());
        Chat chat = chatRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("Chat no encontrado")
        );
        mensaje.setChat(chat);
        UUID usuarioId = UUID.fromString(messageDTO.getEmisor());
        Perfil perfil = perfilRepository.findById(usuarioId).orElseThrow(
                () -> new RuntimeException("Perfil no encontrado")
        );
        mensaje.setEmisor(perfil);
        mensajesRepository.save(mensaje);
        }catch (Exception e){
            throw new RuntimeException("Error al crear el mensaje: " + e.getMessage());
        }
        return mensaje;
    }

    @Override
    public Chat createChat(String servicioId) {
        System.out.println("Creando chat");
        String username = getCurrentUsername();
        if(username == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        System.out.println("Usuario: " + username);
        Chat chat = new Chat();
        System.out.println("ID del servicio: " + servicioId);
        UUID uuid = UUID.fromString(servicioId);
        ServicioGeneral servicioGeneral = servicioGeneralRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("Servicio no encontrado")
        );
        System.out.println("servicioGeneral");
        chat.setServicioGeneral(servicioGeneral);
        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        chat.setUsuario(usuario);
        Chat findedChat = chatRepository.findByServicioGeneralIdAndUsuarioId(servicioGeneral.getId(), usuario.getId()).orElse(null);
        if (findedChat != null) {
            System.out.println("Chat ya existe: " + findedChat);
            return findedChat;
        }
        System.out.println("Creando chat: " + chat);
        chatRepository.save(chat);
        return chat;

    }

    @Override
    public ServicioGeneral getServicioGeneralById(String id) {
        UUID uuid = UUID.fromString(id);
        ServicioGeneral servicioGeneral = servicioGeneralRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("Servicio no encontrado")
        );
        System.out.println("Servicio encontrado: " + servicioGeneral);
        return servicioGeneral;
    }

    @Override
    public String getUsuarioId() {
        String username = getCurrentUsername();
        if (username == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        Perfil perfil = perfilRepository.findByUsuario_Email(usuario.getEmail()).orElseThrow(
                () -> new RuntimeException("Perfil no encontrado")
        );
        return perfil.getId().toString();
    }

    @Override
    public List<Mensaje> getMensajesByChatId(String chatId) {
        UUID uuid = UUID.fromString(chatId);
        Chat chat = chatRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("Chat no encontrado")
        );
        List<Mensaje> mensajes = mensajesRepository.findByChatIdOrderByFechaCreacionAsc(chat.getId());
        return mensajes;
    }

    @Override
    public List<Chat> getChatsByUsuarioId() {
        String username = getCurrentUsername();
        if (username == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        List<Chat> chats = chatRepository.findByUsuarioId(usuario.getId());
        return chats;
    }

    @Override
    public List<Chat> getChatsByProveedorId() {
        String username = getCurrentUsername();
        if (username == null) {
            throw new RuntimeException("Usuario no autenticado");
        }
        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        Perfil perfil = perfilRepository.findByUsuario_Email(usuario.getEmail()).orElseThrow(
                () -> new RuntimeException("Perfil no encontrado")
        );
        Proveedor proveedor = perfil.getProveedor();
        if (proveedor == null) {
            throw new RuntimeException("Proveedor no encontrado para el perfil: " + perfil.getId());
        }
        List<Chat> chats = chatRepository.findByProveedorIdCombo(proveedor.getId());
        List<Chat> chats2 = chatRepository.findByProveedorIdServicio(proveedor.getId());
        chats.addAll(chats2);
        return chats;
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
