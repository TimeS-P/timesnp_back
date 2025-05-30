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

    // Campos básicos existentes
    private String addressee;
    private String subject;
    private String title;
    private String name;
    private String message;

    // Campos adicionales para contratación
    private String tipoServicio;
    private String categoria;
    private String totalPagado;
    private String fechaContratacion;
    private String duracion;

    public EmailDTO(String addressee, String subject, String title, String name, String message) {
        this.addressee = addressee;
        this.subject = subject;
        this.title = title;
        this.name = name;
        this.message = message;
    }

}