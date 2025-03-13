package com.charly.timesnp_back.services;

import com.charly.timesnp_back.dtos.RegisterUserDto;
import com.charly.timesnp_back.models.Usuario;

public interface IUserService {

    public Usuario registerUser(RegisterUserDto registerUserDto);

}
