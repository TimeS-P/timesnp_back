package com.charly.timesnp_back.controllers;

import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.services.implementations.UserVerificationServiceImpl;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserVerificationController para manejar la verificacion de Email e INE
 */

@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
@Slf4j
public class UserVerificationController {

    private final UserVerificationServiceImpl userVerificationService;

    @GetMapping("/email/{token}")
    public String verifyEmail() {
        return "Email verificado";
    }

    @GetMapping("/email")
    public ResponseEntity<ApiResponseTemplate<String>> requestEmailVerification() {

        // Get the authenticated user from  the SecurityContextHolder
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Send the email verification request
        try {
            userVerificationService.requestEmailVerification(usuario);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ApiResponseTemplate.ok("Se ha enviado el correo de verificación", "Correo enviado al usuario " + usuario.getEmail()));

        } catch (MessagingException e) {
            log.error("Error al enviar el correo de verificación", e);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponseTemplate.error("Error al enviar el correo de verificación"));

        }
    }

    @GetMapping("/ine")
    public String verifyINE() {
        return "INE verificado";
    }


}
