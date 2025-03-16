package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.dtos.VerificationDeniedDTO;

import jakarta.mail.MessagingException;

//Interfaz para el servicio de envío de correos
public interface IEmailService  {
    public void sendEmail(EmailDTO emailDTO) throws MessagingException;
    public void sendForgotPassword(String email, String link) throws  MessagingException;
    public void sendVerificationConfirmation(String email, String nombre) throws MessagingException;
    public void sendVerificationDenied(VerificationDeniedDTO verificationDeniedDTO) throws MessagingException;
}
