package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.EmailDTO;

import jakarta.mail.MessagingException;

public interface IEmailService  {
    public void sendEmail(EmailDTO emailDTO) throws MessagingException;
}
