package com.charly.timesnp_back.dtos;

import com.charly.timesnp_back.models.Perfil;
import com.charly.timesnp_back.models.ServicioGeneral;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ContratacionDTO {
    private Optional<UUID> id = Optional.empty();
    private Optional<Date> fechaInicio = Optional.empty();
    private Optional<Date> fechaFin = Optional.empty();
    private Optional<BigDecimal> total = Optional.empty();
    private Optional<Perfil> perfil = Optional.empty();
    private Optional<ServicioGeneral> servicioGeneral = Optional.empty();

    public ContratacionDTO(Date fechaInicio, Date fechaFin, BigDecimal total, Perfil perfil, ServicioGeneral servicioGeneral) {
        this.fechaInicio = Optional.ofNullable(fechaInicio);
        this.fechaFin = Optional.ofNullable(fechaFin);
        this.total = Optional.ofNullable(total);
        this.perfil = Optional.ofNullable(perfil);
        this.servicioGeneral = Optional.ofNullable(servicioGeneral);
    }
}
