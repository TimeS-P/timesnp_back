package com.charly.timesnp_back.dtos;

import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.ServicioGeneral;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContratacionDTO {
    private Date fechaInicio;
    private Date fechaFin;
    private BigDecimal total;
    private Integer cantidad;
    private UUID perfilId;  // Solo el id
    private UUID servicioGeneralId; // Solo el id

    private String codigoCompartir;
    private Boolean usePoints;
}