package com.charly.timesnp_back.services;

import java.util.Optional;

import com.charly.timesnp_back.dtos.PerfilDTO;
import com.charly.timesnp_back.models.Perfil;

//Interfaz para el servicio de cambio de datos de perfil
public interface IChangePerfilData {
    Perfil changeData(PerfilDTO perfilDTO);
    Optional<Perfil> getPerfilData();
}
