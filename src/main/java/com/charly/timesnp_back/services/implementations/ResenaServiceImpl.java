package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.ResenaDTO;
import com.charly.timesnp_back.models.Reporte;
import com.charly.timesnp_back.models.Resena;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.ReporteRepository;
import com.charly.timesnp_back.repositories.ResenaRepository;
import com.charly.timesnp_back.repositories.ServicioGeneralRepository;
import com.charly.timesnp_back.services.ReporteService;
import com.charly.timesnp_back.services.ResenaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    @Override
    public void crearResena(Resena resena) throws Exception {
        try {
            resenaRepository.save(resena);
        } catch (Exception e) {
            log.error("Error al crear reseña: {}", e.getMessage());
            throw new Exception("Error al crear reseña: " + e.getMessage());
        }
    }
}
