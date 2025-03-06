package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.EmailDTO;

import jakarta.mail.MessagingException;

//Interfaz para el servicio de envío de correos
public interface IEmailService  {
    public void sendEmail(EmailDTO emailDTO) throws MessagingException;
}
