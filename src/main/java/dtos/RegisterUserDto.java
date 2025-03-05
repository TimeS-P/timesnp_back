package dtos;

import lombok.*;
import models.Rol;

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
    private Set<Rol> roles;

}
