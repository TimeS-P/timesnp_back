package com.charly.timesnp_back.dtos;

import com.charly.timesnp_back.models.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CrearServicioDTO {
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private UUID idCategoria;
    private UUID idTipoPrecio;
    private UUID idProveedor;
}
