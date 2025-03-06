package com.charly.timesnp_back.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.charly.timesnp_back.dtos.EmailDTO;
import com.charly.timesnp_back.services.IEmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

//Implementación del servicio de envío de correos
@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Override
    public void sendEmail(EmailDTO emailDTO) throws MessagingException {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(emailDTO.getAddressee());
            helper.setSubject(emailDTO.getSubject());
            Context context = new Context();
            context.setVariable("title", emailDTO.getTitle());
            context.setVariable("name", emailDTO.getName());
            context.setVariable("message", emailDTO.getMessage());
            String htmlContent = templateEngine.process("email", context);
            helper.setText(htmlContent, true);
            ClassPathResource logo = new ClassPathResource("static/logo.png");
            helper.addInline("logo", logo);
            javaMailSender.send(message);
        }catch (MessagingException e){
            throw new MessagingException("Error al enviar el correo: "+e.getMessage());
        }
    }
}
