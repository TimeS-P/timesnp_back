package com.charly.timesnp_back.dtos;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.charly.timesnp_back.models.Combo;
import com.charly.timesnp_back.models.Contratacion;
import com.charly.timesnp_back.models.FotoTrabajo;
import com.charly.timesnp_back.models.ProveedorHasServicio;
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
    private Optional<List<FotoTrabajo>> fotosTrabajo;
    private Optional<ProveedorHasServicio> idProveedorHasServicio;
    private Optional<List<Contratacion>> contrataciones;
    private Optional<UUID> idCombo;

    public static ServicioGeneralDTO fromEntity(ServicioGeneral servicioGeneral) {
      ServicioGeneralDTO servicioGeneralDTO = new ServicioGeneralDTO();
      servicioGeneralDTO.setId(servicioGeneral.getId());
      servicioGeneralDTO.setNombre(servicioGeneral.getNombre());
      servicioGeneralDTO.setDescripcion(servicioGeneral.getDescripcion());
      servicioGeneralDTO.setPrecio(servicioGeneral.getPrecio());
      servicioGeneralDTO.setTipoServicio(servicioGeneral.getTipoServicio());
      servicioGeneralDTO.setFotosTrabajo(Optional.ofNullable(servicioGeneral.getFotos()));
      servicioGeneralDTO.setContrataciones(Optional.ofNullable(servicioGeneral.getContrataciones()));
      servicioGeneralDTO.setIdProveedorHasServicio(Optional.ofNullable(servicioGeneral.getProveedorHasServicio() != null ? servicioGeneral.getProveedorHasServicio(): null));
      servicioGeneralDTO.setIdCombo(Optional.ofNullable(servicioGeneral.getCombo() != null ? servicioGeneral.getCombo().getId() : null));
      return servicioGeneralDTO;
  }
}
