package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.dtos.CambiarContrasenaDTO;
import com.charly.timesnp_back.services.ForgotPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Controlador para validar un token de recuperación de contraseña
@RestController
public class CambiarContrasenaController {

    @Autowired
    ForgotPasswordService forgotPasswordService;

//    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/api/cambiar_contrasena")
    public ResponseEntity<ApiResponseTemplate<String>> cambiarContrasena(@RequestBody CambiarContrasenaDTO cambiarContrasenaDTO) throws MessagingException {
        String token = cambiarContrasenaDTO.getToken();
        String contrasena = cambiarContrasenaDTO.getNuevaContrasena();

        //Obtener status de token
        String respuesta = forgotPasswordService.validarToken(token);

        //Validar que el token sea correcto y cambiar contrasena
        if(respuesta.equals("Token validado correctamente")){
            forgotPasswordService.cambiarContrasena(contrasena, token);
            forgotPasswordService.eliminarToken(token);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Contraseña actualizada correctamente", null));
        }
        else{
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error(respuesta));
        }
    }
}

