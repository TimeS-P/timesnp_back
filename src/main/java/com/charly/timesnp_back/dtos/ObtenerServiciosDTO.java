package com.charly.timesnp_back.dtos;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ObtenerServiciosDTO {
    private UUID idCategoria;

    //Opciones para el filtro: "PRECIO_ASC", "PRECIO_DESC", "CALIF", "ALF"
    private String filtro;
}
