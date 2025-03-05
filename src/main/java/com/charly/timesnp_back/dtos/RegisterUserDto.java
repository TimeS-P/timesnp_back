package com.charly.timesnp_back.dtos;

import com.charly.timesnp_back.models.RolNombre;
import lombok.*;
import com.charly.timesnp_back.models.Rol;

import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {

    private String email;
    private String password;
    // List of  roles that the user has
    private Set<RolNombre> roles;

}
