package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.services.implementations.CloudinaryServiceImpl;
import com.cloudinary.api.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
@Slf4j
public class CloudinaryResourcesController {

    // Inyectamos el servicio de Cloudinary por constructor
    private final CloudinaryServiceImpl cloudinaryService;


    @PostMapping("/upload")
    public ResponseEntity<ApiResponseTemplate<Map<String, String>>> uploadImage(
            @RequestParam("files") MultipartFile[] files
    ) {

        try {

            // Subimos la imagen a Cloudinary
            Map<String, String> response = cloudinaryService.uploadImages(files);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponseTemplate.ok("Imagenes subidas correctamente", response));


        } catch (Exception e) {
            log.error("ERROR AL SUBIR LAS IMAGENES: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error(e.getMessage()));

        }

    }

    @PostMapping("/delete")
    public ResponseEntity<ApiResponseTemplate<Object>> deleteImage(
            @RequestBody List<String> ids
    ) {

        try {

            // Eliminamos la imagen de Cloudinary
            ApiResponse response = cloudinaryService.deleteImage(ids);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponseTemplate.ok("Imagenes eliminadas correctamente", response));

        } catch (Exception e) {
            log.error("ERROR AL ELIMINAR LA IMAGEN: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error(e.getMessage()));

        }

    }


}
