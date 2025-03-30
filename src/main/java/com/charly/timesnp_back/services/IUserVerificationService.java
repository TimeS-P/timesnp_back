package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Usuario;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

public interface IUserVerificationService {

    /**
     * Verifica el email de un usuario
     */
    public void verifyEmail(Usuario usuario, String token) throws Exception;

    /**
     * Solicita la verificación del email de un usuario
     * Envia el email de verificación con el token correspondiente
     * Guarda el token en VerificarCorreo
     * @param usuario usuario a verificar
     */
    public void requestEmailVerification(Usuario usuario) throws MessagingException;

    /**
     * Verifica el INE de un usuario (SOLO VERIFICADOR)
     * @param usuario usuario a verificar
     * @throws Exception
     */
    public void verifyINE(Usuario usuario) throws Exception;


    /**
     * Verifica el INE de un usuario
     * @param usuario usuario a verificar
     * @param photoFront foto del frente del INE
     * @param photoBack foto del reverso del INE
     */
    void requestINEVerification(Usuario usuario, MultipartFile photoFront, MultipartFile photoBack) throws Exception;
}
