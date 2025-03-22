package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.services.implementations.GcpStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador para los recursos de Google Cloud Storage para poder
 * SUBIR y ELIMINAR archivos de Google Cloud Storage (INES) de manera segura
 */
@RestController
@RequestMapping("/api/resources/gcp")
@RequiredArgsConstructor
@Slf4j
public class GcpCloudStorageResourcesController {

    private final GcpStorageServiceImpl gcpStorageService;

    // Enpoint para subir archivo a Google Cloud Storage
    @PostMapping("/upload")
    public ResponseEntity<ApiResponseTemplate<Object>> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            // Subimos el archivo a Google Cloud Storage
            gcpStorageService.uploadFile(file.getOriginalFilename(), file.getBytes(), file.getContentType());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponseTemplate.ok("Archivo subido correctamente", null));

        } catch (Exception e) {
            log.error("ERROR AL SUBIR EL ARCHIVO: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error(e.getMessage()));

        }

    }

}
