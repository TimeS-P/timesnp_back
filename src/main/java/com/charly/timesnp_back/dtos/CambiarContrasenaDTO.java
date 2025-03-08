package com.charly.timesnp_back.dtos;

import lombok.*;

//DTO para el envío de correos, recibe Destinatario, Asunto, Mensaje, Título y Nombre
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CambiarContrasenaDTO {
    private String token;
    private String nuevaContrasena;
}
