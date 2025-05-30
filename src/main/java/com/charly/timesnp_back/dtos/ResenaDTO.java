package com.charly.timesnp_back.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResenaDTO {
    private int calificacion;
    private String comentario;
    private UUID contratacionId;
}
