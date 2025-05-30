package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.ContratacionDTO;
import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.dtos.ResenaDTO;
import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.Resena;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.services.IEmailService;
import com.charly.timesnp_back.services.IPerfilService;
import com.charly.timesnp_back.services.IServicios;
import com.charly.timesnp_back.services.ResenaService;
import com.charly.timesnp_back.services.implementations.ContratacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/resena")
@RequiredArgsConstructor
public class ResenaController {

    @Autowired
    ContratacionService contratacionService;

    @Autowired
    ResenaService resenaService;

    @PostMapping("/crearResena")
    public ResponseEntity<String> crearResena(@RequestBody ResenaDTO dto) {
        try {
            Resena resena = new Resena();
            Contratacion contratacion = contratacionService.getContratacionById(dto.getContratacionId());

            resena.setComentario(dto.getComentario());
            resena.setCalificacion(dto.getCalificacion());
            resena.setContratacion(contratacion);

            resenaService.crearResena(resena);

            return ResponseEntity.ok("Reseña creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la reseña: " + e.getMessage());
        }
    }
}
