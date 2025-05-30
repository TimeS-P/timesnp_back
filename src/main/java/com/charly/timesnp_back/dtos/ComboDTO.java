package com.charly.timesnp_back.dtos;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ComboDTO {
    private MultipartFile imagen;
    private String nombre;
    private String precio;
    private String descripcion;
}
