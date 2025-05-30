package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.Verificacion;
import com.charly.timesnp_back.services.IGcpStorageService;
import com.charly.timesnp_back.services.VerificacionService;
import com.charly.timesnp_back.services.implementations.GcpStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/verificacionesadmin")
@RequiredArgsConstructor
public class VerificacionController {

    private final VerificacionService verificacionService;
    private final IGcpStorageService gcpStorageService;

     @GetMapping("/pendientes")
     public ResponseEntity<ApiResponseTemplate<List<Verificacion>>> getPendingVerifications() {
         try {
             List<Verificacion> verifications = verificacionService.obtenerVerificacionesPendientes();
             return ResponseEntity.ok(ApiResponseTemplate.ok("Verificaciones obtenidas exitosamente", verifications));
         } catch (Exception e) {
             return ResponseEntity.status(500).body(ApiResponseTemplate.error("Error al obtener las verificaciones: " + e.getMessage()));
         }
     }

     @GetMapping("/generate-url/{filename}")
     public ResponseEntity<ApiResponseTemplate<URL>> generateUrlForVerification(
             @PathVariable("filename") String filename
     ) {
            try {
                URL url = gcpStorageService.generateSignedUrl(filename);
                return ResponseEntity.ok(ApiResponseTemplate.ok("URL generada exitosamente", url));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(ApiResponseTemplate.error("Error al generar la URL: " + e.getMessage()));
            }
     }

}
