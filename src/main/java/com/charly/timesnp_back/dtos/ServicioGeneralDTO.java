package com.charly.timesnp_back.dtos;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import com.charly.timesnp_back.models.ServicioGeneral;
import com.charly.timesnp_back.models.TipoServicio;

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
public class ServicioGeneralDTO {
    private UUID id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private TipoServicio tipoServicio;
    private Optional<UUID> idProveedorHasServicio;
    private Optional<UUID> idCombo;

    public static ServicioGeneralDTO fromEntity(ServicioGeneral servicioGeneral) {
      ServicioGeneralDTO servicioGeneralDTO = new ServicioGeneralDTO();
      servicioGeneralDTO.setId(servicioGeneral.getId());
      servicioGeneralDTO.setNombre(servicioGeneral.getNombre());
      servicioGeneralDTO.setDescripcion(servicioGeneral.getDescripcion());
      servicioGeneralDTO.setPrecio(servicioGeneral.getPrecio());
      servicioGeneralDTO.setTipoServicio(servicioGeneral.getTipoServicio());
      servicioGeneralDTO.setIdProveedorHasServicio(Optional.ofNullable(servicioGeneral.getProveedorHasServicio() != null ? servicioGeneral.getProveedorHasServicio().getId() : null));
      servicioGeneralDTO.setIdCombo(Optional.ofNullable(servicioGeneral.getCombo() != null ? servicioGeneral.getCombo().getId() : null));
      return servicioGeneralDTO;
  }
}
