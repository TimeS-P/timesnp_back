package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.ResenaDTO;
import com.charly.timesnp_back.models.Reporte;
import com.charly.timesnp_back.models.Resena;

import java.util.List;
import java.util.UUID;

public interface ResenaService {
    public void crearResena(Resena resena) throws Exception;
}
