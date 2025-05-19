package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.ChatDTO;
import com.charly.timesnp_back.models.Chat;
import com.charly.timesnp_back.models.ServicioGeneral;

public interface MensajesService {
    public void cqreateMessage();
    public Chat createChat(String servicioId);
    public ServicioGeneral getServicioGeneralById(String idServicioGeneral);
}
