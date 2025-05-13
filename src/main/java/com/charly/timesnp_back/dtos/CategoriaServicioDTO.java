package com.charly.timesnp_back.dtos;

import com.charly.timesnp_back.models.CategoriaServicio;
import lombok.*;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaServicioDTO {
    private Optional<UUID> id = Optional.empty();
    private Optional<String> nombre = Optional.empty();
    private Optional<String> icono = Optional.empty();

    public static CategoriaServicioDTO fromEntity(CategoriaServicio categoriaServicio) {
        CategoriaServicioDTO categoriaServicioDTO = new CategoriaServicioDTO();
        categoriaServicioDTO.setId(Optional.ofNullable(categoriaServicio.getId()));
        categoriaServicioDTO.setNombre(Optional.ofNullable(categoriaServicio.getNombre()));
        categoriaServicioDTO.setIcono(Optional.ofNullable(categoriaServicio.getIcono()));
        return categoriaServicioDTO;
    }
}
