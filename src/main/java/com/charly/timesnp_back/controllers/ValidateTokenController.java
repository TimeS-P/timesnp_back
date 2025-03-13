package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.repositories.RecuperarPasswordRepository;
import com.charly.timesnp_back.services.ForgotPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// Controlador para validar un token de recuperación de contraseña
@RestController
public class ValidateTokenController {

    @Autowired
    private RecuperarPasswordRepository recuperarPasswordRepository;

    @Autowired
    ForgotPasswordService forgotPasswordService;

//    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/api/validate_token")
    public ResponseEntity<ApiResponseTemplate<String>> validateToken(@RequestBody Map<String, String> requestBody) throws MessagingException {
        String token = requestBody.get("token");

        //Validar token
        String respuesta = forgotPasswordService.validarToken(token);

        //Validar que el token no este vacio
        if(respuesta.equals("Token validado correctamente")){
            return ResponseEntity.ok(ApiResponseTemplate.ok(respuesta, null));
        }
        else{
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error(respuesta));
        }
    }

}

