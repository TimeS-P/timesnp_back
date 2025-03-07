package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.RecuperarPassword;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.RecuperarPasswordRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.charly.timesnp_back.services.IEmailService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

//Controlador para el envío de correos
@RestController
@RequestMapping
public class GenerateTokenController {
    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private RecuperarPasswordRepository recuperarPasswordRepository;

    @Autowired
    IEmailService emailService;

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/api/forgot_password")
    public ResponseEntity<ApiResponseTemplate<String>> generateToken(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        Usuario user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"+": "+email));

        String token = UUID.randomUUID().toString();
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(5); // Expira en 5 minutos
        Date expiration = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()); // Expira en 5 minutos

        String link = "http://localhost:5173/forgot_password?token=" + token;

        RecuperarPassword recuperarPassword = new RecuperarPassword(token, new java.sql.Date(expiration.getTime()), user);
        recuperarPasswordRepository.save(recuperarPassword);

        try {
            emailService.sendForgotPassword(email, link);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Email enviado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al enviar el correo: " + e.getMessage()));
        }

    }
}
