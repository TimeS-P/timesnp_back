package com.charly.timesnp_back.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.services.IEmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping
public class EmailController {

    @Autowired
    IEmailService emailService;

    @PostMapping("/api/send-email")
    private ResponseEntity<String> sendEmail(@RequestBody EmailDTO emailDTO){
        try {
            emailService.sendEmail(emailDTO);
            return ResponseEntity.ok("Email enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al enviar el correo: "+e.getMessage());
        }
    }
    
}
