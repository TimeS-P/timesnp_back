package com.charly.timesnp_back.controllers;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.RecuperarPasswordRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import com.charly.timesnp_back.services.ForgotPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.charly.timesnp_back.services.IEmailService;
import java.util.Map;
import java.util.Optional;
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

    @Autowired
    ForgotPasswordService forgotPasswordService;

//    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/api/forgot_password")
    public ResponseEntity<ApiResponseTemplate<String>> generateToken(@RequestBody Map<String, String> requestBody) throws MessagingException {
        try {
            //Obtener link para email
            String email = requestBody.get("email"), link = "";
            Optional<Usuario> user = userRepository.findByEmail(email);
            UUID id_usuario = user.get().getId();

            if(recuperarPasswordRepository.existsByUsuario_Id(id_usuario)) link = forgotPasswordService.actualizarToken(id_usuario);
            else link = forgotPasswordService.generarToken(email);

            //Enviar email
            emailService.sendForgotPassword(email, link);
            return ResponseEntity.ok(ApiResponseTemplate.ok("Email enviado correctamente", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponseTemplate.error("Error al enviar el correo: " + e.getMessage()));
        }

    }
}
