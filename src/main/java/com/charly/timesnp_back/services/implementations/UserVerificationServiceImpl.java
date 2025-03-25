package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.models.VerificarCorreo;
import com.charly.timesnp_back.repositories.VerificarCorreoRepository;
import com.charly.timesnp_back.services.IUserVerificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserVerificationServiceImpl implements IUserVerificationService {

    private final EmailServiceImpl emailServiceImpl;
    private final VerificarCorreoRepository verificarCorreoRepository;

    // Obtenemos el active profile
    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * @param usuario
     * @param token
     * @return
     */
    @Override
    public String verifyEmail(Usuario usuario, String token) {
        return "";
    }

    /**
     * @param usuario usuario a verificar
     */
    @Override
    public void requestEmailVerification(Usuario usuario) throws MessagingException {
        // Generamos un token en base a un UUID y el email del usuario hashedeado con SHA-256
        String token = UUID.randomUUID().toString() + usuario.getEmail().hashCode();

        String link = activeProfile.equals("dev")
                ? "http://localhost:8080/verify-email?token=" + token
                : "https://timesnp-back.herokuapp.com/verify-email?token=" + token;

        // Enviamos el email de verificación
        String message = "¡Hola! Para verificar tu correo, haz click en el siguiente enlace: " + link + token;
        EmailDTO emailDTO = new EmailDTO(
                usuario.getEmail(),
                "Verifica tu correo",
                message,
                "Verificación de correo",
                usuario.getPerfil().getNombre()
        );

        emailServiceImpl.sendEmail(emailDTO);

        // Guardamos el token en la base de datos
        VerificarCorreo verificarCorreo = new VerificarCorreo(usuario, token);

        VerificarCorreo newVerifyEmail = verificarCorreoRepository.save(verificarCorreo);

        // Si no se guardo el verificarCorreo, lanzamos una excepción
        if (newVerifyEmail.getId() == null) {
            throw new MessagingException("Error al guardar el token de verificación");
        }


    }

    /**
     * @return
     */
    @Override
    public String verifyINE() {
        return "";
    }
}
