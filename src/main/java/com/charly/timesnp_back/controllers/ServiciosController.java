package com.charly.timesnp_back.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charly.timesnp_back.dtos.ObtenerServiciosDTO;
import com.charly.timesnp_back.dtos.ServicioGeneralDTO;
import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.services.IServicios;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/servicios")
public class ServiciosController {

    @Autowired 
    IServicios servicios;

    @GetMapping("/serviciosCategoria")
    public ResponseEntity<ApiResponseTemplate<List<ServicioGeneralDTO>>> getMethodName(@RequestParam UUID idCategoria, @RequestParam String filtro) {
        try {
            ObtenerServiciosDTO obtenerServiciosDTO = new ObtenerServiciosDTO(idCategoria, filtro);
            List<ServicioGeneral> serviciosList = servicios.obtenerServicios(obtenerServiciosDTO).orElse(null);
            List<ServicioGeneralDTO> serviciosDTOList = serviciosList.stream()
                    .map(ServicioGeneralDTO::fromEntity)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponseTemplate.ok("Servicios obtenidos correctamente", serviciosDTOList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al obtener los servicios: " + e.getMessage()));
        }
    }
    
    @GetMapping("/servicio")
    public ResponseEntity<ApiResponseTemplate<ServicioGeneralDTO>> getMethodName(@RequestParam UUID id) {
        try {
            ServicioGeneral servicio = servicios.obtenerServicioPorId(id);
            ServicioGeneralDTO servicioDTO = ServicioGeneralDTO.fromEntity(servicio);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Servicio obtenido correctamente", servicioDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al obtener el servicio: " + e.getMessage()));
        }
    }
    
    

}
