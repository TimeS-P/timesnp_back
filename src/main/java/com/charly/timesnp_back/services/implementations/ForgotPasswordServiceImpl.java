package com.charly.timesnp_back.services.implementations;

import com.charly.timesnp_back.models.RecuperarPassword;
import com.charly.timesnp_back.models.Usuario;
import com.charly.timesnp_back.repositories.RecuperarPasswordRepository;
import com.charly.timesnp_back.repositories.UsuarioRepository;
import com.charly.timesnp_back.services.ForgotPasswordService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


//Implementación del servicio de envío de correos
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    @Autowired
    RecuperarPasswordRepository recuperarPasswordRepository;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Override
    public String generarToken(String email) throws MessagingException {
        Optional<Usuario> user = userRepository.findByEmail(email);
        String token = UUID.randomUUID().toString();
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10); // Expira en 10 minutos
        RecuperarPassword recuperarPassword = new RecuperarPassword(token, localDateTime, user.get());
        recuperarPasswordRepository.save(recuperarPassword);

        return "http://localhost:5173/forgot_password?token=" + token;
    }

    @Override
    public String actualizarToken(UUID id_usuario) throws MessagingException {
        String token = UUID.randomUUID().toString();
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(10); // Expira en 10 minutos

        Optional<RecuperarPassword> record = recuperarPasswordRepository.findByUsuario_Id(id_usuario);
        record.get().setToken(token);
        record.get().setExpires(localDateTime);
        recuperarPasswordRepository.save(record.get());

        return "http://localhost:5173/forgot_password?token=" + token;
    }

    @Override
    public String validarToken(String token) throws MessagingException {
        //Validar que el token no este vacio
        if (token == null || token.isEmpty()) {
            return "El token no puede estar vacío";
        }

        Optional<RecuperarPassword> record = recuperarPasswordRepository.findByToken(token);

        //Validar que el token exista
        if (!record.isPresent()) {
            return "Token inválido";
        }

        // Validar que el token no ha expirado
        RecuperarPassword recuperarPassword = record.get();
        LocalDateTime expirationDate = recuperarPassword.getExpires();


        // Verificar si la fecha actual es posterior a la fecha de expiración
        if (LocalDateTime.now().isAfter(expirationDate)) {
            return "El token ha expirado";
        }

        return "Token validado correctamente";
    }

    @Override
    public void cambiarContrasena(String contrasena, String token) throws MessagingException {
        Optional<RecuperarPassword> record = recuperarPasswordRepository.findByToken(token);
        RecuperarPassword recuperarPassword = record.get();
        Usuario usuario = recuperarPassword.getUsuario();
        usuario.setPassword(this.passwordEncoder.encode(contrasena));
        userRepository.save(usuario);
    }

    @Override
    public void eliminarToken(String token) throws MessagingException {
        Optional<RecuperarPassword> record = recuperarPasswordRepository.findByToken(token);
        recuperarPasswordRepository.delete(record.get());
    }
}
