package com.charly.timesnp_back.dtos;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private Optional<UUID> id = Optional.empty();
    private Optional<String> nombre = Optional.empty();
    private Optional<String> apellidoPaterno = Optional.empty();
    private Optional<String> apellidoMaterno = Optional.empty();
    private Optional<String> telefono = Optional.empty();
    private Optional<Date> fechaNacimiento = Optional.empty();
    private Optional<String> genero = Optional.empty();
    private Optional<String> descripcion = Optional.empty();
}
