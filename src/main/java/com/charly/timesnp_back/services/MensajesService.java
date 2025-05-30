package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.ChatDTO;
import com.charly.timesnp_back.dtos.MessageDTO;
import com.charly.timesnp_back.models.Chat;
import com.charly.timesnp_back.models.Mensaje;
import com.charly.timesnp_back.models.ServicioGeneral;

import java.util.List;

public interface MensajesService {
    public Mensaje createMessage(MessageDTO messageDTO);
    public Chat createChat(String servicioId);
    public ServicioGeneral getServicioGeneralById(String idServicioGeneral);
    public String getUsuarioId();
    public List<Mensaje> getMensajesByChatId(String chatId);
    public List<Chat>getChatsByUsuarioId();
    public List<Chat> getChatsByProveedorId();
}
