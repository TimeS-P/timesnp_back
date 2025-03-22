package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.services.implementations.GcpStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    /**
     * @param file archivo a subir del tipo MultipartFile (FormData)
     * @return respuesta de la petición
     */
    @PostMapping("/upload")
    public ResponseEntity<ApiResponseTemplate<String>> uploadFile(
            @RequestParam("file") MultipartFile file
    ) {

        try {

            String uniqueFileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            // Subimos el archivo a Google Cloud Storage
            gcpStorageService.uploadFile(uniqueFileName, file.getBytes(), file.getContentType());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(ApiResponseTemplate.ok("Archivo subido correctamente", uniqueFileName));

        } catch (Exception e) {
            log.error("ERROR AL SUBIR EL ARCHIVO: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error(e.getMessage()));

        }

    }

    // Enpoint para eliminar archivo de Google Cloud Storage
    /**
     * @param fileName nombre del archivo a eliminar
     * @return respuesta de la petición
     */
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<ApiResponseTemplate<String>> deleteFile(
            @PathVariable("fileName") String fileName
    ) {

        try {

            // Eliminamos el archivo de Google Cloud Storage
            gcpStorageService.deleteFile(fileName);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponseTemplate.ok("Archivo eliminado correctamente", fileName));

        } catch (Exception e) {
            log.error("ERROR AL ELIMINAR EL ARCHIVO: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error(e.getMessage()));

        }

    }

    // Enpoint para descargar archivo de Google Cloud Storage
    /**
     * @param fileName nombre del archivo a descargar
     * @return archivo en bytes
     */
    @GetMapping(value = "/download/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadFile(
            @PathVariable("fileName") String fileName
    ) {
        try {
            // Descargamos el archivo de Google Cloud Storage y retornamos el contenido en bytes
            byte[] fileContent = gcpStorageService.downloadFile(fileName);
            return ResponseEntity.ok(fileContent);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Enpoint para generar URL firmada de Google Cloud Storage
    /**
     * @param fileName nombre del archivo a generar la URL
     * @return URL firmada
     */
    @GetMapping("/signed-url/{fileName}")
    public ResponseEntity<ApiResponseTemplate<String>> generateSignedUrl(
            @PathVariable("fileName") String fileName
    ) {

        try {

            // Generamos la URL firmada para el archivo de Google Cloud Storage
            String signedUrl = gcpStorageService.generateSignedUrl(fileName).toString();

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponseTemplate.ok("URL firmada generada correctamente", signedUrl));

        } catch (Exception e) {
            log.error("ERROR AL GENERAR LA URL FIRMADA: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error(e.getMessage()));

        }

    }


}
