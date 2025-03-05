package com.charly.timesnp_back.models;

import java.util.*;
import java.util.stream.Collectors;

import com.charly.timesnp_back.dtos.RegisterUserDto;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "usuario")
@Table(name = "usuario")
@Getter
@Setter
@ToString(exclude = {"domicilios", "verificarCorreo", "perfil", "recuperarPassword"})
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;
    
    @Column(name = "email", nullable = true, length = 100, unique = true)
    private String email;

    @Column(name = "password", nullable = true, length = 100)
    private String password;

    // Columna para manejar el rol o los roles del usuario
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    @OneToOne(mappedBy = "usuario")
    private VerificarCorreo verificarCorreo;

    @OneToMany(mappedBy = "usuario")
    private List<Domicilio> domicilios;

    @OneToOne(mappedBy = "usuario")
    private Perfil perfil;

    @OneToOne(mappedBy = "usuario")
    private RecuperarPassword recuperarPassword;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "accountNonExpired", nullable = false)
    private boolean accountNonExpired = true;

    @Column(name = "accountNonLocked", nullable = false)
    private boolean accountNonLocked = true;

    @Column(name = "credentialsNonExpired", nullable = false)
    private boolean credentialsNonExpired = true;


    public Usuario(String email, String password,  Set<Rol> roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    /**
     * @return Collection of authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(rolElement -> new SimpleGrantedAuthority(rolElement.toString()))
                .collect(Collectors.toList());
    }

    /**
     * @return String for the password
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * @return String for the
     */
    @Override
    public String getUsername() {
        return this.email;
    }

    // DTO Mapper
    public static Usuario from(RegisterUserDto registerUserDto, Set<Rol> roles) {

        return new Usuario(registerUserDto.getEmail(), registerUserDto.getPassword(), roles);
    }
}
