package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.services.implementations.CloudinaryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@RequestMapping("/api/resources")
@RequiredArgsConstructor
public class CloudinaryResourcesController {

    // Inyectamos el servicio de Cloudinary por constructor
    private final CloudinaryServiceImpl cloudinaryService;


    @PostMapping("/upload")
    public ApiResponseTemplate<Map<String, String>> uploadImage(
            @RequestParam("files") MultipartFile[] files
    ) {

        try {

            // Subimos la imagen a Cloudinary
            Map<String, String> response = cloudinaryService.uploadImages(files);

            return ApiResponseTemplate.ok("Imagenes subidas correctamente", response);


        } catch (Exception e) {

            return ApiResponseTemplate.error(e.getMessage());

        }

    }


}
