package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.RecuperarPassword;
import com.charly.timesnp_back.repositories.RecuperarPasswordRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

// Controlador para validar un token de recuperación de contraseña
@RestController
public class ValidateTokenController {

    @Autowired
    private RecuperarPasswordRepository recuperarPasswordRepository;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/api/validate_token")
    public ResponseEntity<ApiResponseTemplate<String>> validateToken(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");

        //Validar que el token no este vacio
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("El token no puede estar vacío"));
        }

        Optional<RecuperarPassword> record = recuperarPasswordRepository.findByToken(token);

        //Validar que el token exista
        if (!record.isPresent()) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Token inválido"));
        }

        // Validar que el token no ha expirado
        RecuperarPassword recuperarPassword = record.get();
        Date expirationDate = recuperarPassword.getExpires();


        // Verificar si la fecha actual es posterior a la fecha de expiración
        Date currentDate = new Date();
        if (currentDate.after(expirationDate)) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("El token ha expirado"));
        }

        return ResponseEntity.ok(ApiResponseTemplate.ok("Token validado correctamente", null));
    }
}

