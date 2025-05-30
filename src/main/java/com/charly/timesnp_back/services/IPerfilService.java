package com.charly.timesnp_back.services;

import com.charly.timesnp_back.models.Perfil;

import java.util.UUID;

public interface IPerfilService {

    /**
     * Obtiene el perfil de un usuario
     * @param id ID del usuario
     * @return perfil del usuario
     */
    public Perfil getPerfilById(UUID id);

    /**
     * Crea un perfil para un usuario
     * @param perfil perfil del usuario
     * @return perfil creado
     */
    public Perfil createPerfil(Perfil perfil);

    /**
     * Actualiza el perfil de un usuario
     * @param perfil perfil del usuario
     * @return perfil actualizado
     */
    public Perfil updatePerfil(Perfil perfil);

    /**
     * Elimina el perfil de un usuario
     * @param id ID del usuario
     */
    public void deletePerfil(UUID id);

    /**
     * Verifica el perfil de un usuario
     * @param id ID del usuario
     * @return perfil verificado
     */
    public Perfil verifyPerfil(UUID id);

    /**
     * Obtiene el perfil de un usuario por su correo
     * @param email correo del usuario
     * @return perfil del usuario
     */
    public Perfil getPerfilByUserEmail(String email) throws Exception;

    public Perfil existsByCodigoCompartir(String codigoCompartir) throws Exception;


    public Perfil existsByCodigoCompartirContratacion(String codigoCompartir) throws Exception;
}
