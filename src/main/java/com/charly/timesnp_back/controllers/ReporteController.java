package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.ReporteDTO;
import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.Reporte;
import com.charly.timesnp_back.services.ReporteService;
import com.charly.timesnp_back.services.implementations.ContratacionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Slf4j
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    private ContratacionService contratacionService;

    /**
     * Endpoint para obtener reportes de un servicio general.
     * @param idServicioGeneral ID del servicio general del cual se obtienen los reportes.
     * @return Lista de reportes del servicio general.
     */
    @GetMapping("/{idServicioGeneral}")
    public ResponseEntity<ApiResponseTemplate<List<Reporte>>> getReportesByServicioGeneral(
            @PathVariable("idServicioGeneral") UUID idServicioGeneral
    ) {
        try {

            List<Reporte> reportes = reporteService.obtenerReportesPorServicioGeneral(idServicioGeneral);

            return ResponseEntity.ok(ApiResponseTemplate.ok("Reportes obtenidos exitosamente", reportes));
        } catch (Exception e) {

            log.error("Error al obtener los reportes: {}", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Error al obtener los reportes: " + e.getMessage()));
        }

    }


    @PostMapping("/crearReporte")
    public ResponseEntity<ApiResponseTemplate<String>> crearReporte(
            @RequestBody ReporteDTO reporteDTO
            ) {
        try {
            Contratacion contratacion = contratacionService.getContratacionById(reporteDTO.idContratacion());


            reporteService.crearReporte(reporteDTO.idPerfil(), contratacion.getServicioGeneral().getId(), reporteDTO.comentario());
            return ResponseEntity.ok(ApiResponseTemplate.ok("Reporte creado exitosamente", null));
        } catch (Exception e) {
            log.error("Error al crear el reporte: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Error al crear el reporte: " + e.getMessage()));
        }
    }

    /**
     * Endpoint para obtener todos los reportes.
     * @return Lista de reportes.
     */
    @GetMapping("/todos")
    public ResponseEntity<ApiResponseTemplate<List<Reporte>>> getAllReportes() {
        try {
            List<Reporte> reportes = reporteService.obtenerTodosLosReportes();
            return ResponseEntity.ok(ApiResponseTemplate.ok("Todos los reportes obtenidos exitosamente", reportes));
        } catch (Exception e) {
            log.error("Error al obtener todos los reportes: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Error al obtener todos los reportes: " + e.getMessage()));
        }
    }

}
