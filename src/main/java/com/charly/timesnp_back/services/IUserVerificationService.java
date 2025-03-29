package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Usuario;
import jakarta.mail.MessagingException;

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
     * Verifica el INE de un usuario
     * @return mensaje de confirmación
     */
    public String verifyINE();

}
