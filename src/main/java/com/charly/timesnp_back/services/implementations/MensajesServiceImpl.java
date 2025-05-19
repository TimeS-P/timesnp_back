package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.ChatDTO;
import com.charly.timesnp_back.models.Chat;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.ChatRepository;
import com.charly.timesnp_back.repositories.ServicioGeneralRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import com.charly.timesnp_back.services.MensajesService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class MensajesServiceImpl implements MensajesService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ServicioGeneralRepository servicioGeneralRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void cqreateMessage() {

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
        System.out.println("ID del combo a eliminar: " + servicioId);
        UUID uuid = UUID.fromString(servicioId);
        ServicioGeneral servicioGeneral = servicioGeneralRepository.findById(uuid).orElseThrow(
                () -> new RuntimeException("Servicio no encontrado")
        );
        System.out.println(servicioGeneral);
        chat.setServicioGeneral(servicioGeneral);
        Usuario usuario = usuarioRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Usuario no encontrado")
        );
        chat.setUsuario(usuario);
        Chat findedChat = chatRepository.findByServicioGeneralIdAndUsuarioId(servicioGeneral.getId(), usuario.getId()).orElse(null);
        if (findedChat != null) {
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

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
