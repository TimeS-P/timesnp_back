package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Usuario;
import jakarta.mail.MessagingException;

import java.util.UUID;

//Interfaz para el servicio de validar tokens
public interface ForgotPasswordService {
    public String generarToken(String email) throws MessagingException;
    public String actualizarToken(UUID id_usuario) throws MessagingException;
    public String validarToken(String token) throws MessagingException;
    public void cambiarContrasena(String contrasena, String token) throws MessagingException;
    public void eliminarToken(String token) throws MessagingException;
}
