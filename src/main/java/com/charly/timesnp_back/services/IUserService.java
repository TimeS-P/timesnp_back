package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.RegisterUserDto;
import com.charly.timesnp_back.models.Usuario;

import java.util.UUID;

public interface IUserService {

    public Usuario registerUser(RegisterUserDto registerUserDto);

    /**
     * Actualiza el estado de bloqueo del usuario.
     * @param usuarioId ID del usuario a actualizar.
     * @param estadoBloqueado Estado de bloqueo a establecer.
     * @return Usuario actualizado.
     */
    public Usuario updateUserBlockStatus(UUID usuarioId, boolean estadoBloqueado);

}
