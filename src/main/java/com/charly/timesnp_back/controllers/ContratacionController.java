package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.ContratacionDTO;
import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.services.IEmailService;
import com.charly.timesnp_back.services.IPerfilService;
import com.charly.timesnp_back.services.IServicios;
import com.charly.timesnp_back.services.implementations.ContratacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contratacion")
@RequiredArgsConstructor
public class ContratacionController {

    @Autowired
    ContratacionService contratacionService;

    @Autowired
    IPerfilService perfilService;

    @Autowired
    IServicios servicios;

    @Autowired
    IEmailService emailService;

    @PostMapping("/crearContratacion")
    public ResponseEntity<String> crearContratacion(@RequestBody ContratacionDTO dto) {
        try {
            Perfil perfil = perfilService.getPerfilById(dto.getPerfilId());
            ServicioGeneral servicioGeneral = servicios.obtenerServicioPorId(dto.getServicioGeneralId());

            Contratacion contratacion = new Contratacion();
            contratacion.setFechaInicio(dto.getFechaInicio());
            contratacion.setFechaFin(dto.getFechaFin());
            contratacion.setTotal(dto.getTotal());
            contratacion.setCantidad(dto.getCantidad() != null ? dto.getCantidad() : 0);
            contratacion.setPerfil(perfil);
            contratacion.setServicioGeneral(servicioGeneral);

            contratacionService.saveContratacion(perfil, contratacion, dto.getCodigoCompartir(), dto.getUsePoints());

            return ResponseEntity.ok("Contratación creada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la contratación: " + e);
        }
    }

    @PostMapping("/sendContratacion")
    private ResponseEntity<ApiResponseTemplate<String>> sendEmail(@RequestBody EmailDTO emailDTO){
        try {
            emailService.sendContratacion(emailDTO);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Email enviado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al enviar el correo: " + e.getMessage()));
        }
    }

}
