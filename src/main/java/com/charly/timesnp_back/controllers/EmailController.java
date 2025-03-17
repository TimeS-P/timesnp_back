package com.charly.timesnp_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.dtos.EmailVerConfDTO;
import com.charly.timesnp_back.dtos.VerificationDeniedDTO;
import com.charly.timesnp_back.services.IEmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//Controlador para el envío de correos
@RestController
@RequestMapping
public class EmailController {

    @Autowired
    IEmailService emailService;

    @PostMapping("/api/send-email")
    private ResponseEntity<ApiResponseTemplate<String>> sendEmail(@RequestBody EmailDTO emailDTO){
        try {
            emailService.sendEmail(emailDTO);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Email enviado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al enviar el correo: " + e.getMessage()));
        }
    }

    @PostMapping("/api/send-accept-verification")
    private ResponseEntity<ApiResponseTemplate<String>> sendAcceptVerification(@RequestBody EmailVerConfDTO emailVerConfDTO){
        try {
            emailService.sendVerificationConfirmation(emailVerConfDTO.getEmail(), emailVerConfDTO.getName());
            return ResponseEntity.ok(ApiResponseTemplate.ok("Email enviado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al enviar el correo: " + e.getMessage()));
        }
    }

    @PostMapping("/api/send-denied-verification")
    private ResponseEntity<ApiResponseTemplate<String>> sendDeniedVerification(@RequestBody VerificationDeniedDTO verificationDeniedDTO){
        try {
            emailService.sendVerificationDenied(verificationDeniedDTO);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Email enviado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al enviar el correo: " + e.getMessage()));
        }
    }
}