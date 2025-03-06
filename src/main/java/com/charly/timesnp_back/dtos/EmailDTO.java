package com.charly.timesnp_back.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//DTO para el envío de correos, recibe Destinatario, Asunto, Mensaje, Título y Nombre
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmailDTO {
    private String addressee;
    private String subject;
    private String message;
    private String title;
    private String name;
}
