package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.models.Verificacion;
import com.charly.timesnp_back.models.VerificarCorreo;
import com.charly.timesnp_back.repositories.VerificacionRepository;
import com.charly.timesnp_back.repositories.VerificarCorreoRepository;
import com.charly.timesnp_back.services.IUserVerificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserVerificationServiceImpl implements IUserVerificationService {

    private final EmailServiceImpl emailServiceImpl;
    private final VerificarCorreoRepository verificarCorreoRepository;
    private final VerificacionRepository verificacionRepository;
    private final GcpStorageServiceImpl gcpStorageServiceImpl;

    // Obtenemos el active profile
    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * @param usuario
     * @param token
     * @return
     */
    @Override
    public void verifyEmail(Usuario usuario, String token) throws Exception {

        // Verificamos si el token existe en la base de datos
        VerificarCorreo verificarCorreo = verificarCorreoRepository.findByToken(token);

        // Si el token no existe, lanzamos una excepción
        if (verificarCorreo == null) {
            throw new RuntimeException("Token no válido");
        }

        // Si el token existe, verificamos el email del usuario
        if (verificarCorreo.getUsuario().getEmail().equals(usuario.getEmail())) {
            verificarCorreo.setVerificado(true);

            // Guardamos el verificarCorreo actualizado
            verificarCorreoRepository.save(verificarCorreo);
        } else {
            throw new RuntimeException("El token no corresponde al usuario logueado");
        }

    }

    /**
     * @param usuario usuario a verificar
     */
    @Override
    public void requestEmailVerification(Usuario usuario) throws MessagingException {
        // Generamos un token en base a un UUID y el email del usuario hashedeado con SHA-256
        String token = UUID.randomUUID().toString() + usuario.getEmail().hashCode();

        // Recordamos el token a 100 caracteres
        if (token.length() > 40) {
            token = token.substring(0, 40);
        }

        String link = activeProfile.equals("dev")
                ? "http://localhost:8080/verify-email?token=" + token
                : "https://timesnp.com/verify-email?token=" + token;

        // Enviamos el email de verificación
        String message = "¡Hola! Para verificar tu correo, haz click en el siguiente enlace: " + link;
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
     * @param usuario usuario a verificar
     * @throws Exception
     */
    @Override
    public void verifyINE(Usuario usuario) throws Exception {

        Verificacion verificacion = usuario.getPerfil().getVerificacion();

        // Verificamos si el INE existe
        if (verificacion == null) {
            throw new Exception("El INE no existe");
        }

        // Verificamos si el INE ya fue verificado
        if (verificacion.isVerificado()) {
            throw new Exception("El INE ya fue verificado");
        }

        verificacion.setVerificado(true);
        // Asignamos la fecha de verificacion actual (SQL Date)
        verificacion.setFecha_verificacion(new java.sql.Date(System.currentTimeMillis()));

        // Guardamos la verificación en la base de datos
        Verificacion updatedVerification = verificacionRepository.save(verificacion);

        // Si no se guardo la verificación, lanzamos una excepción
        if (updatedVerification.getId() == null) {
            throw new Exception("Error al actualizar la verificación");
        }

    }

    /**
     * @param usuario usuario a verificar
     * @throws Exception
     */
    @Override
    public void requestINEVerification(Usuario usuario, MultipartFile photoFront, MultipartFile photoBack) throws Exception {

        String uniqueFileNameFront = System.currentTimeMillis() + "_" + photoFront.getOriginalFilename();
        String uniqueFileNameBack = System.currentTimeMillis() + "_" + photoBack.getOriginalFilename();

        Verificacion verificacion = new Verificacion(uniqueFileNameFront, uniqueFileNameBack, usuario.getPerfil());

        // Subimos las fotos a Google Cloud Storage
        gcpStorageServiceImpl.uploadFile(uniqueFileNameFront, photoFront.getBytes(), photoFront.getContentType());
        gcpStorageServiceImpl.uploadFile(uniqueFileNameBack, photoBack.getBytes(), photoBack.getContentType());

        // Guardamos la verificación en la base de datos
        Verificacion newVerificacion = verificacionRepository.save(verificacion);

        // Si no se guardo la verificación, lanzamos una excepción
        if (newVerificacion.getId() == null) {
            throw new Exception("Error al guardar la verificación");
        }

        // Enviamos el email de la solicitud de verificación
        String message = "¡Hola! " + usuario.getPerfil().getNombre() + " Hemos recibido tu solicitud de verificación de INE. " +
                "En breve nos pondremos en contacto contigo para informarte del estado de tu solicitud.";

        EmailDTO emailDTO = new EmailDTO(
                usuario.getEmail(),
                "Solicitud de verificación de INE",
                message,
                "Verificación de INE",
                usuario.getPerfil().getNombre()
        );

        emailServiceImpl.sendEmail(emailDTO);

    }
}
